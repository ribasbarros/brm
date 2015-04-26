package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

	@Override
	public UsuarioResponseDTO create(UsuarioRequestDTO request) throws UsuarioExistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioResponseDTO find(Long id) throws UsuarioNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioResponseDTO update(UsuarioRequestDTO usuarioSuccess) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearMemory() {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<UsuarioResponseDTO> all() throws UsuarioNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
