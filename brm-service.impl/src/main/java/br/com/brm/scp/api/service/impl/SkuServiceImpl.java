package br.com.brm.scp.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.OrigemSkuResponseDTO;
import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotSuchMuchQuantityException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.api.service.status.SkuFiltroEnum;
import br.com.brm.scp.api.vo.PedidoVO;
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
	private static final String SKU_ITEM_NOTFOUND_IN_CHAIN = "sku.chain.notfound";
	private static final String SKU_ORIGEM_DEFAULT = "sku.set.default";

	private static final String SKU_NOTSUCHMUCH = "sku.notsuch";

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
		Assert.notNull(request.getOrigens(), SKU_ORIGEM);
		Assert.isTrue(isDefaultOrigem(request.getOrigens()), SKU_ORIGEM_DEFAULT);

		hasSkuRegistered(request);

		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		document.setEstoqueAtual(0);
		document.setEstoqueSeguranca(0);
		document.setEstoqueMaximo(0);
		document.setDataCriacao(new Date());
		document.setUsuarioCriacao(
				(UsuarioDocument) ConverterHelper.convert(usuarioService.getUsuarioLogado(), UsuarioDocument.class));

		document = repository.save(document);

		SkuResponseDTO response = invokeResponse(document);

		return response;

	}

	private boolean isDefaultOrigem(Collection<OrigemSkuResponseDTO> origens) {
		for (OrigemSkuResponseDTO dto : origens)
			if (dto.isPadrao())
				return true;

		return false;
	}

	private void hasSkuRegistered(SkuRequestDTO request) throws SkuExistenteException {
		ObjectId[] tags = request.toTagArray();
		String id = request.getItem().getId();

		SkuDocument document = repository.findSku(id, tags, tags.length);

		if (document != null) {
			logger.info("SKU existente");
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
		if (document.getTags() != null) {
			document.setTags(new ArrayList<TagDocument>(document.getTags()));
		}
		if (document.getOrigens() != null) {
			document.setOrigens(new ArrayList<OrigemSkuDocument>(document.getOrigens()));
		}
		return invokeResponse(document);
	}

	private SkuDocument findByFiltro(SkuFiltroEnum filtro, Object value) throws SkuNotFoundException {

		Assert.notNull(filtro, SKU_FILTRO);

		SkuDocument document = null;
		if (SkuFiltroEnum.ID.equals(filtro))
			document = repository.findOne((String) value);

		if (document == null) {
			logger.info("Document nao encontrado");
			throw new SkuNotFoundException(SKU_NOTFOUND);
		}
		return document;

	}

	@Override
	public Pageable<SkuResponseDTO> all(int pageIndex, int size) throws SkuNotFoundException {

		Page<SkuDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<SkuDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new SkuNotFoundException(SKU_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		// TODO CRIAR SOLUCAO NO CONVERTER
		for (SkuDocument d : result) {
			if (d.getTags() != null) {
				d.setTags(new ArrayList<TagDocument>(d.getTags()));
			}
			if (d.getOrigens() != null) {
				d.setOrigens(new ArrayList<OrigemSkuDocument>(d.getOrigens()));
			}
		}

		Collection<SkuResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<SkuResponseDTO>(response, numberOfElements, totalPages, pageIndex);

	}

	@Override
	public Pageable<SkuResponseDTO> search(String searchTerm, int pageIndex, int size) throws SkuNotFoundException {

		// TODO Pesquisar por mais atributos!
		Page<SkuDocument> requestedPage = repository.findByDescricao(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<SkuDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new SkuNotFoundException(SKU_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<SkuResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<SkuResponseDTO>(response, sizePage, totalPages, pageIndex);

	}

	@Override
	public SkuResponseDTO update(SkuRequestDTO request) throws SkuNotFoundException {
		Assert.notNull(request, SKU_NOTNULL);
		Assert.notNull(request.getItem(), SKU_ITEM);
		Assert.notNull(request.getTags(), SKU_TAG);
		Assert.notNull(request.getDescricao(), SKU_DESCRICAO);
		Assert.notNull(request.getFrequenciaAnalise(), SKU_FREQUENCIA_ANALISE);
		Assert.notNull(request.getModelo(), SKU_MODELO);
		Assert.notNull(request.getOrigens(), SKU_ORIGEM);
		Assert.isTrue(isDefaultOrigem(request.getOrigens()), SKU_ORIGEM_DEFAULT);

		if (find(SkuFiltroEnum.ID, request.getId()) == null) {
			logger.info("SKU nao encontrado");
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

		if (find(SkuFiltroEnum.ID, id) == null) {
			logger.info("SKU nao encontrada");
			throw new SkuNotFoundException(SKU_NOTFOUND);
		}

		repository.delete(id);
	}

	@Override
	public Collection<SkuResponseDTO> all() {
		List<SkuDocument> all = repository.findAll();
		
		for(SkuDocument d : all){
			if (d.getTags() != null) {
				d.setTags(new ArrayList<TagDocument>(d.getTags()));
			}
			if (d.getOrigens() != null) {
				d.setOrigens(new ArrayList<OrigemSkuDocument>(d.getOrigens()));
			}
		}
		
		return invokeResponse(all);
	}

	@Override
	public Collection<SkuResponseDTO> chain(String idItem) throws SkuNotFoundException {
		Collection<SkuDocument> skus = repository.findSkuByItem(idItem);

		if (skus == null || skus.isEmpty()) {
			logger.info("Item da sku nao encontradad na cadeia");
			throw new SkuNotFoundException(SKU_ITEM_NOTFOUND_IN_CHAIN);
		}

		// TODO CRIAR SOLUCAO NO CONVERTER
		for (SkuDocument d : skus) {
			if (d.getTags() != null) {
				d.setTags(new ArrayList<TagDocument>(d.getTags()));
			}
			/*
			 * if (d.getOrigens() != null) { d.setOrigens(new
			 * ArrayList<OrigemSkuDocument>(d.getOrigens())); }
			 */
		}

		return ConverterHelper.convert(skus, SkuResponseDTO.class);
	}

	@Override
	public synchronized void addEstoque(String id, Integer quantidade)
			throws SkuNotFoundException, SkuNotSuchMuchQuantityException {
		SkuDocument document = repository.findOne(id);

		if (document == null)
			throw new SkuNotFoundException(SKU_NOTFOUND);

		Integer estoqueAtual = document.getEstoqueAtual();

		if (quantidade > 0) {
			document.setEstoqueAtual(Integer.sum(estoqueAtual, quantidade));
		} else {
			if (document.getEstoqueAtual() < Math.abs(quantidade))
				throw new SkuNotSuchMuchQuantityException(SKU_NOTSUCHMUCH);
			document.setEstoqueAtual(Integer.sum(estoqueAtual, quantidade));
		}
		repository.save(document);

	}

	@Override
	public void estoqueSeguranca(String id, Double es) throws SkuNotFoundException {

		SkuDocument document = repository.findOne(id);

		if (null == document)
			throw new SkuNotFoundException(SKU_NOTFOUND);

		document.setEstoqueSeguranca(Double.valueOf(Math.ceil(es)).intValue());
		repository.save(document);
	}

	@Override
	public void estoqueMaximo(String id, Double em) throws SkuNotFoundException {
		SkuDocument document = repository.findOne(id);

		if (null == document)
			throw new SkuNotFoundException(SKU_NOTFOUND);

		document.setEstoqueMaximo(Double.valueOf(Math.ceil(em)).intValue());
		repository.save(document);
	}

}
