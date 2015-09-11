package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemCategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.ItemDocument;
import br.com.brm.scp.mock.api.service.status.ItemFiltroEnum;

public class ItemServiceMockImpl implements ItemService {
	private Logger logger = Logger.getLogger(ItemServiceMockImpl.class);

	@Autowired
	private MockData mockdb;

	public ItemServiceMockImpl(MockData mockdb) {
		super();
		this.mockdb = mockdb;
	}

	@Override
	public ItemResponseDTO create(ItemRequestDTO request)
			throws ItemExistenteException, ItemCategoriaNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemResponseDTO find(ItemFiltroEnum filtro, Object value) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemResponseDTO update(ItemRequestDTO request) throws ItemNotFoundException, ItemCategoriaNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public ItemResponseDTO create(ItemRequestDTO request) throws ItemNotFoundException, ItemExistenteException {
		prepareSave(request);
		hasItem(request);
		return insert(request);
	}
	
	public ItemResponseDTO insert(ItemRequestDTO request){
		request.setId(new Long("1"));
		ItemDocument document = (ItemDocument) ConverterHelper.convert(request, ItemDocument.class);
		mockdb.getItemCollection().put(document.getId(), document);
		return (ItemResponseDTO) ConverterHelper.convert(document, ItemResponseDTO.class);
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
		prepareSave(request);
		findById(request.getId());
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
	
	@Override
	public ItemResponseDTO findByName(String nome) throws ItemNotFoundException{
		for (ItemDocument document : mockdb.getItemCollection().values()) {
			if (document.getNome().equals(nome)) {
				return (ItemResponseDTO) ConverterHelper.convert(document,ItemResponseDTO.class);
			}
		}
		logger.info("Item não encontrado");
		throw new ItemNotFoundException();
	}
	
	@Override
	public ItemResponseDTO findById(Long id) throws ItemNotFoundException{
		for (ItemDocument document : mockdb.getItemCollection().values()) {
			if (document.getId().equals(id)) {
				return (ItemResponseDTO) ConverterHelper.convert(document,ItemResponseDTO.class);
			}
		}
		logger.info("Item não encontrado");
		throw new ItemNotFoundException();
	}
	
	public void prepareSave(ItemRequestDTO request){
		Assert.notNull(request.getNome(), "item.nomenaopreenchido");
		Assert.notNull(request.getFornecedor(), "item.fornecedornaopreenchido");
		Assert.notNull(request.getCategoria(), "item.categorianaopreenchida");
	}

	@Override
	public Collection<ItemResponseDTO> all() throws ItemNotFoundException {
		Collection<ItemDocument> allItens = mockdb.getItemCollection().values();
		if(allItens.isEmpty()){
			throw new ItemNotFoundException("item.nenhum.registro.encontrado");
		}
		return ConverterHelper.convert(allItens, ItemResponseDTO.class);
	}*/
}
