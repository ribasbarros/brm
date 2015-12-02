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

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.dto.response.ReturnMessage;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.PerfilService;
import br.com.brm.scp.api.service.status.MessageBootstrap;
import br.com.brm.scp.api.service.status.PerfilFiltroEnum;
import br.com.brm.scp.controller.exception.PerfilNotFoundWebException;
import br.com.brm.scp.fw.helper.ExceptionHelper;
import br.com.brm.scp.fw.helper.RestHelper;

@Controller
@RequestMapping("perfil")
public class PerfilController extends RestHelper implements Serializable {
	@Autowired
	PerfilService service;
	
	private static final long serialVersionUID = -9005186692408166650L;

	private static final String PERFIL_CRIADO_COM_SUCESSO = "perfil.savesuccess";

	private static final String PERFIL_ALTERADO_COM_SUCESSO = "perfil.updatesuccess";

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<ReturnMessage<PerfilResponseDTO>> create(@RequestBody PerfilRequestDTO request) {
		ReturnMessage<PerfilResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;

		try {
			PerfilResponseDTO response = service.create(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(PERFIL_CRIADO_COM_SUCESSO));

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
	ResponseEntity<ReturnMessage<PerfilResponseDTO>> update(@RequestBody PerfilRequestDTO request) {
		ReturnMessage<PerfilResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		try {
			PerfilResponseDTO response = service.update(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(PERFIL_ALTERADO_COM_SUCESSO));
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
	
	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<PerfilResponseDTO> all() {
		try {
			return service.all();
		} catch (PerfilNotFoundException e) {
			throw new PerfilNotFoundWebException(e.getMessage());
		}
	}
}
