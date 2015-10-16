package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.brm.scp.api.dto.MatrizSkuDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.PacotePlanoDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.service.status.PlanejamentoDfu;
import br.com.brm.scp.fw.annotations.BindingClass;

@Document
public class DfuDocument implements Serializable {
	private static final long serialVersionUID = -474555671538384916L;
	@Id
	private String id;
	@DBRef
	@BindingClass(ItemResponseDTO.class)
	private ItemDocument produto;
	@DBRef
	@BindingClass(TagResponseDTO.class)
	private Collection<TagDocument> tags;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataMaturidade;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataLancamento;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataDescontinuacao;
	@DBRef
	@BindingClass(PacotePlanoDTO.class)
	private PacotePlanoDocument pacotePlano;
	private PlanejamentoDfu modelo;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date validadeModelo;
	private BigDecimal precoUnitarioMedio;
	private BigDecimal margemUnitMedio;
	private Double fatorAjustePeriodoAtual;
	private Double demandaRecuperada;
	private Double demandaAntecipada;
	private Double maxAjuste;
	private Double minAjuste;
	private Double representatividade;
	private Integer diaVenda;
	@DBRef
	@BindingClass(MatrizSkuDTO.class)
	private Collection<MatrizSkuDocument> matriz;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataCriacao;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataAlteracao;
	@DBRef
	@BindingClass(UsuarioResponseDTO.class)
	private UsuarioDocument usuarioCriacao;
	@DBRef
	@BindingClass(UsuarioResponseDTO.class)
	private UsuarioDocument usuarioAlteracao;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Date getDataMaturidade() {
		return dataMaturidade;
	}
	public void setDataMaturidade(Date dataMaturidade) {
		this.dataMaturidade = dataMaturidade;
	}
	public Date getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public Date getDataDescontinuacao() {
		return dataDescontinuacao;
	}
	public void setDataDescontinuacao(Date dataDescontinuacao) {
		this.dataDescontinuacao = dataDescontinuacao;
	}
	public PacotePlanoDocument getPacotePlano() {
		return pacotePlano;
	}
	public void setPacotePlano(PacotePlanoDocument pacotePlano) {
		this.pacotePlano = pacotePlano;
	}
	public PlanejamentoDfu getModelo() {
		return modelo;
	}
	public void setModelo(PlanejamentoDfu modelo) {
		this.modelo = modelo;
	}
	public Date getValidadeModelo() {
		return validadeModelo;
	}
	public void setValidadeModelo(Date validadeModelo) {
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
