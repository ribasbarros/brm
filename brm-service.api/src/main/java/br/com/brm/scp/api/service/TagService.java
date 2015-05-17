package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.response.TagResponseDTO;

public interface TagService {

	//TODO Trazem todos as tags excetos os niveis ja selecionados, NAO podem repetir os Niveis
	Collection<TagResponseDTO> selecionar(Collection<TagResponseDTO> selecionadas);

	Collection<TagResponseDTO> all();
}
