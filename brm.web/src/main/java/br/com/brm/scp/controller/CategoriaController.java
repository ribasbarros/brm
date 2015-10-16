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

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.service.CategoriaService;
import br.com.brm.scp.api.service.status.CategoriaFiltroEnum;
import br.com.brm.scp.controller.exception.CategoriaExistenteWebException;
import br.com.brm.scp.controller.exception.CategoriaNotFoundWebException;

@Controller
@RequestMapping("categoria")
public class CategoriaController implements Serializable {

	private static final long serialVersionUID = 1933782146057829577L;

	@Autowired
	private CategoriaService service;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	CategoriaResponseDTO create(CategoriaRequestDTO request) {
		CategoriaResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (CategoriaExistenteException e) {
			throw new CategoriaExistenteWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(CategoriaRequestDTO request) {
		try {
			service.update(request);
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	CategoriaResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		CategoriaResponseDTO response = null;
		try {
			response = service.find(CategoriaFiltroEnum.valueOf(filtro), value);
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException();
		}
		return response;
	}

}
