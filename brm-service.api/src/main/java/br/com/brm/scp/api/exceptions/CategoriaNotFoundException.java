package br.com.brm.scp.api.exceptions;

public class CategoriaNotFoundException extends Exception{
	private static final long serialVersionUID = 9079808779979645664L;

	public CategoriaNotFoundException(String string) {
		super(string);
	}
	
	public CategoriaNotFoundException() {
		super();
	}
}
