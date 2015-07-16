package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.service.ItemService;

public class ItemServiceImpl implements ItemService {

	@Override
	public Collection<ItemResponseDTO> all() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemResponseDTO create(ItemRequestDTO request)
			throws ItemNotFoundException, ItemExistenteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ItemRequestDTO request) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ItemRequestDTO request) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemResponseDTO findByName(String nome) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemResponseDTO findById(Long id) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
