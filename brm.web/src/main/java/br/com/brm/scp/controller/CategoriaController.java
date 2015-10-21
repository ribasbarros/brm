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

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
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
	CategoriaResponseDTO create(@RequestBody CategoriaRequestDTO request) {
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
	void update(@RequestBody CategoriaRequestDTO request) {
		try {
			service.update(request);
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	CategoriaResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(CategoriaFiltroEnum.ID, id);
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Pageable<CategoriaResponseDTO> search(@RequestBody SearchPageableVO searchPageable) {
		Pageable<CategoriaResponseDTO> result = null;

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
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException(e.getMessage());
		}
		return result;
	}

}
