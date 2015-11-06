package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

public class ReturnMessage implements Serializable {

	private static final long serialVersionUID = 4207286067198576033L;

	private String httpStatus;
	private String httpMensagem;

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getHttpMensagem() {
		return httpMensagem;
	}

	public void setHttpMensagem(String httpMensagem) {
		this.httpMensagem = httpMensagem;
	}

}
