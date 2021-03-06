package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.status.UsuarioFiltroEnum;

public interface UsuarioService {

	UsuarioResponseDTO create(UsuarioRequestDTO request) throws UsuarioExistentException;

	UsuarioResponseDTO update(UsuarioRequestDTO request) throws UsuarioNotFoundException;

	void delete(String id) throws UsuarioNotFoundException;
	
	UsuarioResponseDTO find(UsuarioFiltroEnum filtro, Object value) throws UsuarioNotFoundException;
	
	Pageable<UsuarioResponseDTO> search(String searchTerm, int pageIndex, int size) throws UsuarioNotFoundException;
	
	Pageable<UsuarioResponseDTO> all(int pageIndex, int size) throws UsuarioNotFoundException;
	
	UsuarioResponseDTO getUsuarioLogado();
	
}
