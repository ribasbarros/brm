package br.com.brm.scp.api.service.impl;

import java.util.UUID;

import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.mockdata.MockData;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class UsuarioServiceMockImpl implements UsuarioService {

	private MockData dbMock;
	
	public UsuarioServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	@Override
	public UsuarioResponseDTO create(UsuarioRequestDTO request) throws UsuarioExistentException {
		
		Assert.notNull(request, "brm.usuario.notnull");
		Assert.notNull(request.getNome(), "brm.usuario.name");
		Assert.notNull(request.getEmail(), "brm.usuario.email");
		
		if(findByEmail(request.getEmail()) != null)
			throw new UsuarioExistentException("brm.usuario.existente");
		
		UsuarioDocument usuarioDocument = convert2Document(request);
		
		insert(usuarioDocument);
		
		UsuarioResponseDTO response = convert2Response(usuarioDocument);
		
		return response;
		
	}

	private UsuarioResponseDTO findByEmail(String email) {
		for( UsuarioDocument document : dbMock.getUsuarioCollection().values())
			if(email.equals(document.getEmail()))
				return convert2Response(document);
		
		return null;
	}

	private UsuarioResponseDTO convert2Response(UsuarioDocument usuarioDocument) {
		UsuarioResponseDTO response = new UsuarioResponseDTO();
		response.setId(usuarioDocument.getId());
		response.setNome(usuarioDocument.getNome());
		response.setCargo(usuarioDocument.getCargo());
		response.setEmail(usuarioDocument.getEmail());
		return response;
	}

	private UsuarioDocument convert2Document(UsuarioRequestDTO request) {
		UsuarioDocument usuarioDocument = new UsuarioDocument();
		usuarioDocument.setId(UUID.randomUUID().getMostSignificantBits());
		usuarioDocument.setNome(request.getNome());
		usuarioDocument.setCargo(request.getCargo());
		usuarioDocument.setEmail(request.getEmail());
		return usuarioDocument;
	}

	@Override
	public UsuarioResponseDTO find(Long id) throws UsuarioNotFoundException {

		Assert.notNull(id, "brm.usuario.id");
		
		UsuarioDocument usuarioDocument = findById(id);
		
		return convert2Response(usuarioDocument);
		
	}

	private UsuarioDocument findById(Long id) {
		UsuarioDocument usuarioDocument = dbMock.getUsuarioCollection().get(id);
		return usuarioDocument;
	}
	
	private void insert(UsuarioDocument usuarioDocument) {
		dbMock.getUsuarioCollection().put(usuarioDocument.getId(), usuarioDocument);
	}

	@Override
	public UsuarioResponseDTO update(UsuarioRequestDTO usuarioSuccess) {
		UsuarioDocument document = findById(usuarioSuccess.getId());
		
		
		
	}

}
