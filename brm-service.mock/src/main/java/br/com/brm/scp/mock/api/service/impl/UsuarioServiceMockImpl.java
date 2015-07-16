package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;

public class UsuarioServiceMockImpl implements UsuarioService {
	private Logger logger = Logger.getLogger(CategoriaServiceMockImpl.class);

	private MockData mockdb;

	public UsuarioServiceMockImpl(MockData mockdb) {
		super();
		this.mockdb = mockdb;
	}

	@Override
	public UsuarioResponseDTO create(UsuarioRequestDTO request)	throws UsuarioExistentException {
		prepareSave(request);
		hasUsuario(request);
		return insert(request);
	}

	public void hasUsuario(UsuarioRequestDTO request) throws UsuarioExistentException {
		try {
			if (findByEmail(request.getEmail()) != null) {
				logger.debug(String.format("Usuario já cadastrado na base de id=%s",request.getId()));
				throw new UsuarioExistentException();
			}
		} catch (UsuarioNotFoundException exception) {
		}
	}

	public void prepareSave(UsuarioRequestDTO request) {
		Assert.notNull(request, "brm.usuario.notnull");
		Assert.notNull(request.getNome(), "brm.usuario.name");
		Assert.notNull(request.getEmail(), "brm.usuario.email");
	}


	public UsuarioResponseDTO insert(UsuarioRequestDTO request) {
		request.setId(new Long("1"));
		UsuarioDocument document = (UsuarioDocument) ConverterHelper.convert(request, UsuarioDocument.class);
		mockdb.getUsuarioCollection().put(document.getId(), document);
		return (UsuarioResponseDTO) ConverterHelper.convert(document,UsuarioResponseDTO.class);
	}

	private UsuarioResponseDTO findByEmail(String email) throws UsuarioNotFoundException {
		for (UsuarioDocument document : mockdb.getUsuarioCollection().values()){
			if (email.equals(document.getEmail())) {
				return (UsuarioResponseDTO) ConverterHelper.convert(document, UsuarioResponseDTO.class);
			}
		}
		throw new UsuarioNotFoundException();
	}

	@Override
	public UsuarioResponseDTO findById(Long id) throws UsuarioNotFoundException {
		for (UsuarioDocument document : mockdb.getUsuarioCollection().values()){
			if (id.equals(document.getId())) {
				return (UsuarioResponseDTO) ConverterHelper.convert(document, UsuarioResponseDTO.class);
			}
		}
		throw new UsuarioNotFoundException();
	}

	@Override
	public void update(UsuarioRequestDTO request) throws UsuarioNotFoundException {
		prepareSave(request);
		findById(request.getId());
		UsuarioDocument document = (UsuarioDocument) ConverterHelper.convert(request, UsuarioDocument.class);
		mockdb.getUsuarioCollection().put(document.getId(), document);
	}

	@Override
	public void delete(UsuarioRequestDTO request) throws UsuarioNotFoundException {
		findById(request.getId());
		UsuarioDocument document = (UsuarioDocument) ConverterHelper.convert(request, UsuarioDocument.class);
		document.setDataExcluido(new Date());
		mockdb.getUsuarioCollection().put(document.getId(), document);

	}

	@Override
	public Collection<UsuarioResponseDTO> all() throws UsuarioNotFoundException {
		Collection<UsuarioDocument> usuarios = mockdb.getUsuarioCollection().values();
		if(usuarios.isEmpty()){
			throw new UsuarioNotFoundException("nenhuma.categoria.encontrada");			
		}		
		return ConverterHelper.convert(usuarios, UsuarioResponseDTO.class);
	}
	

	@Override
	public UsuarioResponseDTO findByName(String nome) throws UsuarioNotFoundException{
		for (UsuarioDocument document : mockdb.getUsuarioCollection().values()){
			if (nome.equals(document.getNome())) {
				return (UsuarioResponseDTO) ConverterHelper.convert(document, UsuarioResponseDTO.class);
			}	
		}
		throw new UsuarioNotFoundException();
	}
}
