package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.brm.scp.api.dto.ContatoDTO;
import br.com.brm.scp.api.dto.FornecedorCentroDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.fw.annotations.BindingClass;

@Document
public class FornecedorDocument implements Serializable {

	private static final long serialVersionUID = -9082309408255141102L;

	@Id
	private String id;
	private String razaoSocial;
	private String nomeFantasia;
	private String descricao;
	private String cnpj;

	@BindingClass(ContatoDTO.class)
	private Collection<ContatoDocument> contatos = new ArrayList<>();

	@BindingClass(FornecedorCentroDTO.class)
	private Collection<FornecedorCentroDocument> centros = new ArrayList<>();

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataCriacao;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataAlteracao;
	
	@BindingClass(UsuarioResponseDTO.class)
	private UsuarioDocument usuarioCriacao;
	
	
	public UsuarioDocument getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioDocument usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<FornecedorCentroDocument> getCentros() {
		return centros;
	}

	public void setCentros(Collection<FornecedorCentroDocument> centros) {
		this.centros = centros;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Collection<ContatoDocument> getContatos() {
		return contatos;
	}

	public void setContatos(Collection<ContatoDocument> contatos) {
		this.contatos = contatos;
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
