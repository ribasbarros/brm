package br.com.brm.scp.controller;

import java.io.Serializable;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.ReturnMessage;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.status.MessageBootstrap;
import br.com.brm.scp.api.service.status.UsuarioFiltroEnum;
import br.com.brm.scp.controller.exception.UsuarioNotFoundWebException;
import br.com.brm.scp.fw.helper.ExceptionHelper;
import br.com.brm.scp.fw.helper.RestHelper;

@Controller
@RequestMapping("usuario")
public class UserController extends RestHelper implements Serializable {
	@Autowired
	UsuarioService service;
	private static final long serialVersionUID = -1334399509544544618L;
	private static final String USUARIO_CRIADO_COM_SUCESSO = "usuario.savesuccess";

	private static final String USUARIO_ALTERADO_COM_SUCESSO = "usuario.updatesuccess";


	private static final String FORMAT_DATE = "dd/MM/yyyy";

	@RequestMapping(value = "user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Principal userLogado(Principal user) {
		return user;
	}

	@RequestMapping(value = "date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String date() {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.format(new Date());
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<ReturnMessage<UsuarioResponseDTO>> create(@RequestBody UsuarioRequestDTO request) {
		ReturnMessage<UsuarioResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;

		try {
			UsuarioResponseDTO response = service.create(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(USUARIO_CRIADO_COM_SUCESSO));

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
	ResponseEntity<ReturnMessage<UsuarioResponseDTO>> update(@RequestBody UsuarioRequestDTO request) {
		ReturnMessage<UsuarioResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		try {
			UsuarioResponseDTO response = service.update(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(USUARIO_ALTERADO_COM_SUCESSO));
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
		} catch (UsuarioNotFoundException e) {
			throw new UsuarioNotFoundWebException(e.getMessage());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value= "{filtro}/{value}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	UsuarioResponseDTO find(@PathVariable("filtro") String filtro, @PathVariable("value") String value) {
		UsuarioResponseDTO response = null;
		try {
			response = service.find(UsuarioFiltroEnum.valueOf(filtro), value);
		} catch (UsuarioNotFoundException e) {
			throw new UsuarioNotFoundWebException(e.getMessage());
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	UsuarioResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(UsuarioFiltroEnum.ID, id);
		} catch (UsuarioNotFoundException e) {
			throw new UsuarioNotFoundWebException(e.getMessage());
		}
	}


	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Pageable<UsuarioResponseDTO> search(@RequestBody SearchPageableVO searchPageable) {
		Pageable<UsuarioResponseDTO> result = null;

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
		} catch (UsuarioNotFoundException e) {
			throw new UsuarioNotFoundWebException(e.getMessage());
		}
		return result;
	}
}
