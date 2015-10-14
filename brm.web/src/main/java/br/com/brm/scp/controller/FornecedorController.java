package br.com.brm.scp.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.exceptions.FornecedorNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.controller.exception.FornecedorExistenteWebException;
import br.com.brm.scp.controller.exception.FornecedorNotFoundWebException;
import br.com.brm.scp.mock.api.service.status.FornecedorFiltroEnum;

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
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(FornecedorRequestDTO request) {
		try {
			service.update(request);
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{pageIndex}/{numberOfFornecedorPorPagina}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Pageable<FornecedorResponseDTO> all(@PathVariable("pageIndex") int pageIndex, @PathVariable("numberOfFornecedorPorPagina") int numberOfFornecedorPorPagina){
		Pageable<FornecedorResponseDTO> result = null;
		try {
			result = service.all(pageIndex, numberOfFornecedorPorPagina);
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException();
		}
		return result;
	}
	
	/*@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	FornecedorResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		FornecedorResponseDTO response = null;
		try {
			response = service.find(FornecedorFiltroEnum.valueOf(filtro), value);
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException();
		}
		return response;
	}*/

	
}
