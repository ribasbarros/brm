package br.com.brm.scp.mock.api.service.document;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.brm.scp.mock.api.service.document.status.StatusProduto;

public class ProdutoDocument {

	private Long id;
	private String nome;
	private String nomeReduzido;
	private CategoriaDocument categoria;
	private StatusProduto status;
	private BigDecimal valorUnitario;
	private Collection<FornecedorDocument> fornecedor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public CategoriaDocument getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDocument categoria) {
		this.categoria = categoria;
	}

	public StatusProduto getStatus() {
		return status;
	}

	public void setStatus(StatusProduto status) {
		this.status = status;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Collection<FornecedorDocument> getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Collection<FornecedorDocument> fornecedor) {
		this.fornecedor = fornecedor;
	}

}
