package br.com.brm.scp.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.api.service.status.SkuFiltroEnum;
import br.com.brm.scp.api.service.status.TagFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

/**
 * @author Ribas
 *
 */
@Service
public class SkuServiceImpl implements SkuService {

	private static Logger logger = Logger.getLogger(SkuServiceImpl.class);

	private static final String SKU_NOTNULL = "sku.notnull";
	private static final String SKU_ITEM = "sku.item";
	private static final String SKU_TAG = "sku.tag";
	private static final String SKU_DESCRICAO = "sku.descricao";
	private static final String SKU_FREQUENCIA_ANALISE = "sku.frequenciaanalise";
	private static final String SKU_MODELO = "sku.modelo";
	private static final String SKU_ORIGEM = "sku.origem";
	private static final String SKU_FILTRO = "sku.filtro";
	private static final String SKU_EXISTENTE = "sku.existente";
	private static final String SKU_NOTFOUND = "sku.notfound";
	private static final String SKU_ID = "sku.id";

	
	@Autowired
	private SkuRepository repository;
	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public SkuResponseDTO create(SkuRequestDTO request) throws SkuExistenteException {
		
		Assert.notNull(request, SKU_NOTNULL);
		Assert.notNull(request.getItem(), SKU_ITEM);
		Assert.notNull(request.getTags(), SKU_TAG);
		Assert.notNull(request.getDescricao(), SKU_DESCRICAO);
		Assert.notNull(request.getFrequenciaAnalise(), SKU_FREQUENCIA_ANALISE);
		Assert.notNull(request.getModelo(), SKU_MODELO);
		//Assert.notNull(request.getOrigens(), SKU_ORIGEM);
		
		hasSkuRegistered(request);
		
		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		document.setDataCriacao(new Date());
		document.setUsuarioCriacao((UsuarioDocument) ConverterHelper.convert(usuarioService.getUsuarioLogado(),UsuarioDocument.class));

		document = repository.save(document);
		
		SkuResponseDTO response = invokeResponse(document);
		
		return response;
		
	}

	private void hasSkuRegistered(SkuRequestDTO request) throws SkuExistenteException {
		ObjectId[] tags = request.toTagArray();
		String id = request.getItem().getId();
		
		SkuDocument document = repository.findSku(id, tags, tags.length);
		
		if(document != null){
			throw new SkuExistenteException(SKU_EXISTENTE);
		}
	}

	private SkuResponseDTO invokeResponse(SkuDocument document) {
		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
	}

	private Collection<SkuResponseDTO> invokeResponse(Collection<SkuDocument> collection) {
		return ConverterHelper.convert(collection, SkuResponseDTO.class);
	}

	@Override
	public SkuResponseDTO find(SkuFiltroEnum filtro, Object value) throws SkuNotFoundException {
		SkuDocument document = findByFiltro(filtro, value);	
		if(document.getTags() != null){
			document.setTags(new ArrayList<TagDocument>(document.getTags()));
		}
		if(document.getOrigens() != null){
			document.setOrigens(new ArrayList<OrigemSkuDocument>(document.getOrigens()));
		}	
		return invokeResponse(document);
	}

	private SkuDocument findByFiltro(SkuFiltroEnum filtro, Object value) throws SkuNotFoundException {
		
		Assert.notNull(filtro, SKU_FILTRO);
		
		SkuDocument document = null;
		if(SkuFiltroEnum.ID.equals(filtro))
			document = repository.findOne((String) value);
		
		if(document == null)
			throw new SkuNotFoundException(SKU_NOTFOUND);
		
		return document;
		
	}

	@Override
	public Pageable<SkuResponseDTO> all(int pageIndex, int size) throws SkuNotFoundException {

		Page<SkuDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<SkuDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new SkuNotFoundException(SKU_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();
		
		//TODO CRIAR SOLUCAO NO CONVERTER
		for(SkuDocument d : result){
			if(d.getTags() != null){
				d.setTags(new ArrayList<TagDocument>(d.getTags()));
			}
			if(d.getOrigens() != null){
				d.setOrigens(new ArrayList<OrigemSkuDocument>(d.getOrigens()));
			}
		}

		Collection<SkuResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<SkuResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);

	
	}

	@Override
	public Pageable<SkuResponseDTO> search(String searchTerm, int pageIndex, int size) throws SkuNotFoundException {

		//TODO Pesquisar por mais atributos!
		Page<SkuDocument> requestedPage = repository.findByDescricao(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<SkuDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new SkuNotFoundException(SKU_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<SkuResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<SkuResponseDTO>(response, sizePage, totalPages,
				pageIndex);

	
	}
	
	@Override
	public SkuResponseDTO update(SkuRequestDTO request) throws SkuNotFoundException {
		Assert.notNull(request, SKU_NOTNULL);
		Assert.notNull(request.getItem(), SKU_ITEM);
		Assert.notNull(request.getTags(), SKU_TAG);
		Assert.notNull(request.getDescricao(), SKU_DESCRICAO);
		Assert.notNull(request.getFrequenciaAnalise(), SKU_FREQUENCIA_ANALISE);
		Assert.notNull(request.getModelo(), SKU_MODELO);
		//Assert.notNull(request.getOrigens(), SKU_ORIGEM);
	
		if (find(SkuFiltroEnum.ID, request.getId()) == null) {
			throw new SkuNotFoundException(SKU_NOTFOUND);
		}

		SkuDocument document = invokeDocument(request);
		document.setDataAlteracao(new Date());
		return invokeResponse(repository.save(document));
		
	}

	private SkuDocument invokeDocument(SkuRequestDTO request) {
		return (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
	}

	@Override
	public void delete(String id) throws SkuNotFoundException {
		Assert.notNull(id, SKU_ID);

		if (find(SkuFiltroEnum.ID,id) == null){
			throw new SkuNotFoundException(SKU_NOTFOUND);
		}
				
		repository.delete(id);			
	}

	@Override
	public Collection<SkuResponseDTO> all() {
		// TODO Auto-generated method stub
		return invokeResponse(repository.findAll());
	}

}
