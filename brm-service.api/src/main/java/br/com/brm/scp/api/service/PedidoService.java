package br.com.brm.scp.api.service;

import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoOrigemNotFoundException;
import br.com.brm.scp.api.vo.PedidoVO;

public interface PedidoService {

	PedidoResponseDTO request(String sku, int quantidade, Date dateSolicitacao, String descricao) throws PedidoOrigemNotFoundException;

	Collection<PedidoResponseDTO> listByOrigem(String sku) throws PedidoNotFoundException;

	Collection<PedidoVO> monitoramento();

}
