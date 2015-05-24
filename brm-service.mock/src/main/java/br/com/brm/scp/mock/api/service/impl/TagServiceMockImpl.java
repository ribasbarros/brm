package br.com.brm.scp.mock.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.TagDocument;

public class TagServiceMockImpl implements TagService {

	private Logger logger = Logger.getLogger(TagServiceMockImpl.class);
	
	private MockData dbMock;

	public TagServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	//TODO tag = A selecionada   selecionadas = selecionadas na tela (Nao pode haver niveis repetidos)
	@Override
	public Collection<TagResponseDTO> selecionar(TagResponseDTO tag, Collection<TagResponseDTO> selecionadas) {
		
		logger.info(String.format("Selecionando tags, tags selecionadas %s", selecionadas));
		
		if(selecionadas == null || selecionadas.isEmpty()){
			return all();
		}
		
		Collection<Integer> selecionarUniqueNiveis = new ArrayList<Integer>();
		for(TagResponseDTO response : selecionadas){
			Integer nivel = response.getNivel();
			if(!selecionarUniqueNiveis.contains(nivel)){
				selecionarUniqueNiveis.add(nivel);
			}
		}
		
		logger.info(String.format("Niveis selecionados na(s) tag(s) %s (Nao podem haver repeticoes)", selecionarUniqueNiveis));
		
		return ConverterHelper.convert(selectNotEqualsNivel(selecionarUniqueNiveis), TagResponseDTO.class); 
	}

	private Collection<TagDocument> selectNotEqualsNivel(Collection<Integer> selecionarUniqueNiveis) {
		Collection<TagDocument> result = new ArrayList<TagDocument>();
		Collection<TagDocument> all = findAll();
		for(TagDocument document : all){
			Integer nivel = document.getNivel();
			if(!selecionarUniqueNiveis.contains(nivel)){
				result.add(document);
			}
		}
		
		logger.info(String.format("Tags selecionadas %s (Nao podem haver repeticoes, Nivel 1, Nivel 2....)", selecionarUniqueNiveis));
		
		return result;
	}

	@Override
	public Collection<TagResponseDTO> all() {
		return ConverterHelper.convert(findAll(), TagResponseDTO.class);
	}

	private Collection<TagDocument> findAll() {
		return dbMock.getTagCollection().values();
	}
}
