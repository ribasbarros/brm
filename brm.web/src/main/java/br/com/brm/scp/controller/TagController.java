package br.com.brm.scp.controller;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.brm.scp.api.dto.request.TagRequestDTO;
import br.com.brm.scp.api.dto.response.ReturnMessage;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.TagIsUsedException;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.api.service.status.MessageBootstrap;
import br.com.brm.scp.api.service.status.TagFiltroEnum;
import br.com.brm.scp.controller.exception.TagNotFoundWebException;
import br.com.brm.scp.fw.helper.ExceptionHelper;
import br.com.brm.scp.fw.helper.RestHelper;

@Controller
@RequestMapping("tag")
public class TagController extends RestHelper implements Serializable {
	@Autowired
	TagService service;
	private static final long serialVersionUID = -2800018523116809156L;

	private static final String TAG_CRIADO_COM_SUCESSO = "tag.savesuccess";

	private static final String TAG_ALTERADO_COM_SUCESSO = "tag.updatesuccess";


	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<ReturnMessage<TagResponseDTO>> create(@RequestBody TagRequestDTO request) {
		ReturnMessage<TagResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;

		try {
			TagResponseDTO response = service.create(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(TAG_CRIADO_COM_SUCESSO));

		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;

			restResponse.setHttpMensagem(getLabel(e.getMessage()));
			restResponse.setIco(MessageBootstrap.DANGER);
			
			restResponse.setDetalhe(ExceptionHelper.toString(e));

		}

		return new ResponseEntity<>(restResponse, status);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<ReturnMessage<TagResponseDTO>> update(@RequestBody TagRequestDTO request) {
		ReturnMessage<TagResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		try {
			TagResponseDTO response = service.update(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(TAG_ALTERADO_COM_SUCESSO));
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;

			restResponse.setHttpMensagem(getLabel(e.getMessage()));
			restResponse.setIco(MessageBootstrap.DANGER);
			
			restResponse.setDetalhe(ExceptionHelper.toString(e));
		}
		return new ResponseEntity<>(restResponse, status);
	}

	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void delete(@PathVariable("id") String id) {
		try {
			service.delete(id);
		} catch (Exception e) {
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
