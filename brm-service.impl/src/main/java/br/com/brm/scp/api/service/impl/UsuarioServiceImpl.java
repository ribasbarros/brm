package br.com.brm.scp.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.GrupoDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;
import br.com.brm.scp.api.service.repositories.UsuarioRepository;
import br.com.brm.scp.api.service.status.UsuarioFiltroEnum;
import br.com.brm.scp.fw.helper.UsuarioLogadoHelper;
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
		document.setDataCriacao(new Date());
		document.setUsuarioCriacao((UsuarioDocument) ConverterHelper.convert(getUsuarioLogado(),UsuarioDocument.class));
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
	public UsuarioResponseDTO update(UsuarioRequestDTO request)
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
		UsuarioDocument document = invokeDocument(request);
		document.setDataAlteracao(new Date());
		return invokeResponse(repository.save(document));
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
		
		if(document.getGrupos() != null){
			document.setGrupos(new ArrayList<GrupoDocument>(document.getGrupos()));							
		}
		
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

	@Override
	public Pageable<UsuarioResponseDTO> search(String searchTerm,
			int pageIndex, int size) throws UsuarioNotFoundException {

		Page<UsuarioDocument> requestedPage = repository.findByNomeCargoEmailGrupos(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<UsuarioDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new UsuarioNotFoundException(USUARIO_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();
				
		Collection<UsuarioResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<UsuarioResponseDTO>(response, sizePage, totalPages,
				pageIndex);
	}

	private Collection<UsuarioResponseDTO> invokeResponse(
			Collection<UsuarioDocument> result) {
		return ConverterHelper.convert(result, UsuarioResponseDTO.class);
	}

	@Override
	public Pageable<UsuarioResponseDTO> all(int pageIndex, int size)
			throws UsuarioNotFoundException {
		Page<UsuarioDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<UsuarioDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new UsuarioNotFoundException(USUARIO_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		for(UsuarioDocument document : result){
			if(document.getGrupos() != null){
				document.setGrupos(new ArrayList<GrupoDocument>(document.getGrupos()));							
			}
		}
		
		Collection<UsuarioResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<UsuarioResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);
	}

	@Override
	public UsuarioResponseDTO getUsuarioLogado() {
		return invokeResponse(repository.findByLogin(UsuarioLogadoHelper.getUsuarioLogado().getUsername()));
	}

}
