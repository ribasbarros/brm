package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.PerfilService;
import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.repositories.PerfilRepository;
import br.com.brm.scp.api.service.status.PerfilFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

@Service
public class PerfilServiceImpl implements PerfilService {
	@Autowired
	PerfilRepository repository;

	private static Logger logger = Logger.getLogger(PerfilServiceImpl.class);
	private static final String PERFIL_NOME = "perfil.nome";
	private static final String PERFIL_ID = "perfil.id";
	private static final String PERFIL_NULL = "perfil.null";
	private static final String PERFIL_EXISTENTE = "perfil.existente";
	private static final String PERFIL_NOTFOUND = "perfil.notfound";
	private static final String PERFIL_FILTRO = "perfil.filtro";

	@Override
	public PerfilResponseDTO create(PerfilRequestDTO request)
			throws PerfilExistenteException {
		Assert.notNull(request, PERFIL_NULL);
		Assert.isNull(request.getId(), PERFIL_ID);
		Assert.notNull(request.getNome(), PERFIL_NOME);

		try {
			hasRegister(request);
		} catch (PerfilNotFoundException e) {
			logger.debug(String
					.format("Perfil nao encontrado, pronto para cadastro!"));
		}
		
		PerfilDocument document = invokeDocument(request);

		document = repository.save(document);

		PerfilResponseDTO response = invokeResponse(document);

		return response;
	}

	private boolean hasRegister(PerfilRequestDTO request)
			throws PerfilExistenteException, PerfilNotFoundException {
		if (find(PerfilFiltroEnum.NOME,request.getNome()) != null)
			throw new PerfilExistenteException(PERFIL_EXISTENTE);
		return false;
	}

	private PerfilResponseDTO invokeResponse(PerfilDocument document) {
		return (PerfilResponseDTO) ConverterHelper.convert(document,
				PerfilResponseDTO.class);
	}

	private PerfilDocument invokeDocument(PerfilRequestDTO request) {
		return (PerfilDocument) ConverterHelper.convert(request,
				PerfilDocument.class);
	}

	@Override
	public void delete(String id) throws PerfilNotFoundException {
		Assert.notNull(id, PERFIL_ID);

		if (find(PerfilFiltroEnum.ID,id) == null){
			throw new PerfilNotFoundException(PERFIL_NOTFOUND);
		}
		
		repository.delete(id);
	}

	@Override
	public void update(PerfilRequestDTO request) throws PerfilNotFoundException {
		Assert.notNull(request, PERFIL_NULL);
		Assert.notNull(request.getId(), PERFIL_ID);
		Assert.notNull(request.getNome(), PERFIL_NOME);

		if (find(PerfilFiltroEnum.ID, request.getId()) == null) {
			throw new PerfilNotFoundException(PERFIL_NOTFOUND);
		}

		PerfilDocument document = invokeDocument(request);

		repository.save(document);
	}

	private PerfilDocument findByFiltro(PerfilFiltroEnum filtro, Object value)
			throws PerfilNotFoundException {

		Assert.notNull(filtro, PERFIL_FILTRO);

		PerfilDocument document = null;

		if (PerfilFiltroEnum.ID.equals(filtro)) {
			
			document = repository.findById((String) value);

		} else if (PerfilFiltroEnum.NOME.equals(filtro)) {

			document = repository.findByNome((String) value);
		
		}

		if (document == null)
			throw new PerfilNotFoundException(PERFIL_NOTFOUND);

		return document;
	}

	@Override
	public PerfilResponseDTO find(PerfilFiltroEnum filtro, Object value)
			throws PerfilNotFoundException {
		PerfilDocument document = findByFiltro(filtro, value);
		return invokeResponse(document);
	}

	@Override
	public Pageable<PerfilResponseDTO> search(String searchTerm, int pageIndex,
			int size) throws PerfilNotFoundException {
		
		Page<PerfilDocument> requestedPage = repository.findByNome(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<PerfilDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new PerfilNotFoundException(PERFIL_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<PerfilResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<PerfilResponseDTO>(response, sizePage, totalPages,
				pageIndex);
	}

	private Collection<PerfilResponseDTO> invokeResponse(
			Collection<PerfilDocument> result) {
		return ConverterHelper.convert(result, PerfilResponseDTO.class);
	}

	@Override
	public Pageable<PerfilResponseDTO> all(int pageIndex, int size)
			throws PerfilNotFoundException {
		Page<PerfilDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<PerfilDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new PerfilNotFoundException(PERFIL_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		Collection<PerfilResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<PerfilResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);
	}

	@Override
	public Collection<PerfilResponseDTO> all() throws PerfilNotFoundException {
		return invokeResponse(repository.findAll());
	}

}
