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
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.status.MessageBootstrap;
import br.com.brm.scp.api.vo.PedidoVO;
import br.com.brm.scp.fw.helper.ExceptionHelper;
import br.com.brm.scp.fw.helper.RestHelper;

@Controller
@RequestMapping("pedido")
public class PedidoController extends RestHelper implements Serializable {

	private static final long serialVersionUID = 3060454714771179088L;

	private static final String PEDIDO_CRIADO_COM_SUCESSO = "pedido.criado";

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
			PedidoResponseDTO response = service.request(request.getOrigem(), request.getQuantidade(), request.getDataSolicitacao(), request.getDescricao());
			
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
	@RequestMapping(value = "monitoramento", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	Collection<PedidoVO> monitoramento(){
		return service.monitoramento();
	}
	
}
