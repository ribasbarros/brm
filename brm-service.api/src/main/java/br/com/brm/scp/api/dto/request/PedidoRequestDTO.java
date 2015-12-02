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
import br.com.brm.scp.api.service.status.PedidoType;

public class PedidoRequestDTO implements Serializable {

	private static final long serialVersionUID = -7450117899499419643L;

	@Id
	private String id;

	private PedidoType tipo = PedidoType.INTERNO;
	
	private String origem;

	private String destino;

	private Integer quantidade;

	private PedidoStatus status;

	/*
	 * INDICA QUE UMA SKU NAO TINHA A QUANTIDADE NECESSARIA EM ESTOQUE PARA
	 * LIBERAR, ENTÃO FOI CRIADA UM NOVO PEDIDO PARA CIMA. ex: SKU1 solicita
	 * 1000 -> SKU2 não tem 1000, entao ela pede para o de cima OBS: BASICAMENTE
	 * SERVE PARA ESCONDER O BOTAO SEND DA DATABLE PEDIDOS
	 */
	private boolean escalonada = false;

	private Date dataCriacao;

	private Date dataSolicitacao;

	private Date dataFinalizacao;

	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;

	private String descricao;
	
	private String idPedidoDestino;

	private Collection<String> log = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
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

	public boolean isEscalonada() {
		return escalonada;
	}

	public void setEscalonada(boolean escalonada) {
		this.escalonada = escalonada;
	}

	public String getIdPedidoDestino() {
		return idPedidoDestino;
	}

	public void setIdPedidoDestino(String idPedidoDestino) {
		this.idPedidoDestino = idPedidoDestino;
	}

	public PedidoType getTipo() {
		return tipo;
	}

	public void setTipo(PedidoType tipo) {
		this.tipo = tipo;
	}

}
