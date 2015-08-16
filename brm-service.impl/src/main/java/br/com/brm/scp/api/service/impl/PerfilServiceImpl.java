package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.service.PerfilService;

public class PerfilServiceImpl implements PerfilService {

	@Override
	public PerfilResponseDTO create(PerfilRequestDTO request) throws PerfilExistenteException, PerfilNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(PerfilRequestDTO request) throws PerfilNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PerfilRequestDTO request) throws PerfilNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PerfilResponseDTO findByName(String nome) throws PerfilNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PerfilResponseDTO findById(Long id) throws PerfilNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PerfilResponseDTO> All() throws PerfilNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
