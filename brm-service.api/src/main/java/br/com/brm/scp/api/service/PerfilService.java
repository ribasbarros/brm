package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.status.PerfilFiltroEnum;

public interface PerfilService {
	
	PerfilResponseDTO create(PerfilRequestDTO request) throws PerfilExistenteException;

	void delete(String id) throws PerfilNotFoundException;

	PerfilResponseDTO update(PerfilRequestDTO request) throws PerfilNotFoundException;

	PerfilResponseDTO find(PerfilFiltroEnum filtro, Object value) throws PerfilNotFoundException;

	Pageable<PerfilResponseDTO> search(String searchTerm, int pageIndex, int size) throws PerfilNotFoundException;

	Pageable<PerfilResponseDTO> all(int pageIndex, int size) throws PerfilNotFoundException;

	Collection<PerfilResponseDTO> all() throws PerfilNotFoundException;

}
