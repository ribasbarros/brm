package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Registro duplicado")  // 404
public class FornecedorExistenteWebException extends RuntimeException {

	private static final long serialVersionUID = 6893189746233975779L;

}