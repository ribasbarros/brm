package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Registro não encontrado") // 404
public class PedidoNotFoundWebException extends RuntimeException {

	private static final long serialVersionUID = -3568174860388050040L;

	public PedidoNotFoundWebException(String arg0) {
		super(arg0);
	}
	
}
