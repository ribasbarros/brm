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

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemCategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.controller.exception.ItemCategoriaNotFoundWebException;
import br.com.brm.scp.controller.exception.ItemExistenteWebException;
import br.com.brm.scp.controller.exception.ItemNotFoundWebException;
import br.com.brm.scp.mock.api.service.status.ItemFiltroEnum;

@Controller
@RequestMapping("item")
public class ItemController implements Serializable {
	@Autowired
	ItemService service;
	
	private static final long serialVersionUID = -9194937403856686721L;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	ItemResponseDTO create(ItemRequestDTO request) {
		ItemResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (ItemExistenteException e) {
			throw new ItemExistenteWebException();
		} catch (ItemCategoriaNotFoundException e) {
			throw new ItemCategoriaNotFoundWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(ItemRequestDTO request) {
		try {
			service.update(request);
		} catch (ItemCategoriaNotFoundException e) {
			throw new ItemCategoriaNotFoundWebException();
		} catch (ItemNotFoundException e) {
			throw new ItemNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (ItemNotFoundException e) {
			throw new ItemNotFoundWebException();
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	ItemResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		ItemResponseDTO response = null;
		try {
			response = service.find(ItemFiltroEnum.valueOf(filtro), value);
		} catch (ItemNotFoundException e) {
			throw new ItemNotFoundWebException();
		}
		return response;
	}
}
