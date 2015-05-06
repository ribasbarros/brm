package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.response.TagResponseDTO;

public interface TagService {

	//TODO Trazem todas as tags
	Collection<TagResponseDTO> find();

	//TODO Trazem todos as tags excetos os niveis ja selecionados, NAO podem repetir os Niveis
	Collection<TagResponseDTO> find(Object[] array);

}
