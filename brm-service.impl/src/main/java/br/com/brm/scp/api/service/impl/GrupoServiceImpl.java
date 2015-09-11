package br.com.brm.scp.api.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.neo4j.cypher.internal.compiler.v2_1.perty.docbuilders.docStructureDocBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilRepetidoException;
import br.com.brm.scp.api.service.GrupoService;
import br.com.brm.scp.api.service.document.GrupoDocument;
import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.repositories.GrupoRepository;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.status.GrupoFiltroEnum;

@Service
public class GrupoServiceImpl implements GrupoService {
	@Autowired
	GrupoRepository repository;

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
					.format("Perfil nao encontrado, pronto para cadastro!"));
		}
				
		GrupoDocument document = invokeDocument(request);
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
	public void update(GrupoRequestDTO request) throws GrupoNotFoundException {
		Assert.notNull(request, GRUPO_NULL);
		Assert.notNull(request.getId(), GRUPO_ID);
		Assert.notNull(request.getNome(), GRUPO_NOME);

		if (find(GrupoFiltroEnum.ID, request.getId()) == null) {
			throw new GrupoNotFoundException(GRUPO_NOTFOUND);
		}

		GrupoDocument document = invokeDocument(request);

		repository.save(document);
	}

	@Override
	public GrupoResponseDTO find(GrupoFiltroEnum filtro, Object value)
			throws GrupoNotFoundException {
		GrupoDocument document = findByFiltro(filtro, value);
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
}
