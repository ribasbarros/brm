package br.com.brm.scp.api.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.fields;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoOrigemNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotSuchMuchQuantityException;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.document.PedidoDocument;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.PedidoRepository;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.api.service.status.OrigemTipoEnum;
import br.com.brm.scp.api.service.status.PedidoStatus;
import br.com.brm.scp.api.service.status.PedidoType;
import br.com.brm.scp.api.vo.PedidoVO;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import net.wimpi.telnetd.io.terminal.ansi;

/**
 * @author Ribas
 *
 */
@Service
public class PedidoServiceImpl implements PedidoService {

	private static final String PEDIDO_SKU = "pedido.sku";
	private static final String PEDIDO_QUANTIDADE = "pedido.quantidade";
	private static final String PEDIDO_SKU_ORIGEM_NOTFOUND = "pedido.sku.origem.notfound";
	private static final String PEDIDO_SKU_DESTINO_NOTFOUND = "pedido.sku.destino.notfound";
	private static final String PEDIDO_SKU_DEFAULT_NOTFOUND = "pedido.sku.default.notfound";
	private static final String PEDIDO_NENHUM_PEDIDO_ENCONTRADO = "pedido.notfound";

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private SkuRepository skuRepository;

	@Autowired
	private SkuService skuService;

	@Autowired
	private MongoTemplate mongoTemplate;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	@Override
	public PedidoResponseDTO request(String sku, Long quantidade, Date dataSolicitacao, String descricao,
			boolean escalonado) throws PedidoOrigemNotFoundException {

		/** VALIDACAO **/
		Assert.notNull(sku, PEDIDO_SKU);
		Assert.isTrue(quantidade > 0, PEDIDO_QUANTIDADE);
		SkuDocument skuOrigem = skuRepository.findOne(sku); // PROCURA A SKU DE
															// ORIGEM
		Assert.notNull(skuOrigem, PEDIDO_SKU_ORIGEM_NOTFOUND);
		OrigemSkuDocument skuDefault = getSkuDefault(skuOrigem.getOrigens());
		boolean isSku = OrigemTipoEnum.SKU.equals(skuDefault.getTipo());
		SkuDocument skuDestino = null;
		if (isSku) {
			skuDestino = skuRepository.findOne(skuDefault.getId()); // PROCURA A
																	// SKU DE
																	// DESTINO
																	// (DEFAULT)
			Assert.notNull(skuDestino, PEDIDO_SKU_DESTINO_NOTFOUND);
		}
		/** FIM VALIDACAO **/

		Date dataAtual = new Date();

		PedidoDocument document = new PedidoDocument();

		document.setDataCriacao(dataAtual);
		document.setDataSolicitacao(dataSolicitacao);
		document.getLog().add(String.format("%s - Pedido Criado - %s", sdf.format(dataAtual), descricao));
		document.setOrigem(skuOrigem.getId());
		document.setDestino(skuDestino != null ? skuDestino.getId() : null);
		if (escalonado) {
			Long dif;
			if (skuOrigem.getEstoqueAtual() > quantidade) {
				dif = skuOrigem.getEstoqueAtual() - quantidade;
			} else {
				dif = quantidade - skuOrigem.getEstoqueAtual();
			}
			document.setQuantidade(dif);
		} else {
			document.setQuantidade(quantidade);
		}
		document.setStatus(isSku ? PedidoStatus.SOLICITADO : PedidoStatus.AGUARDANDO_FORNECEDOR);
		document.setDescricao(descricao);
		document.setEscalonada(escalonado);

		document = repository.save(document);

		if (isSku) {
			PedidoDocument document4Dest = doOrderInverse(document);
			document4Dest = repository.save(document4Dest);

			document.setIdPedidoDestino(document4Dest.getId());
			document = repository.save(document);
		}

		return (PedidoResponseDTO) ConverterHelper.convert(document, PedidoResponseDTO.class);

	}

	private PedidoDocument doOrderInverse(PedidoDocument origem) {

		PedidoDocument document = (PedidoDocument) ConverterHelper.convert(origem, PedidoDocument.class);
		document.setId(null);

		document.setOrigem(origem.getDestino());
		document.setDestino(origem.getOrigem());
		document.setQuantidade(-origem.getQuantidade());
		document.setIdPedidoDestino(origem.getId());
		document.setEscalonada(false);
		document.getLog().add(
				String.format("%s - Pedido CRIADO a partir da Origem %s ", sdf.format(new Date()), origem.getOrigem()));

		return document;

	}

	private OrigemSkuDocument getSkuDefault(Collection<OrigemSkuDocument> origens)
			throws PedidoOrigemNotFoundException {
		for (OrigemSkuDocument origem : origens) {
			if (origem.isPadrao()) {
				return origem;
			}
		}

		throw new PedidoOrigemNotFoundException(PEDIDO_SKU_DEFAULT_NOTFOUND);

	}

	@Override
	public Collection<PedidoResponseDTO> listByOrigem(String sku) throws PedidoNotFoundException {
		Collection<PedidoDocument> list = repository.listByOrigem(sku);

		if (null == list || list.isEmpty())
			throw new PedidoNotFoundException(PEDIDO_NENHUM_PEDIDO_ENCONTRADO);

		return ConverterHelper.convert(list, PedidoResponseDTO.class);

	}

	@Override
	public Collection<PedidoVO> monitoramento() {

		Aggregation agg = newAggregation(
				match(Criteria.where("status").in(PedidoStatus.SOLICITADO.name(),
						PedidoStatus.AGUARDANDO_FORNECEDOR.name())),
				group("origem").first("origem").as("origem").sum("quantidade").as("quantidade"),
				sort(Sort.Direction.DESC, "quantidade"));

		AggregationResults<PedidoVO> result = mongoTemplate.aggregate(agg, PedidoDocument.class, PedidoVO.class);

		Collection<PedidoVO> groups = result.getMappedResults();

		return groups;

	}

	@Override
	public void delete(String id) throws PedidoNotFoundException {

		PedidoDocument document = repository.findOne(id);
		if (null == document)
			throw new PedidoNotFoundException(PEDIDO_NENHUM_PEDIDO_ENCONTRADO);

		cancelar(document);

		if (document.getDestino() != null) {
			PedidoDocument destino = repository.findOne(document.getIdPedidoDestino());
			cancelar(destino);
		}

	}

	private void cancelar(PedidoDocument document) {
		document.setStatus(PedidoStatus.CANCELADO);
		document.getLog().add(String.format("%s - Pedido Cancelado", sdf.format(new Date())));
		repository.save(document);
	}

	@Override
	public PedidoResponseDTO find(String id) throws PedidoNotFoundException {

		PedidoDocument document = repository.findOne(id);
		if (null == document)
			throw new PedidoNotFoundException(PEDIDO_NENHUM_PEDIDO_ENCONTRADO);

		return (PedidoResponseDTO) ConverterHelper.convert(document, PedidoResponseDTO.class);

	}

	@Override
	public PedidoResponseDTO escalonar(String id) throws PedidoNotFoundException {

		PedidoDocument document = repository.findOne(id);
		if (null == document)
			throw new PedidoNotFoundException(PEDIDO_NENHUM_PEDIDO_ENCONTRADO);

		document.setEscalonada(true);

		document = repository.save(document);

		return (PedidoResponseDTO) ConverterHelper.convert(document, PedidoResponseDTO.class);
	}

	@Override
	public PedidoResponseDTO liberar(String id) throws PedidoNotFoundException {

		PedidoDocument document = repository.findOne(id);
		if (null == document)
			throw new PedidoNotFoundException(PEDIDO_NENHUM_PEDIDO_ENCONTRADO);

		try {
			
			document.setStatus(PedidoStatus.CONCLUIDO);
			document.setDataFinalizacao(new Date());
			
			try {
				skuService.addEstoque(document.getOrigem(), document.getQuantidade());
			} catch (SkuNotSuchMuchQuantityException e) {
				document.setStatus(PedidoStatus.RUPTURA_ESTOQUE);
			}
			
			repository.save(document);

			if (document.getDestino() != null) {
				
				PedidoDocument destino = repository.findOne(document.getIdPedidoDestino());
				destino.setStatus(PedidoStatus.CONCLUIDO);
				destino.setDataFinalizacao(new Date());
				
				try {
					skuService.addEstoque(document.getDestino(), Math.abs(document.getQuantidade()));
				} catch (SkuNotSuchMuchQuantityException e) {
					document.setStatus(PedidoStatus.RUPTURA_ESTOQUE);
				}
				
				repository.save(destino);
			}
		} catch (SkuNotFoundException e) {
			throw new PedidoNotFoundException(e.getMessage());
		}

		return (PedidoResponseDTO) ConverterHelper.convert(document, PedidoResponseDTO.class);

	}

	@Override
	public PedidoResponseDTO ordemVenda(PedidoType externo, String sku, Long quantidade, Date dataSolicitacao, String descricao,
			boolean escalonado) throws PedidoOrigemNotFoundException {
		/** VALIDACAO **/
		Assert.notNull(sku, PEDIDO_SKU);
		Assert.isTrue(Math.abs(quantidade) > 0, PEDIDO_QUANTIDADE);
		SkuDocument skuOrigem = skuRepository.findOne(sku); // PROCURA A SKU DE ORIGEM
		Assert.notNull(skuOrigem, PEDIDO_SKU_ORIGEM_NOTFOUND);
		/** FIM VALIDACAO **/

		Date dataAtual = new Date();

		PedidoDocument document = new PedidoDocument();
		document.setQuantidade(quantidade);
		document.setTipo(externo);
		document.setDataCriacao(dataAtual);
		document.setDataSolicitacao(dataSolicitacao);
		document.getLog().add(String.format("%s - Pedido Criado - %s", sdf.format(dataAtual), descricao));
		document.setOrigem(skuOrigem.getId());
		document.setStatus(PedidoStatus.EM_PROCESSAMENTO);
		document.setDescricao(descricao);
		document.setEscalonada(escalonado);

		document = repository.save(document);

		return (PedidoResponseDTO) ConverterHelper.convert(document, PedidoResponseDTO.class);
	}

	/* METODO APENAS PARA SIMULACAO! DEVE TER A MESMA FUNCIONALIDADE DO LIBERAR()
	 * @see br.com.brm.scp.api.service.PedidoService#liberar(java.lang.String, java.util.Date)
	 */
	@Override
	public PedidoResponseDTO liberar(String id, Date time) throws PedidoNotFoundException {
		PedidoDocument document = repository.findOne(id);
		if (null == document)
			throw new PedidoNotFoundException(PEDIDO_NENHUM_PEDIDO_ENCONTRADO);

		try {
			
			document.setStatus(PedidoStatus.CONCLUIDO);
			document.setDataFinalizacao(time);
			
			try {
				skuService.addEstoque(document.getOrigem(), document.getQuantidade());
			} catch (SkuNotSuchMuchQuantityException e) {
				document.setStatus(PedidoStatus.RUPTURA_ESTOQUE);
			}
			
			repository.save(document);

			if (document.getDestino() != null) {
				
				PedidoDocument destino = repository.findOne(document.getIdPedidoDestino());
				destino.setStatus(PedidoStatus.CONCLUIDO);
				destino.setDataFinalizacao(time);
				
				try {
					skuService.addEstoque(document.getDestino(), Math.abs(document.getQuantidade()));
				} catch (SkuNotSuchMuchQuantityException e) {
					document.setStatus(PedidoStatus.RUPTURA_ESTOQUE);
				}
				
				repository.save(destino);
			}
		} catch (SkuNotFoundException e) {
			throw new PedidoNotFoundException(e.getMessage());
		}

		return (PedidoResponseDTO) ConverterHelper.convert(document, PedidoResponseDTO.class);
	}

	@Override
	public Collection<PedidoVO> findFaturamentoByMonth(String sku, int monthAgo) {
		Aggregation agg = newAggregation(
				
				match(Criteria.where("status").is(PedidoStatus.CONCLUIDO.name()).
						and("origem").is(sku)),
				project("quantidade")
	            .andExpression("dayOfMonth(dataSolicitacao)").as("day")
	            .andExpression("month(dataSolicitacao)").as("month")
	            .andExpression("year(dataSolicitacao)").as("year"),
				
	            group(fields().and("year").and("month"))
	            .sum("quantidade").as("quantidade").
	            avg("quantidade").as("ddv"),
	            
	            sort(Sort.Direction.ASC, "year", "month")
				
				);
		AggregationResults<PedidoVO> result = mongoTemplate.aggregate(agg, PedidoDocument.class, PedidoVO.class);

		Collection<PedidoVO> groups = result.getMappedResults();
		
		return groups;
	}

}
