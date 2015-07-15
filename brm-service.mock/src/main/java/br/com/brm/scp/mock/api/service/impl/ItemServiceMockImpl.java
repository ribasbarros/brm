package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.CategoriaDocument;
import br.com.brm.scp.mock.api.service.document.ItemDocument;

public class ItemServiceMockImpl implements ItemService {
	private Logger logger = Logger.getLogger(ItemServiceMockImpl.class);

	@Autowired
	private MockData mockdb;

	public ItemServiceMockImpl(MockData mockdb) {
		super();
		this.mockdb = mockdb;
	}

	@Override
	public Collection<ItemResponseDTO> all() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemResponseDTO create(ItemRequestDTO request) throws ItemNotFoundException, ItemExistenteException {
		Assert.notNull(request.getNome(), "item.nomenaopreenchido");
		Assert.notNull(request.getFornecedor(), "item.fornecedornaopreenchido");
		Assert.notNull(request.getCategoria(), "item.categorianaopreenchida");
		hasItem(request);
		ItemDocument document = (ItemDocument) ConverterHelper.convert(request, ItemDocument.class);
		mockdb.getItemCollection().put(document.getId(), document);
		return (ItemResponseDTO) ConverterHelper.convert(document,ItemResponseDTO.class);
	}

	@Override
	public void delete(ItemRequestDTO request) throws ItemNotFoundException {
		findByName(request.getNome());
		ItemDocument document = (ItemDocument) ConverterHelper.convert(request, ItemDocument.class);
		document.setDataExcluido(new Date());
		mockdb.getItemCollection().put(document.getId(), document);

	}

	@Override
	public void update(ItemRequestDTO request) throws ItemNotFoundException {
		findByName(request.getNome());
		ItemDocument document = (ItemDocument) ConverterHelper.convert(request, ItemDocument.class);
		mockdb.getItemCollection().put(document.getId(), document);
	}
	
	public void hasItem(ItemRequestDTO request) throws ItemExistenteException{
		try{
			if (findByName(request.getNome()) != null) {
				logger.debug(String.format("Item já cadastrado na base de id=%s", request.getId()));
				throw new ItemExistenteException();
			}
		}catch(ItemNotFoundException exception){
		
		}
	}
	
	private ItemResponseDTO findByName(String nome) throws ItemNotFoundException{
		for (ItemDocument document : mockdb.getItemCollection().values()) {
			if (document.getNome().equals(nome)) {
				return (ItemResponseDTO) ConverterHelper.convert(document,ItemResponseDTO.class);
			}
		}
		logger.info("Item não encontrado");
		throw new ItemNotFoundException();
	}

}
