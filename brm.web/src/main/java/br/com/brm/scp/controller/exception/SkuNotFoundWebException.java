package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Registro não encontrado")
public class SkuNotFoundWebException extends RuntimeException {

	private static final long serialVersionUID = 571342484703368563L;

	public SkuNotFoundWebException(String message) {
		super(message);
	}

}
