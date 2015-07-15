package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.CategoriaDTO;
import br.com.brm.scp.mock.api.service.status.StatusProduto;

public class ItemResponseDTO implements Serializable {
	
	private static final long serialVersionUID = -6212936946563901667L;
	
	private Long id;
	private String nome;
	private String nomeReduzido;
	@BindingClassMeta("CATEGORIA")
	private CategoriaResponseDTO categoria;
	private StatusProduto status;
	private BigDecimal valorUnitario;
	@BindingClassMeta("FORNECEDOR")
	private FornecedorResponseDTO fornecedor;
	private Date dataExcluido;
	
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

	public CategoriaResponseDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaResponseDTO categoria) {
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

	public FornecedorResponseDTO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorResponseDTO fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}

}
