package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemNotFound;

public interface ItemService {

	Collection<ItemResponseDTO> all();

	void create(ItemRequestDTO request) throws ItemNotFound;

	void delete(ItemRequestDTO request);

	void update(ItemRequestDTO request);

}
