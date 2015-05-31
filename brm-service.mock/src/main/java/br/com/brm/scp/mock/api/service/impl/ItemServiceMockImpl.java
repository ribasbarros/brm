package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemNotFound;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class ItemServiceMockImpl implements ItemService {

	@Autowired
	private MockData dbMock;
	
	@Override
	public Collection<ItemResponseDTO> all() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ItemRequestDTO request) throws ItemNotFound {
		
		Assert.notNull(request, "item.request");
		Assert.notNull(request.getNome(), "item.nome");
		
		if(existe(request)){
			throw new ItemNotFound();
		}
		
		criar(request);
		
	}

	private void criar(ItemRequestDTO request) {
		// TODO Auto-generated method stub
		
	}

	private boolean existe(ItemRequestDTO request) {
		// TODO Auto-generated method stub
		return false;
	}


}
