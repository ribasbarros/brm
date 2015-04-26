package br.com.brm.scp.mock.api.service.document;

public class TelefoneDocument {

	private Integer numero;
	private boolean celular;
	private String Ramal;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
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
