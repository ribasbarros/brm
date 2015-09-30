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

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;
import br.com.brm.scp.api.service.GrupoService;
import br.com.brm.scp.controller.exception.GrupoExistenteWebException;
import br.com.brm.scp.controller.exception.GrupoNotFoundWebException;
import br.com.brm.scp.mock.api.service.status.GrupoFiltroEnum;

@Controller
@RequestMapping("grupo")
public class GrupoController implements Serializable {
	@Autowired
	GrupoService service;
		
	private static final long serialVersionUID = 6720018258769444944L;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	GrupoResponseDTO create(GrupoRequestDTO request) {
		GrupoResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (GrupoExistenteException e) {
			throw new GrupoExistenteWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(GrupoRequestDTO request) {
		try {
			service.update(request);
		} catch (GrupoNotFoundException e) {
			throw new GrupoNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (GrupoNotFoundException e) {
			throw new GrupoNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	GrupoResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		GrupoResponseDTO response = null;
		try {
			response = service.find(GrupoFiltroEnum.valueOf(filtro), value);
		} catch (GrupoNotFoundException e) {
			throw new GrupoNotFoundWebException();
		}
		return response;
	}
	
}
