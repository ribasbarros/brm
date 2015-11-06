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

import br.com.brm.scp.api.dto.request.TagRequestDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.api.exceptions.TagExistenteException;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.api.service.status.TagFiltroEnum;
import br.com.brm.scp.api.service.status.TagFiltroEnum;
import br.com.brm.scp.controller.exception.TagNotFoundWebException;
import br.com.brm.scp.controller.exception.TagExistenteWebException;
import br.com.brm.scp.controller.exception.TagNotFoundWebException;

@Controller
@RequestMapping("tag")
public class TagController implements Serializable {
	@Autowired
	TagService service;
	private static final long serialVersionUID = -2800018523116809156L;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	TagResponseDTO create(@RequestBody TagRequestDTO request) {
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
	void update(@RequestBody TagRequestDTO request) {
		try {
			service.update(request);
		} catch (TagNotFoundException e) {
			throw new TagNotFoundWebException(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (TagNotFoundException e) {
			throw new TagNotFoundWebException(e.getMessage());
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
			throw new TagNotFoundWebException(e.getMessage());
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	TagResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(TagFiltroEnum.ID, id);
		} catch (TagNotFoundException e) {
			throw new TagNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Pageable<TagResponseDTO> search(@RequestBody SearchPageableVO searchPageable) {
		Pageable<TagResponseDTO> result = null;

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
		} catch (TagNotFoundException e) {
			throw new TagNotFoundWebException(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<TagResponseDTO> all() {
		try {
			return service.all();
		} catch (TagNotFoundWebException e) {
			throw new TagNotFoundWebException(e.getMessage());
		}
	}
	
}
