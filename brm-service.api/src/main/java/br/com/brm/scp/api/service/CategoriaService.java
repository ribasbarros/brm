package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.mock.api.service.status.CategoriaFiltroEnum;

public interface CategoriaService {	

	CategoriaResponseDTO create(CategoriaRequestDTO request) throws CategoriaExistenteException;

	void update(CategoriaRequestDTO request) throws CategoriaNotFoundException;	
	
	CategoriaResponseDTO find(CategoriaFiltroEnum filtro, Object value) throws CategoriaNotFoundException;

	void delete(String id) throws CategoriaNotFoundException;
}
