package br.com.brm.scp.api.service.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorNotFoundException;
import br.com.brm.scp.api.exceptions.ItemCategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.repositories.CategoriaRepository;
import br.com.brm.scp.api.service.repositories.ItemRepository;
import br.com.brm.scp.api.service.status.ItemFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

/**
 * @author Ribas
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	private static Logger logger = Logger.getLogger(ItemServiceImpl.class);

	private static final String ITEM_NOTNULL = "item.notnull";
	private static final String ITEM_CATEGORIA = "item.categoria";
	private static final String ITEM_NOME = "item.nome";
	private static final String ITEM_NOMEREDUZIDO = "item.nomereduzido";
	private static final String ITEM_STATUS = "item.status";
	private static final String ITEM_UNITIZACAO = "item.unitizacao";
	private static final String ITEM_VALOR = "item.valor";
	private static final String ITEM_EXISTENTE = "item.existente";
	private static final String ITEM_FILTRO = "item.filtronotnull";
	private static final String ITEM_NOTFOUND = "item.notfound";
	private static final String ITEM_CATEGORIA_NOTFOUND = "item.categorianotfound";
	private static final String ITEM_ID = "item.idinvalido";

	@Autowired
	private ItemRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	/* (non-Javadoc)
	 * @see br.com.brm.scp.api.service.ItemService#create(br.com.brm.scp.api.dto.request.ItemRequestDTO)
	 */
	@Override
	public ItemResponseDTO create(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException {

		Assert.notNull(request, ITEM_NOTNULL);
		Assert.notNull(request.getCategoria(), ITEM_CATEGORIA);
		Assert.notNull(request.getNome(), ITEM_NOME);
		Assert.notNull(request.getNomeReduzido(), ITEM_NOMEREDUZIDO);
		Assert.notNull(request.getStatus(), ITEM_STATUS);
		Assert.notNull(request.getUnitizacao(), ITEM_UNITIZACAO);
		Assert.notNull(request.getValorUnitario(), ITEM_VALOR);

		try {
			hasRegister(request);
		} catch (ItemNotFoundException e) {
			logger.debug(String.format("ITEM %s nao encontrado, pronto para cadastro!", request.getNome()));
		}
		
		hasCategoria(request.getCategoria().getId());
		
		ItemDocument document = (ItemDocument) ConverterHelper.convert(request, ItemDocument.class);

		Date current = new Date();
		
		document.setDataCriacao(current);
		document.setDataAlteracao(current);

		document = repository.save(document);
		
		ItemResponseDTO response = invokeResponse(document);
		
		return response;

	}

	private void hasCategoria(String idCategoria) throws ItemCategoriaNotFoundException {
		if( categoriaRepository.findOne(idCategoria) == null )
			throw new ItemCategoriaNotFoundException(ITEM_CATEGORIA_NOTFOUND);
	}

	/**
	 * @param document
	 * @return
	 */
	private ItemResponseDTO invokeResponse(ItemDocument document) {
		return (ItemResponseDTO) ConverterHelper.convert(document, ItemResponseDTO.class);
	}

	/**
	 * @param request
	 * @throws ItemExistenteException
	 * @throws FornecedorNotFoundException
	 */
	private void hasRegister(ItemRequestDTO request) throws ItemExistenteException, ItemNotFoundException {

		try {
			if (findByFiltro(ItemFiltroEnum.NOME, request.getNome()) != null)
				throw new ItemExistenteException(ITEM_EXISTENTE);

			if (findByFiltro(ItemFiltroEnum.NOME_REDUZIDO, request.getNomeReduzido()) != null)
				throw new ItemExistenteException(ITEM_EXISTENTE);

		} catch (ItemNotFoundException ex) {
			logger.debug(String.format("Item %s nao encontrado", request));
			throw new ItemNotFoundException(ITEM_NOTFOUND);
		}
	}

	/**
	 * @param filtro
	 * @param value
	 * @return
	 * @throws ItemNotFoundException
	 */
	private ItemDocument findByFiltro(ItemFiltroEnum filtro, Object value) throws ItemNotFoundException {

		Assert.notNull(filtro, ITEM_FILTRO);

		ItemDocument document = null;
		if (ItemFiltroEnum.NOME.equals(filtro)) {
			document = repository.findByNome((String) value);
		} else if (ItemFiltroEnum.NOME_REDUZIDO.equals(filtro)) {
			document = repository.findByNomeReduzido((String) value);
		} else if (ItemFiltroEnum.ID.equals(filtro)) {
			document = repository.findOne((String) value);
		}

		if (document == null)
			throw new ItemNotFoundException(ITEM_NOTFOUND);

		return document;

	}

	/* Nao se altera os nomes
	 * @see br.com.brm.scp.api.service.ItemService#update(br.com.brm.scp.api.dto.request.ItemRequestDTO)
	 */
	@Override
	public ItemResponseDTO update(ItemRequestDTO request) throws ItemNotFoundException, ItemCategoriaNotFoundException {
		
		Assert.notNull(request, ITEM_NOTNULL);
		Assert.notNull(request.getId(), ITEM_ID);
		Assert.notNull(request.getCategoria().getId(), ITEM_CATEGORIA);
		Assert.notNull(request.getNome(), ITEM_NOME);
		Assert.notNull(request.getNomeReduzido(), ITEM_NOMEREDUZIDO);
		Assert.notNull(request.getStatus(), ITEM_STATUS);
		Assert.notNull(request.getUnitizacao(), ITEM_UNITIZACAO);
		Assert.notNull(request.getValorUnitario(), ITEM_VALOR);
		
		try {
			hasRegister(request);
		} catch (ItemExistenteException e) {
			logger.debug(String.format("Item %s encontrado, pronto para ser alterado!", request.getNome()));
		}
		
		hasCategoria(request.getCategoria().getId());
		
		ItemDocument document = (ItemDocument) ConverterHelper.convert(request, ItemDocument.class);
		
		/*
		 * Quando o Request não é completo, isto é, quando a Classe de document tem mais coisa que o Request, deve-se
		 * tomar cuidado para nao salvar o objeto de destino para null, sendo que ele tem. (No caso eh a data de criacao)
		 */
		ItemDocument documentOld = findByFiltro(ItemFiltroEnum.ID, document.getId());
		
		Date current = new Date();
		document.setDataCriacao(documentOld.getDataCriacao());
		document.setDataAlteracao(current);
		
		document = repository.save(document);
		
		ItemResponseDTO response = invokeResponse(document);
		
		return response;
	}

	@Override
	public ItemResponseDTO find(ItemFiltroEnum filtro, Object value) throws ItemNotFoundException {
		ItemDocument document = findByFiltro(filtro, value);
		return invokeResponse(document);
	}

	@Override
	public void delete(String id) throws ItemNotFoundException {
		
		Assert.notNull(id, ITEM_ID);
		
		if( findByFiltro(ItemFiltroEnum.ID, id) == null ){
			throw new ItemNotFoundException(ITEM_ID);
		}
		
		repository.delete(id);
	}

	@Override
	public Pageable<ItemResponseDTO> all(int pageIndex, int size) throws ItemNotFoundException {
		Page<ItemDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<ItemDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new ItemNotFoundException(ITEM_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		Collection<ItemResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<ItemResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);
	
	}

	@Override
	public Pageable<ItemResponseDTO> search(String searchTerm, int pageIndex, int size) throws ItemNotFoundException {

		Page<ItemDocument> requestedPage = repository.findByNameOrNomeReduzidoOrDescricaoOrCategoria(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<ItemDocument> result = requestedPage.getContent();
		
		if(result.isEmpty())
			throw new ItemNotFoundException(ITEM_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<ItemResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<ItemResponseDTO>(response, sizePage, totalPages,
				pageIndex);
	
	}

	private Collection<ItemResponseDTO> invokeResponse(Collection<ItemDocument> result) {
		return ConverterHelper.convert(result, ItemResponseDTO.class);
	}

}
