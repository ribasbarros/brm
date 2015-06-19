package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.brm.scp.api.dto.CategoriaDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.fw.annotations.BindingClass;
import br.com.brm.scp.mock.api.service.status.StatusProduto;

public class ItemDocument implements Serializable {

	private static final long serialVersionUID = -852183949869132845L;
	
	private Long id;
	private String nome;
	private String nomeReduzido;
	@BindingClass(CategoriaDTO.class)
	private CategoriaDocument categoria;
	private StatusProduto status;
	private BigDecimal valorUnitario;
	@BindingClass(FornecedorResponseDTO.class)
	private FornecedorDocument fornecedor;

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

	public FornecedorDocument getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorDocument fornecedor) {
		this.fornecedor = fornecedor;
	}

}
