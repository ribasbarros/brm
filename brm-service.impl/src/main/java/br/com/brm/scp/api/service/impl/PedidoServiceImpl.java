package br.com.brm.scp.api.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.previousOperation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

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
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.document.PedidoDocument;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.PedidoRepository;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.api.service.status.OrigemTipoEnum;
import br.com.brm.scp.api.service.status.PedidoStatus;
import br.com.brm.scp.api.vo.PedidoVO;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import net.wimpi.telnetd.io.terminal.ansi;
import scala.annotation.meta.field;

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
	private MongoTemplate mongoTemplate;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	@Override
	public PedidoResponseDTO request(String sku, int quantidade, Date dataSolicitacao, String descricao) throws PedidoOrigemNotFoundException {
		
		/** VALIDACAO **/
		Assert.notNull(sku, PEDIDO_SKU);
		Assert.isTrue(quantidade > 0, PEDIDO_QUANTIDADE);
		SkuDocument skuOrigem = skuRepository.findOne(sku); // PROCURA A SKU DE ORIGEM
		Assert.notNull(skuOrigem, PEDIDO_SKU_ORIGEM_NOTFOUND);
		OrigemSkuDocument skuDefault = getSkuDefault(skuOrigem.getOrigens());
		boolean isSku = OrigemTipoEnum.SKU.equals(skuDefault.getTipo());
		SkuDocument skuDestino = null;
		if(isSku){
			skuDestino = skuRepository.findOne(skuDefault.getId()); // PROCURA A SKU DE DESTINO (DEFAULT)
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
		document.setQuantidade(quantidade);
		document.setStatus(isSku ? PedidoStatus.SOLICITADO : PedidoStatus.AGUARDANDO_FORNECEDOR);
		document.setDescricao(descricao);
		
		document = repository.save(document);
		
		return (PedidoResponseDTO) ConverterHelper.convert(document, PedidoResponseDTO.class);
		
	}

	private OrigemSkuDocument getSkuDefault(Collection<OrigemSkuDocument> origens) throws PedidoOrigemNotFoundException {
		for(OrigemSkuDocument origem : origens){
			if( origem.isPadrao() ){
				return origem;
			}
		}
		
		throw new PedidoOrigemNotFoundException(PEDIDO_SKU_DEFAULT_NOTFOUND);
		
	}

	@Override
	public Collection<PedidoResponseDTO> listByOrigem(String sku) throws PedidoNotFoundException {
		Collection<PedidoDocument> list = repository.listByOrigem(sku);
		
		if(null == list || list.isEmpty())
			throw new PedidoNotFoundException(PEDIDO_NENHUM_PEDIDO_ENCONTRADO);

		return ConverterHelper.convert(list, PedidoResponseDTO.class);
		
	}

	@Override
	public Collection<PedidoVO> monitoramento() {
		
		Aggregation agg = newAggregation(
				match(Criteria.where("status").in(PedidoStatus.SOLICITADO.name(), PedidoStatus.AGUARDANDO_FORNECEDOR.name())),
				group("origem").first("origem").as("origem").
					sum("quantidade").as("quantidade"),
				sort(Sort.Direction.DESC, "quantidade"));
		
		AggregationResults<PedidoVO> result = mongoTemplate.aggregate(agg, PedidoDocument.class, PedidoVO.class);
		
		Collection<PedidoVO> groups = result.getMappedResults();
		
		return groups;
		
	}
	
}
