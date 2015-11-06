package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Registro não encontrado")  // 404
public class UsuarioNotFoundWebException extends RuntimeException {

	public UsuarioNotFoundWebException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4976349939445917310L;


}
