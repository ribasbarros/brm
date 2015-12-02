package br.com.brm.scp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
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

import br.com.brm.scp.api.dto.request.PedidoRequestDTO;
import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.dto.response.ReturnMessage;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.status.MessageBootstrap;
import br.com.brm.scp.api.vo.PedidoVO;
import br.com.brm.scp.controller.exception.PedidoNotFoundWebException;
import br.com.brm.scp.fw.helper.ExceptionHelper;
import br.com.brm.scp.fw.helper.RestHelper;

@Controller
@RequestMapping("pedido")
public class PedidoController extends RestHelper implements Serializable {

	private static final long serialVersionUID = 3060454714771179088L;

	private static final String PEDIDO_CRIADO_COM_SUCESSO = "pedido.criado";

	private static final String PEDIDO_CANCELADO_COM_SUCESSO = "pedido.cancelado";

	private static final String PEDIDO_ESCALONADO_COM_SUCESSO = "pedido.escalonado";

	private static final String PEDIDO_LIBERADO_COM_SUCESSO = "pedido.liberado";

	private static Logger logger = Logger.getLogger(PedidoController.class);
	
	@Autowired
	private PedidoService service;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<ReturnMessage<PedidoResponseDTO>> create(@RequestBody PedidoRequestDTO request) {
		
		ReturnMessage<PedidoResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			PedidoResponseDTO response = service.request(request.getOrigem(), request.getQuantidade(), request.getDataSolicitacao(), request.getDescricao(), request.isEscalonada());
			
			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(PEDIDO_CRIADO_COM_SUCESSO));
			
		} catch (Exception e) {
			
			logger.info(e);
			
			status = HttpStatus.BAD_REQUEST;
			if(null != e.getMessage()){
				restResponse.setHttpMensagem(getLabel(e.getMessage()));
			}else{
				restResponse.setHttpMensagem(getLabel("system.erro"));
			}
			restResponse.setIco(MessageBootstrap.DANGER);

			restResponse.setDetalhe(ExceptionHelper.toString(e));
		}
		
		return new ResponseEntity<>(restResponse, status);
	}
	
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	PedidoResponseDTO get(@PathVariable("id") String id) {
		try {
			return service.find(id);
		} catch (PedidoNotFoundException e) {
			throw new PedidoNotFoundWebException(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="escalonar/{id}", method = RequestMethod.POST)
	ResponseEntity<ReturnMessage<PedidoResponseDTO>> escalonar(@PathVariable("id") String id) {
		ReturnMessage<PedidoResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		try {
			PedidoResponseDTO response = service.escalonar(id);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(PEDIDO_ESCALONADO_COM_SUCESSO));
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;

			restResponse.setHttpMensagem(getLabel(e.getMessage()));
			restResponse.setIco(MessageBootstrap.DANGER);
			
			restResponse.setDetalhe(ExceptionHelper.toString(e));
		}
		return new ResponseEntity<>(restResponse, status);
	}
	
	@ResponseBody
	@RequestMapping(value="liberar/{id}", method = RequestMethod.POST)
	ResponseEntity<ReturnMessage<PedidoResponseDTO>> liberar(@PathVariable("id") String id) {
		ReturnMessage<PedidoResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.CREATED;
		try {
			PedidoResponseDTO response = service.liberar(id);

			restResponse.setResult(response);
			restResponse.setHttpMensagem(getLabel(PEDIDO_LIBERADO_COM_SUCESSO));
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;

			restResponse.setHttpMensagem(getLabel(e.getMessage()));
			restResponse.setIco(MessageBootstrap.DANGER);
			
			restResponse.setDetalhe(ExceptionHelper.toString(e));
		}
		return new ResponseEntity<>(restResponse, status);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "sku/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<PedidoResponseDTO> list(@PathVariable("id") String id) {
		Collection<PedidoResponseDTO> list = new ArrayList<>();
		try {
			list = service.listByOrigem(id);
		} catch (PedidoNotFoundException e) {
			logger.info(e);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value= "{id}", method = RequestMethod.DELETE)
	ResponseEntity<ReturnMessage<PedidoResponseDTO>> delete(@PathVariable("id") String id) {
		ReturnMessage<PedidoResponseDTO> restResponse = new ReturnMessage<>();
		HttpStatus status = HttpStatus.OK;
		try {
			service.delete(id);
			restResponse.setHttpMensagem(getLabel(PEDIDO_CANCELADO_COM_SUCESSO));
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;

			restResponse.setHttpMensagem(getLabel(e.getMessage()));
			restResponse.setIco(MessageBootstrap.DANGER);
			
			restResponse.setDetalhe(ExceptionHelper.toString(e));
		}
		
		return new ResponseEntity<>(restResponse, status);
	}
	
	@ResponseBody
	@RequestMapping(value = "monitoramento", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<PedidoVO> monitoramento(){
		return service.monitoramento();
	}
	
}
