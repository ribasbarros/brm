package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistentException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;

public interface CategoriaService {

	CategoriaResponseDTO create(CategoriaRequestDTO request) throws CategoriaExistentException, CategoriaNotFoundException;

	void delete(CategoriaRequestDTO request) throws CategoriaNotFoundException;

	void update(CategoriaRequestDTO request) throws CategoriaNotFoundException;
	
	CategoriaResponseDTO findByName(String nome) throws CategoriaNotFoundException;

	CategoriaResponseDTO findById(Long id) throws CategoriaNotFoundException;
	
	Collection<CategoriaResponseDTO> All() throws CategoriaNotFoundException;
}
