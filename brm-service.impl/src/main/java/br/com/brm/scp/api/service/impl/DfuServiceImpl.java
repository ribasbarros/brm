package br.com.brm.scp.api.service.impl;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.DfuRequestDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.exceptions.DfuExistenteException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.DfuService;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.document.RelacaoSkuDocument;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;
import br.com.brm.scp.api.service.repositories.DfuRepository;
import br.com.brm.scp.api.service.status.DfuFiltroEnum;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

@Service
public class DfuServiceImpl implements DfuService {
	private static Logger logger = Logger.getLogger(DfuServiceImpl.class);
	private static final String DFU_ID = "perfil.id";
	private static final String DFU_NULL = "perfil.null";
	private static final String DFU_EXISTENTE = "perfil.existente";
	private static final String DFU_NOTFOUND = "perfil.notfound";
	private static final String DFU_FILTRO = "perfil.filtro";

	@Autowired
	DfuRepository repository;
	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public DfuResponseDTO create(DfuRequestDTO request)
			throws DfuExistenteException {
		Assert.notNull(request, DFU_NULL);
		// Ajuste feito enquanto n�o classificamos os campos para determinar
		// quando uma dfu � considerada existente
		/* Assert.isNull(request.getId(), DFU_ID); */

		try {
			hasRegister(request);
		} catch (DfuNotFoundException e) {
			logger.debug(String
					.format("Dfu nao encontrada, pronto para cadastro!"));
		}

		DfuDocument document = invokeDocument(request);
		document.setDataCriacao(new Date());
		document = repository.save(document);
		document.setUsuarioCriacao((UsuarioDocument) ConverterHelper.convert(usuarioService.getUsuarioLogado(),UsuarioDocument.class));

		DfuResponseDTO response = invokeResponse(document);

		return response;
	}

	@Override
	public void delete(String id) throws DfuNotFoundException {
		Assert.notNull(id, DFU_ID);

		if (find(DfuFiltroEnum.ID, id) == null) {
			throw new DfuNotFoundException(DFU_NOTFOUND);
		}
		repository.delete(id);
	}

	@Override
	public DfuResponseDTO update(DfuRequestDTO request) throws DfuNotFoundException {
		Assert.notNull(request, DFU_NULL);
		Assert.notNull(request.getId(), DFU_ID);

		if (find(DfuFiltroEnum.ID, request.getId()) == null) {
			throw new DfuNotFoundException(DFU_NOTFOUND);
		}

		DfuDocument document = invokeDocument(request);
		document.setDataAlteracao(new Date());
		return invokeResponse(repository.save(document));
	}

	@Override
	public DfuResponseDTO find(DfuFiltroEnum filtro, Object value)
			throws DfuNotFoundException {
		DfuDocument document = findByFiltro(filtro, value);
		if (document.getTags() != null) {
			document.setTags(new ArrayList<TagDocument>(document.getTags()));
		}
		
		return invokeResponse(document);
	}

	private DfuDocument findByFiltro(DfuFiltroEnum filtro, Object value)
			throws DfuNotFoundException {

		Assert.notNull(filtro, DFU_FILTRO);

		DfuDocument document = null;

		if (DfuFiltroEnum.ID.equals(filtro)) {

			document = repository.findById((String) value);

		}

		if (document == null)
			throw new DfuNotFoundException(DFU_NOTFOUND);

		return document;
	}

	/*
	 * Adicionar os parametros para saber se � uma DFU que j� existe no sistema
	 * n�o foi adicionado pois ainda n�o foi decidido!
	 */
	private boolean hasRegister(DfuRequestDTO request)
			throws DfuNotFoundException, DfuExistenteException {

		if (find(DfuFiltroEnum.ID, request.getId()) != null)
			throw new DfuExistenteException(DFU_EXISTENTE);

		return false;
	}

	private DfuResponseDTO invokeResponse(DfuDocument document) {
		return (DfuResponseDTO) ConverterHelper.convert(document,
				DfuResponseDTO.class);
	}

	private Collection<DfuResponseDTO> invokeResponse(
			Collection<DfuDocument> result) {
		return ConverterHelper.convert(result, DfuResponseDTO.class);
	}

	private DfuDocument invokeDocument(DfuRequestDTO request) {
		return (DfuDocument) ConverterHelper
				.convert(request, DfuDocument.class);
	}

	@Override
	public Pageable<DfuResponseDTO> all(int pageIndex, int size)
			throws DfuNotFoundException {
		Page<DfuDocument> requestedPage = repository.findAll(ServiceUtil
				.constructPageSpecification(pageIndex, size, new Sort(
						Sort.Direction.ASC, "id")));

		Collection<DfuDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new DfuNotFoundException(DFU_NOTFOUND);

		int numberOfElements = requestedPage.getNumberOfElements();
		int totalPages = requestedPage.getTotalPages();

		// TODO CRIAR SOLUCAO NO CONVERTER
		for (DfuDocument d : result) {
			if (d.getTags() != null) {
				d.setTags(new ArrayList<TagDocument>(d.getTags()));
			}
		}

		Collection<DfuResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<DfuResponseDTO>(response,
				numberOfElements, totalPages, pageIndex);
	}

	@Override
	public Pageable<DfuResponseDTO> search(String searchTerm, int pageIndex,
			int size) throws DfuNotFoundException {
		Page<DfuDocument> requestedPage = repository.findByFase(searchTerm,
				ServiceUtil.constructPageSpecification(pageIndex, size,
						new Sort(Sort.Direction.ASC, "id")));

		Collection<DfuDocument> result = requestedPage.getContent();

		if (result.isEmpty())
			throw new DfuNotFoundException(DFU_NOTFOUND);

		int sizePage = requestedPage.getSize();
		int totalPages = requestedPage.getTotalPages();

		Collection<DfuResponseDTO> response = invokeResponse(result);

		return new br.com.brm.scp.api.pages.Pageable<DfuResponseDTO>(response,
				sizePage, totalPages, pageIndex);
	}

	@Override
	public Collection<DfuResponseDTO> all() throws DfuNotFoundException {
		return invokeResponse(repository.findAll());
	}
}
