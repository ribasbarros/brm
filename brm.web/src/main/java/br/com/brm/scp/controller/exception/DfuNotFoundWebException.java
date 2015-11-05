package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Registro não encontrado")  // 404
public class DfuNotFoundWebException extends RuntimeException {

	public DfuNotFoundWebException(String message) {
		super (message);
	}

	private static final long serialVersionUID = 3287955523960899120L;

}
