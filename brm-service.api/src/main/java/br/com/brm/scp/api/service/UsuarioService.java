package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;

public interface UsuarioService {

	UsuarioResponseDTO create(UsuarioRequestDTO request) throws UsuarioExistentException;

	void update(UsuarioRequestDTO request) throws UsuarioNotFoundException;

	void delete(UsuarioRequestDTO request) throws UsuarioNotFoundException;
	
	Collection<UsuarioResponseDTO> all() throws UsuarioNotFoundException;

	UsuarioResponseDTO findById(Long id) throws UsuarioNotFoundException;

	UsuarioResponseDTO findByName(String nome) throws UsuarioNotFoundException;

}
