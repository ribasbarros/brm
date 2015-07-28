package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;

public interface GrupoService {
	GrupoResponseDTO create(GrupoRequestDTO request) throws GrupoExistenteException, GrupoNotFoundException;

	void delete(GrupoRequestDTO request) throws GrupoNotFoundException;

	void update(GrupoRequestDTO request) throws GrupoNotFoundException;
	
	GrupoResponseDTO findByName(String nome) throws GrupoNotFoundException;

	GrupoResponseDTO findById(Long id) throws GrupoNotFoundException;
	
	Collection<GrupoResponseDTO> All() throws GrupoNotFoundException;
}
