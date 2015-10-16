package br.com.brm.scp.controller;

import java.io.Serializable;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.status.UsuarioFiltroEnum;
import br.com.brm.scp.controller.exception.UsuarioExistenteWebException;
import br.com.brm.scp.controller.exception.UsuarioNotFoundWebException;

@Controller
@RequestMapping("application")
public class UserController implements Serializable {
	@Autowired
	UsuarioService service;
	private static final long serialVersionUID = -1334399509544544618L;
	
	private static final String FORMAT_DATE = "dd/MM/yyyy";

	@RequestMapping(value = "user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Principal userLogado(Principal user) {
		return user;
	}

	@RequestMapping(value = "date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String date() {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.format(new Date());
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	UsuarioResponseDTO create(UsuarioRequestDTO request) {
		UsuarioResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (UsuarioExistentException e) {
			throw new UsuarioExistenteWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(UsuarioRequestDTO request) {
		try {
			service.update(request);
		} catch (UsuarioNotFoundException e) {
			throw new UsuarioNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (UsuarioNotFoundException e) {
			throw new UsuarioNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	UsuarioResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		UsuarioResponseDTO response = null;
		try {
			response = service.find(UsuarioFiltroEnum.valueOf(filtro), value);
		} catch (UsuarioNotFoundException e) {
			throw new UsuarioNotFoundWebException();
		}
		return response;
	}

}
