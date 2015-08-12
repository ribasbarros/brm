package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.service.PerfilService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.PerfilDocument;

public class PerfilServiceMockImpl implements PerfilService {
	private Logger logger = Logger.getLogger(PerfilServiceMockImpl.class);

	private MockData mockdb;

	public PerfilServiceMockImpl(MockData mockdb) {
		super();
		this.mockdb = mockdb;
	}
	
	@Override
	public PerfilResponseDTO create(PerfilRequestDTO request) throws PerfilExistenteException {
		prepareSave(request);
		hasPerfil(request);
		return insert(request);
	}
	
	public PerfilResponseDTO insert(PerfilRequestDTO request){
		request.setId(new Long("1"));
		PerfilDocument document = (PerfilDocument) ConverterHelper.convert(request, PerfilDocument.class);
		mockdb.getPerfilCollection().put(document.getId(), document);
		return (PerfilResponseDTO) ConverterHelper.convert(document, PerfilResponseDTO.class);
	}
	
	public void prepareSave(PerfilRequestDTO request){
		Assert.notNull(request.getNome(), "perfil.nomenaopreenchido");		
	}

	public void hasPerfil(PerfilRequestDTO request) throws PerfilExistenteException{
		try{
			if (findByName(request.getNome()) != null) {
				logger.debug(String.format("Perfil já cadastrada na base de id=%s", request.getId()));
				throw new PerfilExistenteException();
			}
		}catch(PerfilNotFoundException exception){
		
		}
	}
	
	@Override
	public PerfilResponseDTO findByName(String nome) throws PerfilNotFoundException{
		for (PerfilDocument document : mockdb.getPerfilCollection().values()) {
			if (document.getNome().equals(nome)) {
				return (PerfilResponseDTO) ConverterHelper.convert(document,PerfilResponseDTO.class);
			}
		}
		logger.info("Perfil não encontrado");
		throw new PerfilNotFoundException();
	}

	@Override
	public PerfilResponseDTO findById(Long id) throws PerfilNotFoundException{
		for (PerfilDocument document : mockdb.getPerfilCollection().values()) {
			if (document.getId().equals(id)) {
				return (PerfilResponseDTO) ConverterHelper.convert(document,PerfilResponseDTO.class);
			}
		}
		logger.info("Perfil não encontrado");
		throw new PerfilNotFoundException();
	}

	
	@Override
	public void delete(PerfilRequestDTO request) throws PerfilNotFoundException {
		findByName(request.getNome());
		PerfilDocument document = (PerfilDocument) ConverterHelper.convert(request, PerfilDocument.class);
		document.setDataExcluido(new Date());
		mockdb.getPerfilCollection().put(document.getId(), document);
	}

	@Override
	public void update(PerfilRequestDTO request) throws PerfilNotFoundException {
		findById(request.getId());
		prepareSave(request);
		PerfilDocument document = (PerfilDocument) ConverterHelper.convert(request, PerfilDocument.class);
		mockdb.getPerfilCollection().put(document.getId(), document);
	}

	@Override
	public Collection<PerfilResponseDTO> All() throws PerfilNotFoundException {
		Collection<PerfilDocument> Perfils = mockdb.getPerfilCollection().values();
		if(Perfils.isEmpty()){
			throw new PerfilNotFoundException("nenhuma.Perfil.encontrada");			
		}		
		return ConverterHelper.convert(Perfils, PerfilResponseDTO.class);
	}
}
