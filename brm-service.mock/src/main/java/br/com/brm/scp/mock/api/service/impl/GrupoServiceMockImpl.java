package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilRepetidoException;
import br.com.brm.scp.api.service.GrupoService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.GrupoDocument;

public class GrupoServiceMockImpl implements GrupoService {

	private Logger logger = Logger.getLogger(GrupoServiceMockImpl.class);

	private MockData dbMock;

	public GrupoServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	@Override
	public GrupoResponseDTO create(GrupoRequestDTO request)
			throws GrupoExistenteException, GrupoNotFoundException, PerfilRepetidoException {
		prepareSave(request);
		hasGrupo(request);
		return insert(request);
	}

	private GrupoResponseDTO insert(GrupoRequestDTO request) {
		request.setId(new Long("1"));
		GrupoDocument document = (GrupoDocument) ConverterHelper.convert(request, GrupoDocument.class);
		dbMock.getGrupoCollection().put(document.getId(), document);
		return (GrupoResponseDTO) ConverterHelper.convert(document, GrupoResponseDTO.class);
	}

	private void hasGrupo(GrupoRequestDTO request) throws GrupoExistenteException {
		try{
			if (findByName(request.getNome()) != null) {
				logger.debug(String.format("Grupo já cadastrado na base de id=%s", request.getId()));
				throw new GrupoExistenteException();
			}
		}catch(GrupoNotFoundException exception){
		
		}
	}

	private void prepareSave(GrupoRequestDTO request) throws PerfilRepetidoException {
		Assert.notNull(request.getNome(), "grupo.nomenaopreenchido");
		Assert.notEmpty(request.getPerfis(), "grupo.perfilnaopreenchido");
		
		HashSet<PerfilResponseDTO> hashSet = new HashSet<PerfilResponseDTO>(request.getPerfis());
		if(hashSet.size() != request.getPerfis().size()){
			throw new PerfilRepetidoException();
		}	
	}

	@Override
	public void delete(GrupoRequestDTO request) throws GrupoNotFoundException {
		findByName(request.getNome());
		GrupoDocument document = (GrupoDocument) ConverterHelper.convert(request, GrupoDocument.class);
		document.setDataExcluido(new Date());
		dbMock.getGrupoCollection().put(document.getId(), document);
		
	}

	@Override
	public void update(GrupoRequestDTO request) throws GrupoNotFoundException, PerfilRepetidoException {
		findById(request.getId());
		prepareSave(request);
		GrupoDocument document = (GrupoDocument) ConverterHelper.convert(request, GrupoDocument.class);
		dbMock.getGrupoCollection().put(document.getId(), document);
	}

	@Override
	public GrupoResponseDTO findByName(String nome)
			throws GrupoNotFoundException {
		for (GrupoDocument document : dbMock.getGrupoCollection().values()) {
			if (document.getNome().equals(nome)) {
				return (GrupoResponseDTO) ConverterHelper.convert(document,GrupoResponseDTO.class);
			}
		}
		logger.info("Grupo não encontrado");
		throw new GrupoNotFoundException();
	}

	@Override
	public GrupoResponseDTO findById(Long id) throws GrupoNotFoundException {
		for (GrupoDocument document : dbMock.getGrupoCollection().values()) {
			if (document.getId().equals(id)) {
				return (GrupoResponseDTO) ConverterHelper.convert(document,GrupoResponseDTO.class);
			}
		}
		logger.info("Grupo não encontrado");
		throw new GrupoNotFoundException();
	}

	@Override
	public Collection<GrupoResponseDTO> All() throws GrupoNotFoundException {
		Collection<GrupoDocument> grupos = dbMock.getGrupoCollection().values();
		if(grupos.isEmpty()){
			throw new GrupoNotFoundException("nenhum.grupo.encontrado");			
		}		
		return ConverterHelper.convert(grupos, GrupoResponseDTO.class);
	}
}
