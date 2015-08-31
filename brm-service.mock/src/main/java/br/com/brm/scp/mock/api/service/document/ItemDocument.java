package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.fw.annotations.BindingClass;
import br.com.brm.scp.mock.api.service.status.ItemStatus;

public class ItemDocument implements Serializable {

	private static final long serialVersionUID = -852183949869132845L;
	
	private Long id;
	private String nome;
	private String nomeReduzido;
	@BindingClass(CategoriaResponseDTO.class)
	private CategoriaDocument categoria;
	private ItemStatus status;
	private BigDecimal valorUnitario;
	private Integer quantidadeLote;
	@BindingClass(FornecedorResponseDTO.class)
	private FornecedorDocument fornecedor;
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

	public CategoriaDocument getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDocument categoria) {
		this.categoria = categoria;
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

	public FornecedorDocument getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorDocument fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}

	public Integer getQuantidadeLote() {
		return quantidadeLote;
	}

	public void setQuantidadeLote(Integer quantidadeLote) {
		this.quantidadeLote = quantidadeLote;
	}
	
}
