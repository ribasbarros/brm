package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.service.status.PedidoStatus;

public class PedidoRequestDTO implements Serializable {

	private static final long serialVersionUID = -7450117899499419643L;

	@Id
	private String id;

	@BindingClassMeta("SKU")
	private SkuResponseDTO origem;

	@BindingClassMeta("SKU")
	private SkuResponseDTO destino;

	private Integer quantidade;

	private PedidoStatus status;

	private Date dataCriacao;

	private Date dataSolicitacao;

	private Date dataFinalizacao;

	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;

	private String descricao;

	private Collection<String> log = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SkuResponseDTO getOrigem() {
		return origem;
	}

	public void setOrigem(SkuResponseDTO origem) {
		this.origem = origem;
	}

	public SkuResponseDTO getDestino() {
		return destino;
	}

	public void setDestino(SkuResponseDTO destino) {
		this.destino = destino;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public PedidoStatus getStatus() {
		return status;
	}

	public void setStatus(PedidoStatus status) {
		this.status = status;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<String> getLog() {
		return log;
	}

	public void setLog(Collection<String> log) {
		this.log = log;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
