package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;

public interface UsuarioService {

	UsuarioResponseDTO create(UsuarioRequestDTO request) throws UsuarioExistentException;

	UsuarioResponseDTO find(Long id) throws UsuarioNotFoundException;

	UsuarioResponseDTO update(UsuarioRequestDTO usuarioSuccess);

}
