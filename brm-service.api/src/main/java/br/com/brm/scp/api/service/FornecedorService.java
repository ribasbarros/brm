package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;

public interface FornecedorService {

	FornecedorResponseDTO create(FornecedorRequestDTO request) throws FornecedorExistenteException;
	
	FornecedorResponseDTO findByCnpj(String cnpj);
	
	void delete(FornecedorRequestDTO request);
	
	FornecedorResponseDTO update(FornecedorRequestDTO request);
}
