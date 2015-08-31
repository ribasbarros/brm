package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.brm.scp.mock.api.service.status.ItemStatus;

public class ItemDocument implements Serializable {

	private static final long serialVersionUID = -852183949869132845L;

	private String id;
	private String nome;
	private String nomeReduzido;
	private ItemStatus status;
	private BigDecimal valorUnitario;
	private Integer unitizacao; // Quantidade que vem fechado

	private String idCategoria;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeReduzido() {
		return nomeReduzido;
	}

	public void setNomeReduzido(String nomeReduzido) {
		this.nomeReduzido = nomeReduzido;
	}

	public ItemStatus getStatus() {
		return status;
	}

	public void setStatus(ItemStatus status) {
		this.status = status;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Integer getUnitizacao() {
		return unitizacao;
	}

	public void setUnitizacao(Integer unitizacao) {
		this.unitizacao = unitizacao;
	}

	public String getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}

}
