package br.com.brm.scp.api.dto;

public class FornecedorCentroDTO {

	private String cnpj;
	private String cep;
	private Integer centro;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getCentro() {
		return centro;
	}

	public void setCentro(Integer centro) {
		this.centro = centro;
	}

	
}
