package br.com.brm.scp.api.exceptions;

public class FornecedorException extends Exception {

	private static final long serialVersionUID = -2919318671873313397L;

	public FornecedorException() {
		super();
	}

	public FornecedorException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FornecedorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FornecedorException(String arg0) {
		super(arg0);
	}

	public FornecedorException(Throwable arg0) {
		super(arg0);
	}

}
