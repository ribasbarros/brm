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

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.service.PerfilService;
import br.com.brm.scp.controller.exception.PerfilExistenteWebException;
import br.com.brm.scp.controller.exception.PerfilNotFoundWebException;
import br.com.brm.scp.mock.api.service.status.PerfilFiltroEnum;

@Controller
@RequestMapping("perfil")
public class PerfilController implements Serializable {
	@Autowired
	PerfilService service;
	
	private static final long serialVersionUID = -9005186692408166650L;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	PerfilResponseDTO create(PerfilRequestDTO request) {
		PerfilResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (PerfilExistenteException e) {
			throw new PerfilExistenteWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(PerfilRequestDTO request) {
		try {
			service.update(request);
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	PerfilResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		PerfilResponseDTO response = null;
		try {
			response = service.find(PerfilFiltroEnum.valueOf(filtro), value);
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException();
		}
		return response;
	}
	
}
