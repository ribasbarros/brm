package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.TagRequestDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.TagExistenteException;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.repositories.TagRepository;
import br.com.brm.scp.api.service.status.TagFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

@Service
public class TagServiceImpl implements TagService{
	@Autowired
	TagRepository repository;
	
	private static Logger logger = Logger.getLogger(TagServiceImpl.class);
	private static final String TAG_NOME = "tag.nome";
	private static final String TAG_ID = "tag.id";
	private static final String TAG_NULL = "tag.null";
	private static final String TAG_NIVEL = "tag.nivel";
	private static final String TAG_EXISTENTE = "tag.existente";
	private static final String TAG_NOTFOUND = "tag.notfound";
	private static final String TAG_FILTRO = "tag.filtro";
	
	@Override
	public Collection<TagResponseDTO> selecionar(TagResponseDTO tag,
			Collection<TagResponseDTO> selecionadas) {
		return null;
	}


	@Override
	public TagResponseDTO create(TagRequestDTO request)
			throws TagExistenteException {
		Assert.notNull(request, TAG_NULL);
		Assert.isNull(request.getId(), TAG_ID);
		Assert.notNull(request.getNome(), TAG_NOME);
		Assert.notNull(request.getNivel(), TAG_NIVEL);
		
		try {
			hasRegister(request);
		} catch (TagNotFoundException e) {
			logger.debug(String
					.format("Tag nao encontrado, pronto para cadastro!"));
		}
		
		TagDocument document = repository.save(invokeDocument(request));
		
		return invokeResponse(document);
	}

	
	private boolean hasRegister(TagRequestDTO request)
			throws TagExistenteException, TagNotFoundException {
		if (find(TagFiltroEnum.NOME,request.getNome()) != null)
			throw new TagExistenteException(TAG_EXISTENTE);
		return false;
	}
	
	@Override
	public void update(TagRequestDTO request) throws TagNotFoundException {
		Assert.notNull(request, TAG_NULL);
		Assert.notNull(request.getId(), TAG_ID);
		Assert.notNull(request.getNome(), TAG_NOME);

		if (find(TagFiltroEnum.ID, request.getId()) == null) {
			throw new TagNotFoundException(TAG_NOTFOUND);
		}

		TagDocument document = invokeDocument(request);

		repository.save(document);
	}

	@Override
	public TagResponseDTO find(TagFiltroEnum filtro, Object value)
			throws TagNotFoundException {
		TagDocument document = findByFiltro(filtro, value);
		return invokeResponse(document);
	}

	@Override
	public void delete(String id) throws TagNotFoundException {
		Assert.notNull(id, TAG_ID);

		if (find(TagFiltroEnum.ID,id) == null){
			throw new TagNotFoundException(TAG_NOTFOUND);
		}
				
		repository.delete(id);	
	}
	
	private TagDocument findByFiltro(TagFiltroEnum filtro, Object value)
			throws TagNotFoundException {

		Assert.notNull(filtro, TAG_FILTRO);

		TagDocument document = null;

		if (TagFiltroEnum.ID.equals(filtro)) {
			
			document = repository.findById((String) value);

		} else if (TagFiltroEnum.NOME.equals(filtro)) {

			document = repository.findByNome((String) value);
		
		}

		if (document == null)
			throw new TagNotFoundException(TAG_NOTFOUND);

		return document;
	}
	
	private TagResponseDTO invokeResponse(TagDocument document){
		return (TagResponseDTO) ConverterHelper.convert(document, TagResponseDTO.class);
	}

	private TagDocument invokeDocument(TagRequestDTO request){
		return (TagDocument) ConverterHelper.convert(request, TagDocument.class);
	}


	@Override
	public Pageable<TagResponseDTO> all(int pageIndex, int size) throws TagNotFoundException {
		Page<TagDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<TagDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new TagNotFoundException(TAG_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		Collection<TagResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<TagResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);
	}

	private Collection<TagResponseDTO> invokeResponse(Collection<TagDocument> result) {
		return ConverterHelper.convert(result, TagResponseDTO.class);
	}
	
	@Override
	public Pageable<TagResponseDTO> search(String searchTerm, int pageIndex, int size)
			throws TagNotFoundException {
		Page<TagDocument> requestedPage = repository.findByName(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<TagDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new TagNotFoundException(TAG_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<TagResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<TagResponseDTO>(response, sizePage, totalPages,
				pageIndex);
	}


	@Override
	public Collection<TagResponseDTO> all() {
		return invokeResponse(repository.findAll());
	}
}
