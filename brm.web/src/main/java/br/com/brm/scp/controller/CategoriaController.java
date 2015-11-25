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

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.dto.response.ReturnMessage;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.CategoriaService;
import br.com.brm.scp.api.service.status.CategoriaFiltroEnum;
import br.com.brm.scp.api.service.status.MessageBootstrap;
import br.com.brm.scp.controller.exception.CategoriaNotFoundWebException;
import br.com.brm.scp.fw.helper.ExceptionHelper;
import br.com.brm.scp.fw.helper.RestHelper;

@Controller
@RequestMapping("categoria")
public class CategoriaController extends RestHelper implements Serializable {

	private static final long serialVersionUID = 1933782146057829577L;
	
	private static final String CATEGORIA_CRIADO_COM_SUCESSO = "categoria.savesuccess";
	private static final String CATEGORIA_ALTERADO_COM_SUCESSO = "categoria.updatesuccess";
	private static final String CATEGORIA_DELETADO_COM_SUCESSO = "categoria.deletesuccess";

	@Autowired
	private CategoriaService service;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<ReturnMessage<CategoriaResponseDTO>> create(@RequestBody CategoriaRequestDTO request) {
		ReturnMessage<CategoriaResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;

		try {
			CategoriaResponseDTO response = service.create(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(CATEGORIA_CRIADO_COM_SUCESSO));

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
	ResponseEntity<ReturnMessage<CategoriaResponseDTO>> update(@RequestBody CategoriaRequestDTO request) {
		ReturnMessage<CategoriaResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		try {
			CategoriaResponseDTO response = service.update(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(CATEGORIA_ALTERADO_COM_SUCESSO));
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
	ResponseEntity<ReturnMessage<CategoriaResponseDTO>> delete(@PathVariable("id") String id) {
		ReturnMessage<CategoriaResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.OK;
		try {
			service.delete(id);
			restResponse.setHttpMensagem(getLabel(CATEGORIA_DELETADO_COM_SUCESSO));
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;

			restResponse.setHttpMensagem(getLabel(e.getMessage()));
			restResponse.setIco(MessageBootstrap.DANGER);
			
			restResponse.setDetalhe(ExceptionHelper.toString(e));
		}
		
		return new ResponseEntity<>(restResponse, status);
	}
	
	

	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	CategoriaResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		CategoriaResponseDTO response = null;
		try {
			response = service.find(CategoriaFiltroEnum.valueOf(filtro), value);
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException(e.getMessage());
		}
		return response;
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
	
	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<CategoriaResponseDTO> all() {
		try {
			return service.all();
		} catch (CategoriaNotFoundException e) {
			throw new CategoriaNotFoundWebException(e.getMessage());
		}
	}
	

}
