package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.math.BigInteger;

public class TelefoneDocument implements Serializable {

	private static final long serialVersionUID = -6082589107055198601L;

	private BigInteger numero;
	private boolean celular;
	private String Ramal;

	public BigInteger getNumero() {
		return numero;
	}

	public void setNumero(BigInteger numero) {
		this.numero = numero;
	}

	public boolean isCelular() {
		return celular;
	}

	public void setCelular(boolean celular) {
		this.celular = celular;
	}

	public String getRamal() {
		return Ramal;
	}

	public void setRamal(String ramal) {
		Ramal = ramal;
	}

}
