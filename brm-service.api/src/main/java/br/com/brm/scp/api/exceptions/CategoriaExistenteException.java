package br.com.brm.scp.api.exceptions;

public class CategoriaExistenteException extends Exception{
	private static final long serialVersionUID = 9079808779979645664L;

	public CategoriaExistenteException(String string) {
		super(string);
	}
	
	public CategoriaExistenteException() {
		super();
	}
}
