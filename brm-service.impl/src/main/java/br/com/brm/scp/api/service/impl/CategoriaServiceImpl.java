package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.CategoriaService;
import br.com.brm.scp.api.service.document.CategoriaDocument;
import br.com.brm.scp.api.service.repositories.CategoriaRepository;
import br.com.brm.scp.api.service.status.CategoriaFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	private static Logger logger = Logger.getLogger(CategoriaServiceImpl.class);
	private static final String CATEGORIA_NOME = "categoria.nome";
	private static final String CATEGORIA_ID = "categoria.id";
	private static final String CATEGORIA_NULL = "categoria.null";
	private static final String CATEGORIA_EXISTENTE = "categoria.existente";
	private static final String CATEGORIA_NOTFOUND = "categoria.notfound";
	private static final String CATEGORIA_FILTRO = "categoria.filtro";

	@Override
	public CategoriaResponseDTO create(CategoriaRequestDTO request) throws CategoriaExistenteException {
		Assert.notNull(request, CATEGORIA_NULL);
		Assert.isNull(request.getId(), CATEGORIA_ID);
		Assert.notNull(request.getNome(), CATEGORIA_NOME);

		try {
			hasRegister(request);
		} catch (CategoriaNotFoundException e) {
			logger.debug(String.format("Categoria nao encontrado, pronto para cadastro!"));
		}

		CategoriaDocument document = invokeDocument(request);
		document = repository.save(document);

		return invokeResponse(document);
	}

	private CategoriaDocument invokeDocument(CategoriaRequestDTO request) {
		return (CategoriaDocument) ConverterHelper.convert(request, CategoriaDocument.class);
	}

	private CategoriaResponseDTO invokeResponse(CategoriaDocument document) {
		return (CategoriaResponseDTO) ConverterHelper.convert(document, CategoriaResponseDTO.class);
	}

	@Override
	public void delete(String id) throws CategoriaNotFoundException {
		Assert.notNull(id, CATEGORIA_ID);

		if (find(CategoriaFiltroEnum.ID, id) == null) {
			throw new CategoriaNotFoundException(CATEGORIA_NOTFOUND);
		}

		repository.delete(id);
	}

	@Override
	public void update(CategoriaRequestDTO request) throws CategoriaNotFoundException {
		Assert.notNull(request, CATEGORIA_NULL);
		Assert.notNull(request.getId(), CATEGORIA_ID);
		Assert.notNull(request.getNome(), CATEGORIA_NOME);

		if (find(CategoriaFiltroEnum.ID, request.getId()) == null) {
			throw new CategoriaNotFoundException(CATEGORIA_NOTFOUND);
		}

		CategoriaDocument document = invokeDocument(request);

		repository.save(document);
	}

	@Override
	public CategoriaResponseDTO find(CategoriaFiltroEnum filtro, Object value) throws CategoriaNotFoundException {
		CategoriaDocument document = findByFiltro(filtro, value);
		return invokeResponse(document);
	}

	private boolean hasRegister(CategoriaRequestDTO request)
			throws CategoriaExistenteException, CategoriaNotFoundException {
		if (find(CategoriaFiltroEnum.NOME, request.getNome()) != null)
			throw new CategoriaExistenteException(CATEGORIA_EXISTENTE);

		return false;
	}

	private CategoriaDocument findByFiltro(CategoriaFiltroEnum filtro, Object value) throws CategoriaNotFoundException {

		Assert.notNull(filtro, CATEGORIA_FILTRO);

		CategoriaDocument document = null;

		if (CategoriaFiltroEnum.ID.equals(filtro)) {

			document = repository.findById((String) value);

		} else if (CategoriaFiltroEnum.NOME.equals(filtro)) {

			document = repository.findByNome((String) value);

		}

		if (document == null)
			throw new CategoriaNotFoundException(CATEGORIA_NOTFOUND);

		return document;
	}

	@Override
	public Pageable<CategoriaResponseDTO> all(int pageIndex, int size) throws CategoriaNotFoundException {
		Page<CategoriaDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<CategoriaDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new CategoriaNotFoundException(CATEGORIA_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		Collection<CategoriaResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<CategoriaResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);
	}

	private Collection<CategoriaResponseDTO> invokeResponse(Collection<CategoriaDocument> result) {
		return ConverterHelper.convert(result, CategoriaResponseDTO.class);
	}
	
	@Override
	public Pageable<CategoriaResponseDTO> search(String searchTerm, int pageIndex, int size)
			throws CategoriaNotFoundException {
		Page<CategoriaDocument> requestedPage = repository.findByName(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<CategoriaDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new CategoriaNotFoundException(CATEGORIA_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<CategoriaResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<CategoriaResponseDTO>(response, sizePage, totalPages,
				pageIndex);
	}

}
