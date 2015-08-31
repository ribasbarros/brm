package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemCategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;

public interface ItemService {

	ItemResponseDTO create(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException;

	ItemResponseDTO update(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException;

}
