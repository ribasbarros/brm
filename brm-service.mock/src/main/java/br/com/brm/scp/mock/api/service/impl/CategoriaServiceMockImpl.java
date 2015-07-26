package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistentException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.service.CategoriaService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.objects.RandomHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.CategoriaDocument;

public class CategoriaServiceMockImpl implements CategoriaService {
	private Logger logger = Logger.getLogger(CategoriaServiceMockImpl.class);

	private MockData mockdb;

	public CategoriaServiceMockImpl(MockData mockdb) {
		super();
		this.mockdb = mockdb;
	}

	@Override
	public CategoriaResponseDTO create(CategoriaRequestDTO request) throws CategoriaExistentException {
		prepareSave(request);
		hasCategoria(request);
		return insert(request);
	}
	
	public CategoriaResponseDTO insert(CategoriaRequestDTO request){
		request.setId(new Long("1"));
		CategoriaDocument document = (CategoriaDocument) ConverterHelper.convert(request, CategoriaDocument.class);
		mockdb.getCategoriaCollection().put(document.getId(), document);
		return (CategoriaResponseDTO) ConverterHelper.convert(document, CategoriaResponseDTO.class);
	}
	
	public void prepareSave(CategoriaRequestDTO request){
		Assert.notNull(request.getNome(), "categoria.nomenaopreenchido");		
	}

	public void hasCategoria(CategoriaRequestDTO request) throws CategoriaExistentException{
		try{
			if (findByName(request.getNome()) != null) {
				logger.debug(String.format("Categoria já cadastrada na base de id=%s", request.getId()));
				throw new CategoriaExistentException();
			}
		}catch(CategoriaNotFoundException exception){
		
		}
	}
	
	@Override
	public CategoriaResponseDTO findByName(String nome) throws CategoriaNotFoundException{
		for (CategoriaDocument document : mockdb.getCategoriaCollection().values()) {
			if (document.getNome().equals(nome)) {
				return (CategoriaResponseDTO) ConverterHelper.convert(document,CategoriaResponseDTO.class);
			}
		}
		logger.info("Categoria não encontrada");
		throw new CategoriaNotFoundException();
	}

	@Override
	public CategoriaResponseDTO findById(Long id) throws CategoriaNotFoundException{
		for (CategoriaDocument document : mockdb.getCategoriaCollection().values()) {
			if (document.getId().equals(id)) {
				return (CategoriaResponseDTO) ConverterHelper.convert(document,CategoriaResponseDTO.class);
			}
		}
		logger.info("Categoria não encontrada");
		throw new CategoriaNotFoundException();
	}

	
	@Override
	public void delete(CategoriaRequestDTO request) throws CategoriaNotFoundException {
		findByName(request.getNome());
		CategoriaDocument document = (CategoriaDocument) ConverterHelper.convert(request, CategoriaDocument.class);
		document.setDataExcluido(new Date());
		mockdb.getCategoriaCollection().put(document.getId(), document);
	}

	@Override
	public void update(CategoriaRequestDTO request) throws CategoriaNotFoundException {
		findById(request.getId());
		prepareSave(request);
		CategoriaDocument document = (CategoriaDocument) ConverterHelper.convert(request, CategoriaDocument.class);
		mockdb.getCategoriaCollection().put(document.getId(), document);
	}

	@Override
	public Collection<CategoriaResponseDTO> All() throws CategoriaNotFoundException {
		Collection<CategoriaDocument> categorias = mockdb.getCategoriaCollection().values();
		if(categorias.isEmpty()){
			throw new CategoriaNotFoundException("nenhuma.categoria.encontrada");			
		}		
		return ConverterHelper.convert(categorias, CategoriaResponseDTO.class);
	}
}
