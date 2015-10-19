package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.DBRef;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.service.status.ItemStatus;

public class ItemRequestDTO implements Serializable {

	private static final long serialVersionUID = -7145945974196773757L;

	private String id;
	private String nome;
	private String nomeReduzido;
	private ItemStatus status;
	private BigDecimal valorUnitario;
	private Integer unitizacao; // Quantidade que vem fechado
	private String descricao;

	@DBRef
	@BindingClassMeta("CATEGORIA")
	private CategoriaResponseDTO categoria;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CategoriaResponseDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaResponseDTO categoria) {
		this.categoria = categoria;
	}

}
