package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.status.MessageBootstrap;

public class ReturnMessage<T> implements Serializable {

	private static final long serialVersionUID = 4207286067198576033L;

	private String httpMensagem;
	private String ico = MessageBootstrap.SUCCESS;
	private T result;
	private String go;
	private String detalhe;

	public ReturnMessage() {
		super();
	}

	public String getHttpMensagem() {
		return httpMensagem;
	}

	public void setHttpMensagem(String httpMensagem) {
		this.httpMensagem = httpMensagem;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getGo() {
		return go;
	}

	public void setGo(String go) {
		this.go = go;
	}

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

}
