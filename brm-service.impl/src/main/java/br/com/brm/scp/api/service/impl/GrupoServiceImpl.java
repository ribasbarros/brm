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

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.GrupoService;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.GrupoDocument;
import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;
import br.com.brm.scp.api.service.repositories.GrupoRepository;
import br.com.brm.scp.api.service.status.GrupoFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

@Service
public class GrupoServiceImpl implements GrupoService {
	@Autowired
	GrupoRepository repository;
	@Autowired
	UsuarioService usuarioService;
	
	private static Logger logger = Logger.getLogger(GrupoServiceImpl.class);
	private static final String GRUPO_NOME = "grupo.nome";
	private static final String GRUPO_ID = "grupo.id";
	private static final String GRUPO_NULL = "grupo.null";
	private static final String GRUPO_EXISTENTE = "grupo.existente";
	private static final String GRUPO_NOTFOUND = "grupo.notfound";
	private static final String GRUPO_FILTRO = "grupo.filtro";

	@Override
	public GrupoResponseDTO create(GrupoRequestDTO request)
			throws GrupoExistenteException {
		Assert.notNull(request, GRUPO_NULL);
		Assert.isNull(request.getId(), GRUPO_ID);
		Assert.notNull(request.getNome(), GRUPO_NOME);

		try {
			hasRegister(request);
		} catch (GrupoNotFoundException e) {
			logger.debug(String
					.format("Grupo nao encontrado, pronto para cadastro!"));
		}
				
		GrupoDocument document = invokeDocument(request);
		document.setDataCriacao(new Date());
		document.setUsuarioCriacao((UsuarioDocument) ConverterHelper.convert(usuarioService.getUsuarioLogado(),UsuarioDocument.class));
		document = repository.save(document);
		GrupoResponseDTO response = invokeResponse(document);
		
		return response;
	}

	private boolean hasRegister(GrupoRequestDTO request)
			throws GrupoExistenteException,	GrupoNotFoundException {
		if (find(GrupoFiltroEnum.NOME, request.getNome()) != null)
			throw new GrupoExistenteException(GRUPO_EXISTENTE);
		
		return false;
	}

	public GrupoDocument invokeDocument(GrupoRequestDTO request) {
		return (GrupoDocument) ConverterHelper.convert(request,
				GrupoDocument.class);
	}

	public GrupoResponseDTO invokeResponse(GrupoDocument request) {
		return (GrupoResponseDTO) ConverterHelper.convert(request,
				GrupoResponseDTO.class);
	}

	@Override
	public void delete(String id) throws GrupoNotFoundException {
		Assert.notNull(id, GRUPO_ID);

		if (find(GrupoFiltroEnum.ID,id) == null){
			throw new GrupoNotFoundException(GRUPO_NOTFOUND);
		}
		
		repository.delete(id);

	}

	@Override
	public GrupoResponseDTO update(GrupoRequestDTO request) throws GrupoNotFoundException {
		Assert.notNull(request, GRUPO_NULL);
		Assert.notNull(request.getId(), GRUPO_ID);
		Assert.notNull(request.getNome(), GRUPO_NOME);

		if (find(GrupoFiltroEnum.ID, request.getId()) == null) {
			throw new GrupoNotFoundException(GRUPO_NOTFOUND);
		}

		GrupoDocument document = invokeDocument(request);
		document.setDataAlteracao(new Date());
		return invokeResponse(repository.save(document));
	}

	@Override
	public GrupoResponseDTO find(GrupoFiltroEnum filtro, Object value)
			throws GrupoNotFoundException {
		GrupoDocument document = findByFiltro(filtro, value);
		if(document.getPerfis() != null){
			document.setPerfis(new ArrayList<PerfilDocument>(document.getPerfis()));
		}
		return invokeResponse(document);
	}

	private GrupoDocument findByFiltro(GrupoFiltroEnum filtro, Object value)
			throws GrupoNotFoundException {

		Assert.notNull(filtro, GRUPO_FILTRO);

		GrupoDocument document = null;

		if (GrupoFiltroEnum.ID.equals(filtro)) {

			document = repository.findById((String) value);

		} else if (GrupoFiltroEnum.NOME.equals(filtro)) {

			document = repository.findByNome((String) value);

		}

		if (document == null)
			throw new GrupoNotFoundException(GRUPO_NOTFOUND);

		return document;
	}

	@Override
	public Pageable<GrupoResponseDTO> search(String searchTerm, int pageIndex,
			int size) throws GrupoNotFoundException {

		Page<GrupoDocument> requestedPage = repository.findByNomeAndPerfil(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<GrupoDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new GrupoNotFoundException(GRUPO_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<GrupoResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<GrupoResponseDTO>(response, sizePage, totalPages,
				pageIndex);
	}
	
	private Collection<GrupoResponseDTO> invokeResponse(
			Collection<GrupoDocument> result) {
		return ConverterHelper.convert(result, GrupoResponseDTO.class);
	}

	@Override
	public Pageable<GrupoResponseDTO> all(int pageIndex, int size)
			throws GrupoNotFoundException {
		Page<GrupoDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<GrupoDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new GrupoNotFoundException(GRUPO_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();
		
		for(GrupoDocument g : result){
			if(g.getPerfis() != null){				
				g.setPerfis(new ArrayList<PerfilDocument>(g.getPerfis()));
			}
		}

		Collection<GrupoResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<GrupoResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);
	}

	@Override
	public Collection<GrupoResponseDTO> all() {
		return invokeResponse(repository.findAll());
	}	
}
