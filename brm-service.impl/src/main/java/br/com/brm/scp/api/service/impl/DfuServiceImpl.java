package br.com.brm.scp.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.DfuRequestDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.exceptions.DfuExistenteException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.service.DfuService;
import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.repositories.DfuRepository;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.status.DfuFiltroEnum;

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

	@Override
	public DfuResponseDTO create(DfuRequestDTO request)
			throws DfuExistenteException, DfuNotFoundException {
		Assert.notNull(request, DFU_NULL);
		//Ajuste feito enquanto não classificamos os campos para determinar quando uma dfu é considerada existente
		/*Assert.isNull(request.getId(), DFU_ID);*/

		try {
			hasRegister(request);
		} catch (DfuNotFoundException e) {
			logger.debug(String
					.format("Dfu nao encontrada, pronto para cadastro!"));
		}

		DfuDocument document = invokeDocument(request);

		document = repository.save(document);

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
	public void update(DfuRequestDTO request) throws DfuNotFoundException {
		Assert.notNull(request, DFU_NULL);
		Assert.notNull(request.getId(), DFU_ID);

		if (find(DfuFiltroEnum.ID, request.getId()) == null) {
			throw new DfuNotFoundException(DFU_NOTFOUND);
		}

		DfuDocument document = invokeDocument(request);

		repository.save(document);
	}

	@Override
	public DfuResponseDTO find(DfuFiltroEnum filtro, Object value)
			throws DfuNotFoundException {
		DfuDocument document = findByFiltro(filtro, value);
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
	 * Adicionar os parametros para saber se é uma DFU que já existe no sistema
	 * não foi adicionado pois ainda não foi decidido!
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

	private DfuDocument invokeDocument(DfuRequestDTO request) {
		return (DfuDocument) ConverterHelper
				.convert(request, DfuDocument.class);
	}
}
