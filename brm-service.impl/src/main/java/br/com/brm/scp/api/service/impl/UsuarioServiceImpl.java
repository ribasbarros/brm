package br.com.brm.scp.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.UsuarioDocument;
import br.com.brm.scp.api.service.repositories.UsuarioRepository;
import br.com.brm.scp.api.service.status.UsuarioFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.validators.EmailValidator;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository repository;

	private static Logger logger = Logger.getLogger(UsuarioServiceImpl.class);

	private static final String USUARIO_NOTNULL = "usuario.notnull";
	private static final String USUARIO_NOME = "usuario.nome";
	private static final String USUARIO_EMAIL = "usuario.email";
	private static final String USUARIO_EMAILINVALIDO = "usuario.emailinvalido";
	private static final String USUARIO_CARGO = "usuario.cargo";
	private static final String USUARIO_GRUPO = "usuario.grupo";
	private static final String USUARIO_NOTFOUND = "usuario.notfound";
	private static final String USUARIO_FILTRO = "usuario.filtro";
	private static final String USUARIO_ID = "usuario.id";
	private static final String USUARIO_EXISTENTE = "usuario.existente";

	@Override
	public UsuarioResponseDTO create(UsuarioRequestDTO request)
			throws UsuarioExistentException {
		Assert.notNull(request, USUARIO_NOTNULL);
		Assert.notNull(request.getNome(), USUARIO_NOME);
		Assert.notNull(request.getCargo(), USUARIO_CARGO);
		Assert.notNull(request.getGrupos(), USUARIO_GRUPO);
		Assert.notNull(request.getEmail(), USUARIO_EMAIL);
		Assert.isNull(request.getId(), USUARIO_EMAIL);
		Assert.isTrue(EmailValidator.isEmail(request.getEmail()),
				USUARIO_EMAILINVALIDO);

		try {
			hasRegister(request);
		} catch (UsuarioNotFoundException e) {
			logger.debug(String
					.format("Perfil nao encontrado, pronto para cadastro!"));
		}
		UsuarioDocument document = invokeDocument(request);
		document = repository.save(document);

		UsuarioResponseDTO response = invokeResponse(document);

		return response;
	}

	private UsuarioDocument invokeDocument(UsuarioRequestDTO request) {
		UsuarioDocument document = (UsuarioDocument) ConverterHelper.convert(
				request, UsuarioDocument.class);
		return document;
	}

	private UsuarioResponseDTO invokeResponse(UsuarioDocument document) {
		return (UsuarioResponseDTO) ConverterHelper.convert(document,
				UsuarioResponseDTO.class);
	}

	private boolean hasRegister(UsuarioRequestDTO request)
			throws UsuarioExistentException, UsuarioNotFoundException {
		if (find(UsuarioFiltroEnum.NOME, request.getNome()) != null)
			throw new UsuarioExistentException(USUARIO_EXISTENTE);

		return false;
	}

	@Override
	public void update(UsuarioRequestDTO request)
			throws UsuarioNotFoundException {
		Assert.notNull(request.getId(), USUARIO_ID);
		Assert.notNull(request, USUARIO_NOTNULL);
		Assert.notNull(request.getNome(), USUARIO_NOME);
		Assert.notNull(request.getCargo(), USUARIO_CARGO);
		Assert.notNull(request.getGrupos(), USUARIO_GRUPO);
		Assert.notNull(request.getEmail(), USUARIO_EMAIL);
		Assert.isTrue(EmailValidator.isEmail(request.getEmail()),
				USUARIO_EMAILINVALIDO);

		if (find(UsuarioFiltroEnum.ID, request.getId()) == null) {
			throw new UsuarioNotFoundException(USUARIO_NOTFOUND);
		}

		repository.save(invokeDocument(request));
	}

	@Override
	public void delete(String id)
			throws UsuarioNotFoundException {
		Assert.notNull(id, USUARIO_ID);
		
		if (find(UsuarioFiltroEnum.ID, id) == null) {
			throw new UsuarioNotFoundException(USUARIO_NOTFOUND);
		}
		
		repository.delete(id);
		
	}

	@Override
	public UsuarioResponseDTO find(UsuarioFiltroEnum filtro, Object value)
			throws UsuarioNotFoundException {
		UsuarioDocument document = findByFiltro(filtro, value);
		return invokeResponse(document);
	}

	private UsuarioDocument findByFiltro(UsuarioFiltroEnum filtro, Object value)
			throws UsuarioNotFoundException {

		Assert.notNull(filtro, USUARIO_FILTRO);

		UsuarioDocument document = null;

		if (UsuarioFiltroEnum.ID.equals(filtro)) {
			document = repository.findById((String) value);

		} else if (UsuarioFiltroEnum.NOME.equals(filtro)) {
			document = repository.findByNome((String) value);

		} else if (UsuarioFiltroEnum.EMAIL.equals(filtro)) {
			document = repository.findByEmail((String) value);
		}

		if (document == null)
			throw new UsuarioNotFoundException(USUARIO_NOTFOUND);

		return document;
	}

}
