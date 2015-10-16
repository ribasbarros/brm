package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;
import br.com.brm.scp.api.service.status.GrupoFiltroEnum;

public interface GrupoService {
	GrupoResponseDTO create(GrupoRequestDTO request) throws GrupoExistenteException;

	void delete(String id) throws GrupoNotFoundException;

	void update(GrupoRequestDTO request) throws GrupoNotFoundException;
	
	GrupoResponseDTO find(GrupoFiltroEnum filtro, Object value) throws GrupoNotFoundException;

}
