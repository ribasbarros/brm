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

import br.com.brm.scp.api.dto.FornecedorCentroDTO;
import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorCentroExistenteException;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.exceptions.FornecedorNotFoundException;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.api.service.document.ContatoDocument;
import br.com.brm.scp.api.service.document.FornecedorCentroDocument;
import br.com.brm.scp.api.service.document.FornecedorDocument;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.repositories.FornecedorRepository;
import br.com.brm.scp.api.service.status.FornecedorFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.validators.CNPJValidator;
import br.com.brm.scp.fw.helper.validators.NumberHelper;

/**
 * @author Ribas
 *
 */
@Service
public class FornecedorServiceImpl implements FornecedorService {

	private static Logger logger = Logger.getLogger(FornecedorServiceImpl.class);

	private static final String FORNECEDOR_FILTRO = "fornecedor.filtro";

	private static final String FORNECEDOR_RAZAOSOCIAL = "fornecedor.razaosocial";

	private static final String FORNECEDOR_NOMEFANTASIA = "fornecedor.nomefantasia";

	private static final String FORNECEDOR_DESCRICAO = "fornecedor.descricao";

	private static final String FORNECEDOR_CPNJ = "fornecedor.cpnj";

	private static final String FORNECEDOR_NOTNULL = "fornecedor.notnull";

	private static final String FORNECEDOR_NOTFOUND = "fornecedor.notfound";

	private static final String FORNECEDOR_EXISTENTE = "fornecedor.existente";

	private static final String FORNECEDOR_CNPJINVALIDO = "fornecedor.cnpjinvalido";

	private static final String FORNECEDOR_ID = "fornecedor.idinvalido";

	private static final String FORNECEDOR_CENTROCEP = "fornecedor.centro.cep";

	private static final String FORNECEDOR_CENTRONRO = "fornecedor.centro.nro";

	private static final String FORNECEDOR_CENTROEXISTENTE = "fornecedor.centroexistente";

	@Autowired
	private FornecedorRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.brm.scp.api.service.FornecedorService#create(br.com.brm.scp.api.
	 * dto.request.FornecedorRequestDTO)
	 */
	@Override
	public FornecedorResponseDTO create(FornecedorRequestDTO request) throws FornecedorExistenteException {

		Assert.notNull(request, FORNECEDOR_NOTNULL);
		Assert.notNull(request.getCnpj(), FORNECEDOR_CPNJ);
		Assert.notNull(request.getDescricao(), FORNECEDOR_DESCRICAO);
		Assert.notNull(request.getNomeFantasia(), FORNECEDOR_NOMEFANTASIA);
		Assert.notNull(request.getRazaoSocial(), FORNECEDOR_RAZAOSOCIAL);
		Assert.isTrue(NumberHelper.isNumber(request.getCnpj()), FORNECEDOR_CNPJINVALIDO);
		Assert.isTrue(CNPJValidator.isCNPJ(request.getCnpj()), FORNECEDOR_CNPJINVALIDO);

		try {
			hasRegister(request);
		} catch (FornecedorNotFoundException e) {
			logger.debug(String.format("Fornecedor %s nao encontrado, pronto para cadastro!", request.getCnpj()));
		}

		FornecedorDocument document = (FornecedorDocument) ConverterHelper.convert(request, FornecedorDocument.class);
		document.setDataCriacao(new Date());
		document = repository.save(document);
		FornecedorResponseDTO response = invokeResponse(document);

		return response;
	}

	/**
	 * @param request
	 * @throws FornecedorExistenteException
	 * @throws FornecedorNotFoundException
	 */
	private void hasRegister(FornecedorRequestDTO request)
			throws FornecedorExistenteException, FornecedorNotFoundException {
		try {
			if (findByFiltro(FornecedorFiltroEnum.CNPJ, request.getCnpj()) != null)
				throw new FornecedorExistenteException(FORNECEDOR_EXISTENTE);

			if (findByFiltro(FornecedorFiltroEnum.RAZAO_SOCIAL, request.getRazaoSocial()) != null)
				throw new FornecedorExistenteException(FORNECEDOR_EXISTENTE);

		} catch (FornecedorNotFoundException ex) {
			logger.debug(String.format("Fornecedor %s nao encontrado", request));
			throw new FornecedorNotFoundException(FORNECEDOR_NOTFOUND);
		}
	}

	/**
	 * @param filtro
	 * @param value
	 * @return
	 * @throws FornecedorNotFoundException
	 */
	private FornecedorDocument findByFiltro(FornecedorFiltroEnum filtro, Object value)
			throws FornecedorNotFoundException {

		Assert.notNull(filtro, FORNECEDOR_FILTRO);

		FornecedorDocument document = null;
		if (FornecedorFiltroEnum.RAZAO_SOCIAL.equals(filtro)) {
			document = repository.findByRazaoSocial((String) value);

		} else if (FornecedorFiltroEnum.CNPJ.equals(filtro)) {
			document = repository.findByCnpj((String) value);

		} else if (FornecedorFiltroEnum.ID.equals(filtro)) {
			document = repository.findOne((String) value);
		}

		if (document == null)
			throw new FornecedorNotFoundException(FORNECEDOR_NOTFOUND);

		return document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.brm.scp.api.service.FornecedorService#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) throws FornecedorNotFoundException {

		Assert.notNull(id, FORNECEDOR_ID);

		if (findByFiltro(FornecedorFiltroEnum.ID, id) == null) {
			throw new FornecedorNotFoundException(FORNECEDOR_ID);
		}

		repository.delete(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.brm.scp.api.service.FornecedorService#update(br.com.brm.scp.api.
	 * dto.request.FornecedorRequestDTO)
	 */
	@Override
	public FornecedorResponseDTO update(FornecedorRequestDTO request) throws FornecedorNotFoundException {

		Assert.notNull(request, FORNECEDOR_NOTNULL);
		Assert.notNull(request.getId(), FORNECEDOR_ID);
		Assert.notNull(request.getCnpj(), FORNECEDOR_CPNJ);
		Assert.notNull(request.getDescricao(), FORNECEDOR_DESCRICAO);
		Assert.notNull(request.getNomeFantasia(), FORNECEDOR_NOMEFANTASIA);
		Assert.notNull(request.getRazaoSocial(), FORNECEDOR_RAZAOSOCIAL);
		Assert.isTrue(NumberHelper.isNumber(request.getCnpj()), FORNECEDOR_CNPJINVALIDO);
		Assert.isTrue(CNPJValidator.isCNPJ(request.getCnpj()), FORNECEDOR_CNPJINVALIDO);

		try {
			hasRegister(request);
		} catch (FornecedorExistenteException e) {
			logger.debug(String.format("Fornecedor %s encontrado, pronto para ser alterado!", request.getCnpj()));
		}

		FornecedorDocument document = (FornecedorDocument) ConverterHelper.convert(request, FornecedorDocument.class);
		document.setDataAlteracao(new Date());
		
		document = repository.save(document);
		FornecedorResponseDTO response = invokeResponse(document);

		return response;

	}

	/**
	 * @param document
	 * @return
	 */
	private FornecedorResponseDTO invokeResponse(FornecedorDocument document) {
		return (FornecedorResponseDTO) ConverterHelper.convert(document, FornecedorResponseDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.brm.scp.api.service.FornecedorService#addCentro(java.lang.String,
	 * br.com.brm.scp.api.dto.FornecedorCentroDTO)
	 */
	@Override
	public void addCentro(String id, FornecedorCentroDTO request)
			throws FornecedorNotFoundException, FornecedorCentroExistenteException {

		Assert.notNull(id, FORNECEDOR_ID);
		Assert.notNull(request, FORNECEDOR_ID);
		Assert.notNull(request.getCep(), FORNECEDOR_CENTROCEP);
		Assert.notNull(request.getCentro(), FORNECEDOR_CENTRONRO);
		Assert.isTrue(NumberHelper.isNumber(request.getCnpj()), FORNECEDOR_CNPJINVALIDO);
		Assert.isTrue(CNPJValidator.isCNPJ(request.getCnpj()), FORNECEDOR_CNPJINVALIDO);

		FornecedorDocument document = findByFiltro(FornecedorFiltroEnum.ID, id);
		if (document == null)
			throw new FornecedorNotFoundException(FORNECEDOR_ID);

		if (repository.findByIdAndCentro(id, request.getCentro()) != null)
			throw new FornecedorCentroExistenteException(FORNECEDOR_CENTROEXISTENTE);

		FornecedorCentroDocument centroDocument = (FornecedorCentroDocument) ConverterHelper.convert(request,
				FornecedorCentroDocument.class);

		document.getCentros().add(centroDocument);

		document = repository.save(document);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.brm.scp.api.service.FornecedorService#find(br.com.brm.scp.mock.api
	 * .service.status.FornecedorFiltroEnum, java.lang.Object)
	 */
	@Override
	public FornecedorResponseDTO find(FornecedorFiltroEnum filtro, Object value) throws FornecedorNotFoundException {
		FornecedorDocument document = findByFiltro(filtro, value);

		document.setContatos(new ArrayList<ContatoDocument>(document.getContatos()));
		document.setCentros(new ArrayList<FornecedorCentroDocument>(document.getCentros()));

		return invokeResponse(document);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.brm.scp.api.service.FornecedorService#search(java.lang.String,
	 * int)
	 */
	@Override
	public br.com.brm.scp.api.pages.Pageable<FornecedorResponseDTO> search(String searchTerm, int pageIndex, int size)
			throws FornecedorNotFoundException {

		Page<FornecedorDocument> requestedPage = repository.findByRazaoSocialOrNomeFantasiaOrDescricaoOrCpnj(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<FornecedorDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new FornecedorNotFoundException(FORNECEDOR_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		// TODO CRIAR SOLUCAO NO CONVERTER
		for (FornecedorDocument d : result) {
			d.setContatos(new ArrayList<ContatoDocument>(d.getContatos()));
			d.setCentros(new ArrayList<FornecedorCentroDocument>(d.getCentros()));
		}

		Collection<FornecedorResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<FornecedorResponseDTO>(response, sizePage, totalPages, pageIndex);

	}

	/**
	 * @param result
	 * @return
	 */
	private Collection<FornecedorResponseDTO> invokeResponse(Collection<FornecedorDocument> result) {
		return ConverterHelper.convert(result, FornecedorResponseDTO.class);
	}

	@Override
	public br.com.brm.scp.api.pages.Pageable<FornecedorResponseDTO> all(int pageIndex, int size)
			throws FornecedorNotFoundException {

		Page<FornecedorDocument> requestedPage = repository
				.findAll(ServiceUtil.constructPageSpecification(pageIndex, size, new Sort(Sort.Direction.ASC, "id")));

		Collection<FornecedorDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new FornecedorNotFoundException(FORNECEDOR_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		// TODO CRIAR SOLUCAO NO CONVERTER
		for (FornecedorDocument d : result) {
			if(d.getContatos() != null){
				d.setContatos(new ArrayList<ContatoDocument>(d.getContatos()));		
			}
			if(d.getCentros() != null){
				d.setCentros(new ArrayList<FornecedorCentroDocument>(d.getCentros()));			
			}
		}

		Collection<FornecedorResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<FornecedorResponseDTO>(response, numberOfElements, totalPages,
				pageIndex);

	}

	@Override
	public Collection<FornecedorResponseDTO> all() throws FornecedorNotFoundException{
		// TODO Auto-generated method stub
		return invokeResponse(repository.findAll());
	}

}
