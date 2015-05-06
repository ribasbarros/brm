package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.response.ItemResponseDTO;


public interface ItemService {

	Collection<ItemResponseDTO> all();

}
