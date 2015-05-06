package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

public class DfuDocument implements Serializable {

	private static final long serialVersionUID = -474555671538384916L;
	
	private Long id;
	private ItemDocument produto;
	private Collection<TagDocument> tags;
	private Calendar dataMaturidade;
	private Calendar dataLancamento;
	private Calendar dataDescontinuacao;
	private PacotePlanoDocument pacotePlano;
	private ModeloPlanejamentoDocument modelo;
	private Calendar validadeModelo;
	private BigDecimal precoUnitarioMedio;
	private BigDecimal margemUnitMedio;
	private Double fatorAjustePeriodoAtual;
	private Double demandaRecuperada;
	private Double demandaAntecipada;
	private Double maxAjuste;
	private Double minAjuste;
	private Double representatividade;
	private Integer diaVenda;

	private Collection<MatrizSkuDocument> matriz;

	private Calendar dataCriacao;
	private Calendar dataAlteracao;
	private UsuarioDocument usuarioCriacao;
	private UsuarioDocument usuarioAlteracao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemDocument getProduto() {
		return produto;
	}

	public void setProduto(ItemDocument produto) {
		this.produto = produto;
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

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public Calendar getDataDescontinuacao() {
		return dataDescontinuacao;
	}

	public void setDataDescontinuacao(Calendar dataDescontinuacao) {
		this.dataDescontinuacao = dataDescontinuacao;
	}

	public PacotePlanoDocument getPacotePlano() {
		return pacotePlano;
	}

	public void setPacotePlano(PacotePlanoDocument pacotePlano) {
		this.pacotePlano = pacotePlano;
	}

	public ModeloPlanejamentoDocument getModelo() {
		return modelo;
	}

	public void setModelo(ModeloPlanejamentoDocument modelo) {
		this.modelo = modelo;
	}

	public Calendar getValidadeModelo() {
		return validadeModelo;
	}

	public void setValidadeModelo(Calendar validadeModelo) {
		this.validadeModelo = validadeModelo;
	}

	public BigDecimal getPrecoUnitarioMedio() {
		return precoUnitarioMedio;
	}

	public void setPrecoUnitarioMedio(BigDecimal precoUnitarioMedio) {
		this.precoUnitarioMedio = precoUnitarioMedio;
	}

	public BigDecimal getMargemUnitMedio() {
		return margemUnitMedio;
	}

	public void setMargemUnitMedio(BigDecimal margemUnitMedio) {
		this.margemUnitMedio = margemUnitMedio;
	}

	public Double getFatorAjustePeriodoAtual() {
		return fatorAjustePeriodoAtual;
	}

	public void setFatorAjustePeriodoAtual(Double fatorAjustePeriodoAtual) {
		this.fatorAjustePeriodoAtual = fatorAjustePeriodoAtual;
	}

	public Double getDemandaRecuperada() {
		return demandaRecuperada;
	}

	public void setDemandaRecuperada(Double demandaRecuperada) {
		this.demandaRecuperada = demandaRecuperada;
	}

	public Double getDemandaAntecipada() {
		return demandaAntecipada;
	}

	public void setDemandaAntecipada(Double demandaAntecipada) {
		this.demandaAntecipada = demandaAntecipada;
	}

	public Double getMaxAjuste() {
		return maxAjuste;
	}

	public void setMaxAjuste(Double maxAjuste) {
		this.maxAjuste = maxAjuste;
	}

	public Double getMinAjuste() {
		return minAjuste;
	}

	public void setMinAjuste(Double minAjuste) {
		this.minAjuste = minAjuste;
	}

	public Double getRepresentatividade() {
		return representatividade;
	}

	public void setRepresentatividade(Double representatividade) {
		this.representatividade = representatividade;
	}

	public Integer getDiaVenda() {
		return diaVenda;
	}

	public void setDiaVenda(Integer diaVenda) {
		this.diaVenda = diaVenda;
	}

	public Collection<MatrizSkuDocument> getMatriz() {
		return matriz;
	}

	public void setMatriz(Collection<MatrizSkuDocument> matriz) {
		this.matriz = matriz;
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

}
