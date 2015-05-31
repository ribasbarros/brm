package br.com.brm.scp.mock.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.fw.helper.objects.RandomHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;

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
		usuarioDocument.setNome(request.getNome());
		usuarioDocument.setCargo(request.getCargo());
		usuarioDocument.setEmail(request.getEmail());
		return usuarioDocument;
	}

	@Override
	public UsuarioResponseDTO find(Long id) throws UsuarioNotFoundException {

		Assert.notNull(id, "brm.usuario.id");
		
		UsuarioDocument usuarioDocument = findById(id);
		if(usuarioDocument == null){
			throw new UsuarioNotFoundException("brm.usuario.notfound");
		}
		
		return convert2Response(usuarioDocument);
		
	}

	public UsuarioDocument findById(Long id) throws UsuarioNotFoundException {
		UsuarioDocument usuarioDocument = dbMock.getUsuarioCollection().get(id);
		
		if(usuarioDocument == null){
			throw new UsuarioNotFoundException("brm.usuario.notfound");
		}
		
		return usuarioDocument;
	}
	
	private void insert(UsuarioDocument usuarioDocument) {
		
		usuarioDocument.setId(RandomHelper.UUID());
		
		dbMock.getUsuarioCollection().put(usuarioDocument.getId(), usuarioDocument);
	}

	@Override
	public UsuarioResponseDTO update(UsuarioRequestDTO request) throws UsuarioNotFoundException {
		
		Assert.notNull(request, "brm.usuario.notnull");
		Assert.notNull(request.getId(), "brm.usuario.id");
		Assert.notNull(request.getNome(), "brm.usuario.name");
		Assert.notNull(request.getEmail(), "brm.usuario.email");
		Assert.notNull(request.getCargo(), "brm.usuario.cargo");
		
		Long id = request.getId();
		
		UsuarioDocument document = findById(id);
		document.setId(id);
		document.setNome(request.getNome());
		document.setCargo(request.getCargo());
		document.setEmail(request.getEmail());
		
		update(id, document);
		
		UsuarioDocument reload = findById(request.getId());
		
		return convert2Response(reload);
	}

	private void update(Long id, UsuarioDocument document) {
		dbMock.getUsuarioCollection().put(document.getId(), document);
	}

	@Override
	public void clearMemory() {
		dbMock.getUsuarioCollection().clear();
	}

	@Override
	public Collection<UsuarioResponseDTO> all() throws UsuarioNotFoundException {
		
		Collection<UsuarioDocument> all = findAll();
		
		if(all.isEmpty()){
			throw new UsuarioNotFoundException("brm.usuario.listNotFound");
		}
		
		return convertListDocument2ListResponse(all);
	}

	private Collection<UsuarioDocument> findAll() {
		return dbMock.getUsuarioCollection().values();
	}

	private Collection<UsuarioResponseDTO> convertListDocument2ListResponse(Collection<UsuarioDocument> all) {
		Collection<UsuarioResponseDTO> result = new ArrayList<UsuarioResponseDTO>();
		
		for(UsuarioDocument document : all){
			result.add(convert2Response(document));
		}
		
		return result;
	}
	/*
	 * TODO VALIDAR DATA DELETADO!! - RIBAMAR
	 * 
	 */
	@Override
	public void deletar(Long id) throws UsuarioNotFoundException {
		Assert.notNull(id);
		UsuarioResponseDTO usuario = find(id);
		usuario.setDataDeletado(new Date());
		update(convert2Request(usuario));		 
	}
	
	private UsuarioRequestDTO convert2Request(UsuarioResponseDTO usuarioResponse){
		UsuarioRequestDTO usuarioRequest = new UsuarioRequestDTO();
		usuarioRequest.setId(usuarioResponse.getId());
		usuarioRequest.setNome(usuarioResponse.getNome());
		usuarioRequest.setEmail(usuarioResponse.getEmail());
		usuarioRequest.setCargo(usuarioResponse.getCargo());
		usuarioRequest.setGrupoPerfilResponse(usuarioResponse.getGrupoPerfilResponse());
		usuarioRequest.setDataDeletado(usuarioResponse.getDataDeletado());
		return usuarioRequest;
	}

}
