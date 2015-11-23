package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoOrigemNotFoundException;

public interface PedidoService {

	PedidoResponseDTO request(String sku, int quantidade, String descricao) throws PedidoOrigemNotFoundException;

	Collection<PedidoResponseDTO> listByOrigem(String sku) throws PedidoNotFoundException;

}
