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
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
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
	public UsuarioResponseDTO create(UsuarioRequestDTO request)
			throws UsuarioExistentException {
		Assert.notNull(request, "brm.usuario.notnull");
		Assert.notNull(request.getNome(), "brm.usuario.name");
		Assert.notNull(request.getEmail(), "brm.usuario.email");

		if (findByEmail(request.getEmail()) != null) {
			throw new UsuarioExistentException("brm.usuario.existente");
		}

		UsuarioDocument usuarioDocument = (UsuarioDocument) ConverterHelper.convert(request, UsuarioDocument.class);
		insert(usuarioDocument);
		UsuarioResponseDTO response = (UsuarioResponseDTO) ConverterHelper.convert(usuarioDocument, UsuarioResponseDTO.class);
		return response;

	}

	private UsuarioResponseDTO findByEmail(String email) {
		for (UsuarioDocument document : dbMock.getUsuarioCollection().values())
			if (email.equals(document.getEmail()))
				return (UsuarioResponseDTO) ConverterHelper.convert(document, UsuarioResponseDTO.class);

		return null;
	}

	@Override
	public UsuarioResponseDTO find(Long id) throws UsuarioNotFoundException {

		Assert.notNull(id, "brm.usuario.id");

		UsuarioDocument usuarioDocument = findById(id);
		if (usuarioDocument == null) {
			throw new UsuarioNotFoundException("brm.usuario.notfound");
		}

		return (UsuarioResponseDTO) ConverterHelper.convert(usuarioDocument, UsuarioResponseDTO.class);

	}

	public UsuarioDocument findById(Long id) throws UsuarioNotFoundException {
		UsuarioDocument usuarioDocument = dbMock.getUsuarioCollection().get(id);

		if (usuarioDocument == null) {
			throw new UsuarioNotFoundException("brm.usuario.notfound");
		}

		return usuarioDocument;
	}

	private void insert(UsuarioDocument usuarioDocument) {

		usuarioDocument.setId(RandomHelper.UUID());

		dbMock.getUsuarioCollection().put(usuarioDocument.getId(),
				usuarioDocument);
	}

	@Override
	public UsuarioResponseDTO update(UsuarioRequestDTO request)
			throws UsuarioNotFoundException {

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

		return (UsuarioResponseDTO) ConverterHelper.convert(document, UsuarioResponseDTO.class);
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

		if (all.isEmpty()) {
			throw new UsuarioNotFoundException("brm.usuario.listNotFound");
		}

		return ConverterHelper.convert(all, UsuarioResponseDTO.class);
	}

	private Collection<UsuarioDocument> findAll() {
		return dbMock.getUsuarioCollection().values();
	}

	/*
	 * TODO VALIDAR DATA DELETADO!! - RIBAMAR
	 */
	@Override
	public void deletar(Long id) throws UsuarioNotFoundException {
		Assert.notNull(id);
		UsuarioResponseDTO usuario = find(id);
		usuario.setDataDeletado(new Date());
		update(id , (UsuarioDocument) ConverterHelper.convert(usuario, UsuarioDocument.class));
	}

}
