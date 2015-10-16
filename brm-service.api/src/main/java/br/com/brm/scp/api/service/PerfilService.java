package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.service.status.PerfilFiltroEnum;

public interface PerfilService {
	
	PerfilResponseDTO create(PerfilRequestDTO request) throws PerfilExistenteException;

	void delete(String id) throws PerfilNotFoundException;

	void update(PerfilRequestDTO request) throws PerfilNotFoundException;

	PerfilResponseDTO find(PerfilFiltroEnum filtro, Object value) throws PerfilNotFoundException;
}
