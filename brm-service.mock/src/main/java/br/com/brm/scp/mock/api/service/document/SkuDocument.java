package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.fw.annotations.BindingClass;
import br.com.brm.scp.mock.api.service.status.PlanejamentoSku;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;

public class SkuDocument implements Serializable {

	private static final long serialVersionUID = 5204544337090545867L;

	private Long id;
	@BindingClass(ItemResponseDTO.class)
	private ItemDocument item;
	@BindingClass(TagResponseDTO.class)
	private Collection<TagDocument> tags;
	private Calendar dataMaturidade;
	private Calendar dataDescontinuacao;
	private PlanejamentoSku modelo;
	private Integer[] frequenciaAnalise;
	private boolean automatica;
	private Integer loteReposicao;
	private Integer loteReposicaoHistorico;
	private StatusReposicaoEnum status;
	private String descricao;
	@BindingClass(PedidoResponseDTO.class)
	private Collection<PedidoDocument> pedidos;

	private Integer estoqueMaximo;
	private Integer estoqueMinimo;
	private Integer estoqueIdeal;
	private Integer estoqueAtual;

	private BigDecimal custoUnitario;

	private Calendar dataCriacao;
	private Calendar dataAlteracao;
	private UsuarioDocument usuarioCriacao;
	private UsuarioDocument usuarioAlteracao;

	@BindingClass(SkuDocument.class)
	private Collection<SkuDocument> origins;

	@BindingClass(SkuDocument.class)
	private SkuDocument originDefault;

	public ItemDocument getItem() {
		return item;
	}

	public void setItem(ItemDocument item) {
		this.item = item;
	}

	public Collection<TagDocument> getTags() {
		return tags;
	}

	public void setTags(Collection<TagDocument> tags) {
		this.tags = tags;
	}

	public Calendar getDataMaturidade() {
		return dataMaturidade;
	}

	public void setDataMaturidade(Calendar dataMaturidade) {
		this.dataMaturidade = dataMaturidade;
	}

	public Calendar getDataDescontinuacao() {
		return dataDescontinuacao;
	}

	public void setDataDescontinuacao(Calendar dataDescontinuacao) {
		this.dataDescontinuacao = dataDescontinuacao;
	}

	public PlanejamentoSku getModelo() {
		return modelo;
	}

	public void setModelo(PlanejamentoSku modelo) {
		this.modelo = modelo;
	}

	public Integer[] getFrequenciaAnalise() {
		return frequenciaAnalise;
	}

	public void setFrequenciaAnalise(Integer[] frequenciaAnalise) {
		this.frequenciaAnalise = frequenciaAnalise;
	}

	public boolean isAutomatica() {
		return automatica;
	}

	public void setAutomatica(boolean automatica) {
		this.automatica = automatica;
	}

	public Integer getLoteReposicao() {
		return loteReposicao;
	}

	public void setLoteReposicao(Integer loteReposicao) {
		this.loteReposicao = loteReposicao;
	}

	public Integer getLoteReposicaoHistorico() {
		return loteReposicaoHistorico;
	}

	public void setLoteReposicaoHistorico(Integer loteReposicaoHistorico) {
		this.loteReposicaoHistorico = loteReposicaoHistorico;
	}

	public StatusReposicaoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusReposicaoEnum status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getEstoqueMaximo() {
		return estoqueMaximo;
	}

	public void setEstoqueMaximo(Integer estoqueMaximo) {
		this.estoqueMaximo = estoqueMaximo;
	}

	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public Integer getEstoqueIdeal() {
		return estoqueIdeal;
	}

	public void setEstoqueIdeal(Integer estoqueIdeal) {
		this.estoqueIdeal = estoqueIdeal;
	}

	public Integer getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(Integer estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public BigDecimal getCustoUnitario() {
		return custoUnitario;
	}

	public void setCustoUnitario(BigDecimal custoUnitario) {
		this.custoUnitario = custoUnitario;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Calendar getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Calendar dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public UsuarioDocument getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioDocument usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public UsuarioDocument getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(UsuarioDocument usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Collection<PedidoDocument> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<PedidoDocument> pedidos) {
		this.pedidos = pedidos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<SkuDocument> getOrigins() {
		return origins;
	}

	public void setOrigins(Collection<SkuDocument> origins) {
		this.origins = origins;
	}

	public SkuDocument getOriginDefault() {
		return originDefault;
	}

	public void setOriginDefault(SkuDocument originDefault) {
		this.originDefault = originDefault;
	}

}
