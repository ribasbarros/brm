package br.com.brm.scp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Tag vinculada a um outro registro")  // 404
public class TagIsUsedWebException extends RuntimeException {

	public TagIsUsedWebException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4976349939445917310L;


}
