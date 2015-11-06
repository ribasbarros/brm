package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.DfuRequestDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.exceptions.DfuExistenteException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.status.DfuFiltroEnum;


public interface DfuService {
	DfuResponseDTO create(DfuRequestDTO request) throws DfuExistenteException;

	void delete(String id) throws DfuNotFoundException;

	void update(DfuRequestDTO request) throws DfuNotFoundException;

	DfuResponseDTO find(DfuFiltroEnum filtro, Object value) throws DfuNotFoundException;
	
	Pageable<DfuResponseDTO> search(String searchTerm, int pageIndex, int size) throws DfuNotFoundException;

	Pageable<DfuResponseDTO> all(int pageIndex, int size) throws DfuNotFoundException;

	Collection<DfuResponseDTO> all() throws DfuNotFoundException;
}
