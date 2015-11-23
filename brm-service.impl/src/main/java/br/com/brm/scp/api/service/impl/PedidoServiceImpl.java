package br.com.brm.scp.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

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
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	@Override
	public PedidoResponseDTO request(String sku, int quantidade, String descricao) throws PedidoOrigemNotFoundException {
		
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
		
		document.setDataSolicitacao(dataAtual);
		document.getLog().add(String.format("%s - Pedido Criado - %s", sdf.format(dataAtual), descricao));
		document.setOrigem(skuOrigem);
		document.setDestino(skuDestino);
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
	
}
