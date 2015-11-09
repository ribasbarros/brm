package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.ContatoDTO;
import br.com.brm.scp.api.dto.FornecedorCentroDTO;

public class FornecedorResponseDTO extends ReturnMessage implements Serializable {

	private static final long serialVersionUID = -9082309408255141102L;

	private String id;
	private String razaoSocial;
	private String nomeFantasia;
	private String descricao;
	private String cnpj;

	@BindingClassMeta("CONTATO")
	private Collection<ContatoDTO> contatos = new ArrayList<>();

	@BindingClassMeta("CENTRO")
	private Collection<FornecedorCentroDTO> centros = new ArrayList<>();
	private Date dataCriacao;
	private Date dataAlteracao;
	
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;
	
	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
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

	public String getCnpj() {
/*		if (cnpj != null && cnpj.length() > 10) {
			// TODO Temporario
			cnpj = String.format("%s.%s.%s/%s-%s", cnpj.substring(0, 2), cnpj.substring(2, 5), cnpj.substring(5, 8),
					cnpj.substring(8, 12), cnpj.substring(12, 14));
		}
*/		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Collection<ContatoDTO> getContatos() {
		return contatos;
	}

	public void setContatos(Collection<ContatoDTO> contatos) {
		this.contatos = contatos;
	}

	public Collection<FornecedorCentroDTO> getCentros() {
		return centros;
	}

	public void setCentros(Collection<FornecedorCentroDTO> centros) {
		this.centros = centros;
	}

}
