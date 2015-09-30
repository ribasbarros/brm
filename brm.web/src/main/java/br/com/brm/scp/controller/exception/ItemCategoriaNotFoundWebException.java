package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Registro n�o encontrado")  // 404
public class ItemCategoriaNotFoundWebException extends RuntimeException {

	private static final long serialVersionUID = 3287955523960899120L;

}
