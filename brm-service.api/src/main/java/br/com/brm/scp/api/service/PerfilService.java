package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;

public interface PerfilService {

	PerfilResponseDTO create(PerfilRequestDTO request) throws PerfilExistenteException, PerfilNotFoundException;

	void delete(PerfilRequestDTO request) throws PerfilNotFoundException;

	void update(PerfilRequestDTO request) throws PerfilNotFoundException;
	
	PerfilResponseDTO findByName(String nome) throws PerfilNotFoundException;

	PerfilResponseDTO findById(Long id) throws PerfilNotFoundException;
	
	Collection<PerfilResponseDTO> All() throws PerfilNotFoundException;
}
