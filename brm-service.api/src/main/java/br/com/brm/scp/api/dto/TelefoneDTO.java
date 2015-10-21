package br.com.brm.scp.api.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class TelefoneDTO implements Serializable {

	private static final long serialVersionUID = -7074977133533414017L;
	
	private BigInteger numero;
	private boolean celular;
	private String ramal;

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
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

}
