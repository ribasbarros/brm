package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Registro duplicado")  // 404
public class TagExistenteWebException extends RuntimeException {

	private static final long serialVersionUID = 8766500445271525733L;


}
