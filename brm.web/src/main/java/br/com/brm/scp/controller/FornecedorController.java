package br.com.brm.scp.controller;

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

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.dto.response.ReturnMessage;
import br.com.brm.scp.api.exceptions.FornecedorNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.pages.SearchPageableVO;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.api.service.status.FornecedorFiltroEnum;
import br.com.brm.scp.api.service.status.MessageBootstrap;
import br.com.brm.scp.controller.exception.FornecedorNotFoundWebException;
import br.com.brm.scp.fw.helper.ExceptionHelper;
import br.com.brm.scp.fw.helper.RestHelper;

@Controller
@RequestMapping("fornecedor")
public class FornecedorController extends RestHelper {

	private static final long serialVersionUID = 314431848882961019L;

	private static final String FORNECEDOR_CRIADO_COM_SUCESSO = "fornecedor.savesuccess";

	private static final String FORNECEDOR_ALTERADO_COM_SUCESSO = "fornecedor.updatesuccess";

	private static final String FORNECEDOR_DELETADO_COM_SUCESSO = "fornecedor.deletesucess";

	@Autowired
	private FornecedorService service;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<ReturnMessage<FornecedorResponseDTO>> create(@RequestBody FornecedorRequestDTO request) {
		ReturnMessage<FornecedorResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;

		try {
			FornecedorResponseDTO response = service.create(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(FORNECEDOR_CRIADO_COM_SUCESSO));

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
	ResponseEntity<ReturnMessage<FornecedorResponseDTO>> update(@RequestBody FornecedorRequestDTO request) {
		ReturnMessage<FornecedorResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		try {
			FornecedorResponseDTO response = service.update(request);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(FORNECEDOR_ALTERADO_COM_SUCESSO));
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
	ResponseEntity<ReturnMessage<FornecedorResponseDTO>> delete(@PathVariable("id") String id) {
		ReturnMessage<FornecedorResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.OK;
		try {
			service.delete(id);
			restResponse.setHttpMensagem(getLabel(FORNECEDOR_DELETADO_COM_SUCESSO));
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;

			restResponse.setHttpMensagem(getLabel(e.getMessage()));
			restResponse.setIco(MessageBootstrap.DANGER);
			
			restResponse.setDetalhe(ExceptionHelper.toString(e));
		}
		
		return new ResponseEntity<>(restResponse, status);
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	FornecedorResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(FornecedorFiltroEnum.ID, id);
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "{pageIndex}/{size}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Pageable<FornecedorResponseDTO> all(@PathVariable("pageIndex") int pageIndex, @PathVariable("size") int size) {
		Pageable<FornecedorResponseDTO> result = null;
		try {
			result = service.all(pageIndex, size);
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	Pageable<FornecedorResponseDTO> search(@RequestBody SearchPageableVO searchPageable) {
		Pageable<FornecedorResponseDTO> result = null;

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
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<FornecedorResponseDTO> all() {
		try {
			return service.all();
		} catch (FornecedorNotFoundException e) {
			throw new FornecedorNotFoundWebException(e.getMessage());
		}
	}

}
