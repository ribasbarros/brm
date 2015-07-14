package br.com.brm.scp.api.service.impl;

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.service.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService{

	@Override
	public CategoriaResponseDTO create(CategoriaRequestDTO request)
			throws CategoriaExistenteException, CategoriaNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(CategoriaRequestDTO request)
			throws CategoriaNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CategoriaRequestDTO request)
			throws CategoriaNotFoundException {
		// TODO Auto-generated method stub
		
	}

}
