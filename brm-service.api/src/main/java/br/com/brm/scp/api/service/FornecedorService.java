package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.FornecedorCentroDTO;
import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorCentroExistenteException;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.exceptions.FornecedorNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.status.FornecedorFiltroEnum;

public interface FornecedorService {

	FornecedorResponseDTO create(FornecedorRequestDTO request) throws FornecedorExistenteException;
	
	void delete(String id) throws FornecedorNotFoundException;
	
	FornecedorResponseDTO update(FornecedorRequestDTO request) throws FornecedorNotFoundException;

	FornecedorResponseDTO find(FornecedorFiltroEnum filtro, Object value) throws FornecedorNotFoundException;

	void addCentro(String id, FornecedorCentroDTO request) throws FornecedorNotFoundException, FornecedorCentroExistenteException;

	Pageable<FornecedorResponseDTO> search(String searchTerm, int pageIndex, int size) throws FornecedorNotFoundException;

	Pageable<FornecedorResponseDTO> all(int pageIndex, int size) throws FornecedorNotFoundException;

	Collection<FornecedorResponseDTO> all();
}
