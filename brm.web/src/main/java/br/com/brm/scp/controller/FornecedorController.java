package br.com.brm.scp.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.controller.exception.FornecedorExistenteWebException;

@Controller
@RequestMapping("fornecedor")
public class FornecedorController implements Serializable {

	private static final long serialVersionUID = 900434713647217465L;
	
	@Autowired
	private FornecedorService service;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	FornecedorResponseDTO create(FornecedorRequestDTO request) {
		FornecedorResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (FornecedorExistenteException e) {
			throw new FornecedorExistenteWebException();
		}

		return response;
	}
	
	/*
	void delete(String id) throws FornecedorNotFoundException;
	
	FornecedorResponseDTO update(FornecedorRequestDTO request) throws FornecedorNotFoundException;

	FornecedorResponseDTO find(FornecedorFiltroEnum filtro, Object value) throws FornecedorNotFoundException;

	void addCentro(String id, FornecedorCentroDTO request) throws FornecedorNotFoundException, FornecedorCentroExistenteException;
	 */
	
}
