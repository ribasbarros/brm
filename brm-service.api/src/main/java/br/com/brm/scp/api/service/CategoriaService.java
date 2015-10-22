package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.status.CategoriaFiltroEnum;

public interface CategoriaService {	

	CategoriaResponseDTO create(CategoriaRequestDTO request) throws CategoriaExistenteException;

	void update(CategoriaRequestDTO request) throws CategoriaNotFoundException;	
	
	CategoriaResponseDTO find(CategoriaFiltroEnum filtro, Object value) throws CategoriaNotFoundException;

	void delete(String id) throws CategoriaNotFoundException;
	
	Collection<CategoriaResponseDTO> all() throws CategoriaNotFoundException;
	
	Pageable<CategoriaResponseDTO> all(int pageIndex, int size) throws CategoriaNotFoundException;
	
	Pageable<CategoriaResponseDTO> search(String searchTerm, int pageIndex, int size) throws CategoriaNotFoundException;
}
