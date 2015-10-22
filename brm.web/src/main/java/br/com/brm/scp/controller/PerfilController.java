package br.com.brm.scp.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.PerfilService;
import br.com.brm.scp.api.service.status.PerfilFiltroEnum;
import br.com.brm.scp.api.service.status.PerfilFiltroEnum;
import br.com.brm.scp.controller.exception.PerfilNotFoundWebException;
import br.com.brm.scp.controller.exception.PerfilNotFoundWebException;
import br.com.brm.scp.controller.exception.PerfilExistenteWebException;
import br.com.brm.scp.controller.exception.PerfilNotFoundWebException;

@Controller
@RequestMapping("perfil")
public class PerfilController implements Serializable {
	@Autowired
	PerfilService service;
	
	private static final long serialVersionUID = -9005186692408166650L;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	PerfilResponseDTO create(@RequestBody PerfilRequestDTO request) {
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
	void update(@RequestBody PerfilRequestDTO request) {
		try {
			service.update(request);
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException(e.getMessage());
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
			throw new PerfilNotFoundWebException(e.getMessage());
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Pageable<PerfilResponseDTO> search(@RequestBody SearchPageableVO searchPageable) {
		Pageable<PerfilResponseDTO> result = null;
		try {
			if ("".equals(searchPageable.getSearchTerm()) || null == searchPageable.getSearchTerm()) {
				result = service.all(searchPageable.getPageIndex(), searchPageable.getSize());
			} else {
				
				searchPageable.setSearchTerm(searchPageable.getSearchTerm().replaceAll("[();$]", "\\\\$0"));

				result = service.search(searchPageable.getSearchTerm(), searchPageable.getPageIndex(),
						searchPageable.getSize());
			}
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	PerfilResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(PerfilFiltroEnum.ID, id);
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException(e.getMessage());
		}
	}
}
