package br.com.brm.scp.api.service;

import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoOrigemNotFoundException;
import br.com.brm.scp.api.service.status.PedidoType;
import br.com.brm.scp.api.vo.PedidoVO;

public interface PedidoService {

	PedidoResponseDTO request(String sku, int quantidade, Date dateSolicitacao, String descricao, boolean escalonado) throws PedidoOrigemNotFoundException;

	Collection<PedidoResponseDTO> listByOrigem(String sku) throws PedidoNotFoundException;

	Collection<PedidoVO> monitoramento();

	void delete(String id) throws PedidoNotFoundException;

	PedidoResponseDTO find(String id) throws PedidoNotFoundException;

	PedidoResponseDTO escalonar(String id) throws PedidoNotFoundException;

	PedidoResponseDTO liberar(String id) throws PedidoNotFoundException;

	PedidoResponseDTO ordemVenda(PedidoType externo, String sku, int quantidade, Date dataSolicitacao, String descricao, boolean escalonado) throws PedidoOrigemNotFoundException;

	@Deprecated
	PedidoResponseDTO liberar(String id, Date time) throws PedidoNotFoundException;

	Collection<PedidoVO> findFaturamentoByMonth(String sku, int monthAgo);

}
