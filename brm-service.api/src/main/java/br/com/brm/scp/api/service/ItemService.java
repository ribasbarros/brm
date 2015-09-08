package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemCategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.mock.api.service.status.ItemFiltroEnum;

public interface ItemService {

	ItemResponseDTO create(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException;

	ItemResponseDTO update(ItemRequestDTO request) throws ItemNotFoundException, ItemCategoriaNotFoundException;

	ItemResponseDTO find(ItemFiltroEnum filtro, Object value) throws ItemNotFoundException;

	void delete(String id) throws ItemNotFoundException;

}
