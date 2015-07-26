package br.com.brm.scp.mock.api.service.strategies;

import java.util.Calendar;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;
import br.com.brm.scp.api.service.strategies.interfaces.SkuStrategy;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.db.SkuOperationDB;
import br.com.brm.scp.mock.api.service.document.PedidoDocument;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;
import br.com.brm.scp.mock.api.service.status.StatusPedido;

public class SkuReabastecimentoStrategyImpl implements SkuStrategy {

	private SkuOperationDB db;

	public SkuReabastecimentoStrategyImpl(SkuOperationDB db) {
		super();
		this.db = db;
	}

	@Override
	public SkuResponseDTO save(SkuRequestDTO request, Long ... idUsuarioLogado) throws SkuException {
		SkuDocument document = db.findOne(request.getId());

		Integer quantidadeLote = document.getItem().getQuantidadeLote();
		Integer estoqueIdeal = document.getEstoqueIdeal();

		Integer quantidadePedido = estoqueIdeal / quantidadeLote;

		PedidoDocument pedido = new PedidoDocument();
		pedido.setDataCriacao(Calendar.getInstance());
		pedido.setDescricao("Criação de pedido pelo metodo 'reabastecimento'");
		pedido.setIdSku(request.getId());
		pedido.setQuantidade(quantidadePedido);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		UsuarioDocument user = new UsuarioDocument();
		if(idUsuarioLogado != null && idUsuarioLogado.length > 0)
			user.setId(idUsuarioLogado[0]);
		
		pedido.setUsuarioCriacao(user);
		
		db.insertPedido(pedido);
		
		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
		
	}

	/* METODO NAO EXISTENTE PARA ESTA ESTRATEGIA
	 * @see br.com.brm.scp.api.service.strategies.interfaces.SkuStrategy#save(br.com.brm.scp.api.dto.request.SkuRequestDTO)
	 */
	@Override
	public SkuResponseDTO save(SkuRequestDTO request) throws SkuException {
		throw new IllegalArgumentException("Metodo invalido para esta estrategia");
	}

}
