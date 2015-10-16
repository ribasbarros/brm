package br.com.brm.scp.api.dto.response;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.MatrizSkuDTO;
import br.com.brm.scp.api.service.status.PlanejamentoDfu;

public class DfuResponseDTO {
	private String id;
	@BindingClassMeta("ITEM")
	private ItemResponseDTO produto;
	@BindingClassMeta("TAGS")
	private Collection<TagResponseDTO> tags;
	private Date dataMaturidade;
	private Date dataLancamento;
	private Date dataDescontinuacao;
	@BindingClassMeta("TAGS")
	private PacotePlanoDTO pacotePlano;
	private PlanejamentoDfu modelo;
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
	@BindingClassMeta("MATRIZES")
	private Collection<MatrizSkuDTO> matriz;
	private Date dataCriacao;
	private Date dataAlteracao;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioAlteracao;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ItemResponseDTO getProduto() {
		return produto;
	}
	public void setProduto(ItemResponseDTO produto) {
		this.produto = produto;
	}
	public Collection<TagResponseDTO> getTags() {
		return tags;
	}
	public void setTags(Collection<TagResponseDTO> tags) {
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
	public PacotePlanoDTO getPacotePlano() {
		return pacotePlano;
	}
	public void setPacotePlano(PacotePlanoDTO pacotePlano) {
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
	public Collection<MatrizSkuDTO> getMatriz() {
		return matriz;
	}
	public void setMatriz(Collection<MatrizSkuDTO> matriz) {
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
	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public UsuarioResponseDTO getUsuarioAlteracao() {
		return usuarioAlteracao;
	}
	public void setUsuarioAlteracao(UsuarioResponseDTO usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
}
