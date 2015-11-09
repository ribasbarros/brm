package br.com.brm.scp.controller;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.brm.scp.api.dto.request.DfuRequestDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.exceptions.DfuExistenteException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.DfuService;
import br.com.brm.scp.api.service.status.DfuFiltroEnum;
import br.com.brm.scp.controller.exception.DfuExistenteWebException;
import br.com.brm.scp.controller.exception.DfuNotFoundWebException;

@Controller
@RequestMapping("dfu")
public class DfuController implements Serializable {

	private static final long serialVersionUID = 1933782146057829577L;

	@Autowired
	private DfuService service;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	DfuResponseDTO create(@RequestBody DfuRequestDTO request) {
		DfuResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (DfuExistenteException e) {
			throw new DfuExistenteWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(@RequestBody DfuRequestDTO request) {
		try {
			service.update(request);
		} catch (DfuNotFoundException e) {
			throw new DfuNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (DfuNotFoundException e) {
			throw new DfuNotFoundWebException(e.getMessage());
		}
	}
	
	

	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	DfuResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		DfuResponseDTO response = null;
		try {
			response = service.find(DfuFiltroEnum.valueOf(filtro), value);
		} catch (DfuNotFoundException e) {
			throw new DfuNotFoundWebException(e.getMessage());
		}
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	DfuResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(DfuFiltroEnum.ID, id);
		} catch (DfuNotFoundException e) {
			throw new DfuNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Pageable<DfuResponseDTO> search(@RequestBody SearchPageableVO searchPageable) {
		Pageable<DfuResponseDTO> result = null;

		if (searchPageable.getPageIndex() < 0) {
			searchPageable.setPageIndex(0);
		}

		try {
			if ("".equals(searchPageable.getSearchTerm()) || null == searchPageable.getSearchTerm()) {
				result = service.all(searchPageable.getPageIndex(), searchPageable.getSize());
			} else {

				searchPageable.setSearchTerm(searchPageable.getSearchTerm().replaceAll("[();$]", "\\\\$0"));

				result = service.search(searchPageable.getSearchTerm(), searchPageable.getPageIndex(),
						searchPageable.getSize());
			}
		} catch (DfuNotFoundException e) {
			throw new DfuNotFoundWebException(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<DfuResponseDTO> all() {
		try {
			return service.all();
		} catch (DfuNotFoundException e) {
			throw new DfuNotFoundWebException(e.getMessage());
		}
	}
	
}
