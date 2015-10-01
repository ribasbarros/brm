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

import br.com.brm.scp.api.dto.request.TagRequestDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.TagExistenteException;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.controller.exception.TagExistenteWebException;
import br.com.brm.scp.controller.exception.TagNotFoundWebException;
import br.com.brm.scp.mock.api.service.status.TagFiltroEnum;

@Controller
@RequestMapping("tag")
public class TagController implements Serializable {
	@Autowired
	TagService service;
	private static final long serialVersionUID = -2800018523116809156L;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	TagResponseDTO create(TagRequestDTO request) {
		TagResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (TagExistenteException e) {
			throw new TagExistenteWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(TagRequestDTO request) {
		try {
			service.update(request);
		} catch (TagNotFoundException e) {
			throw new TagNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (TagNotFoundException e) {
			throw new TagNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	TagResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		TagResponseDTO response = null;
		try {
			response = service.find(TagFiltroEnum.valueOf(filtro), value);
		} catch (TagNotFoundException e) {
			throw new TagNotFoundWebException();
		}
		return response;
	}
	
}
