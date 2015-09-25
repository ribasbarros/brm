package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.TagRequestDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.TagExistenteException;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.mock.api.service.status.TagFiltroEnum;

public interface TagService {

	//TODO Trazem todos as tags excetos os niveis ja selecionados, NAO podem repetir os Niveis
	Collection<TagResponseDTO> selecionar(TagResponseDTO tag, Collection<TagResponseDTO> selecionadas);
	
	TagResponseDTO create(TagRequestDTO request) throws TagExistenteException;

	void update(TagRequestDTO request) throws TagNotFoundException;	
	
	TagResponseDTO find(TagFiltroEnum filtro, Object value) throws TagNotFoundException;

	void delete(String id) throws TagNotFoundException;
}
