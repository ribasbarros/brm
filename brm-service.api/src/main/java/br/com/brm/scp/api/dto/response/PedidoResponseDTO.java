package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.Calendar;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.service.status.StatusPedido;

public class PedidoResponseDTO implements Serializable {

	private static final long serialVersionUID = 754950276721603193L;
	
	private Long id;
	private Long idSku;
	private String descricao;
	private Integer quantidade;
	private StatusPedido status;

	private Calendar dataCriacao;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public Long getIdSku() {
		return idSku;
	}

	public void setIdSku(Long idSku) {
		this.idSku = idSku;
	}

}
