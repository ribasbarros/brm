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

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.status.SkuFiltroEnum;
import br.com.brm.scp.controller.exception.SkuExistenteWebException;
import br.com.brm.scp.controller.exception.SkuNotFoundWebException;

@Controller
@RequestMapping("sku")
public class SkuController implements Serializable {

	private static final long serialVersionUID = 9119085167667772244L;
	
	@Autowired
	private SkuService service;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	SkuResponseDTO create(@RequestBody SkuRequestDTO request) {
		SkuResponseDTO response = null;
		try {
			response = service.create(request);
		} catch (SkuExistenteException e) {
			throw new SkuExistenteWebException();
		}

		return response;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void update(@RequestBody SkuRequestDTO request) {
		try {
			service.update(request);
		} catch (SkuNotFoundException e) {
			throw new SkuNotFoundWebException(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (SkuNotFoundException e) {
			throw new SkuNotFoundWebException(e.getMessage());
		}
	}

		
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	SkuResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(SkuFiltroEnum.ID, id);
		} catch (SkuNotFoundException e) {
			throw new SkuNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Pageable<SkuResponseDTO> search(@RequestBody SearchPageableVO searchPageable) {
		Pageable<SkuResponseDTO> result = null;

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
		} catch (SkuNotFoundException e) {
			throw new SkuNotFoundWebException(e.getMessage());
		}
		return result;
	}

}
