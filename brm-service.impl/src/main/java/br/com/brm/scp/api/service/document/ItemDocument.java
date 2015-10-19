package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.service.status.ItemStatus;
import br.com.brm.scp.fw.annotations.BindingClass;

public class ItemDocument implements Serializable {

	private static final long serialVersionUID = -852183949869132845L;

	private String id;
	private String nome;
	private String nomeReduzido;
	private ItemStatus status;
	private BigDecimal valorUnitario;
	private Integer unitizacao; // Quantidade que vem fechado
	private String descricao;
	
	@DBRef
	@BindingClass(CategoriaResponseDTO.class)
	private CategoriaDocument categoria;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataCriacao;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataAlteracao;

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

	public CategoriaDocument getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDocument categoria) {
		this.categoria = categoria;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

}
