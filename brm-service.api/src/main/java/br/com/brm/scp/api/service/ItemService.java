package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;

public interface ItemService {

	Collection<ItemResponseDTO> all() throws ItemNotFoundException;
	
	ItemResponseDTO create(ItemRequestDTO request) throws ItemNotFoundException, ItemExistenteException;

	void delete(ItemRequestDTO request) throws ItemNotFoundException;

	void update(ItemRequestDTO request) throws ItemNotFoundException;
	
	ItemResponseDTO findByName(String nome) throws ItemNotFoundException;

	ItemResponseDTO findById(Long id) throws ItemNotFoundException;
}
