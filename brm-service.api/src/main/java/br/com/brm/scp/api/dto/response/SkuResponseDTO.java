package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.mock.api.service.status.PlanejamentoSku;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;

public class SkuResponseDTO implements Serializable {

	private static final long serialVersionUID = 7347879265505042643L;

	private Long id;
	@BindingClassMeta("ITEM")
	private ItemResponseDTO item;
	@BindingClassMeta("TAGS")
	private Collection<TagResponseDTO> tags;
	private Calendar dataMaturidade;
	private Calendar dataDescontinuacao;
	private PlanejamentoSku modelo;
	private Integer[] frequenciaAnalise;
	private boolean automatica;
	private Integer loteReposicao;
	private Integer loteReposicaoHistorico;
	private StatusReposicaoEnum status;
	private String descricao;
	@BindingClassMeta("PEDIDOS")
	private Collection<PedidoResponseDTO> pedidos;

	private Integer estoqueMaximo;
	private Integer estoqueMinimo;
	private Integer estoqueIdeal;
	private Integer estoqueAtual;

	private BigDecimal custoUnitario;

	private Calendar dataCriacao;
	private Calendar dataAlteracao;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;
	private UsuarioResponseDTO usuarioAlteracao;

	@BindingClassMeta("ORIGIN")
	private Collection<SkuResponseDTO> origins;

	@BindingClassMeta("ORIGIN")
	private SkuResponseDTO originDefault;

	public ItemResponseDTO getItem() {
		return item;
	}

	public void setItem(ItemResponseDTO item) {
		this.item = item;
	}

	public Collection<TagResponseDTO> getTags() {
		return tags;
	}

	public void setTags(Collection<TagResponseDTO> tags) {
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

	public Collection<PedidoResponseDTO> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<PedidoResponseDTO> pedidos) {
		this.pedidos = pedidos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<SkuResponseDTO> getOrigins() {
		return origins;
	}

	public void setOrigins(Collection<SkuResponseDTO> origins) {
		this.origins = origins;
	}

	public SkuResponseDTO getOriginDefault() {
		return originDefault;
	}

	public void setOriginDefault(SkuResponseDTO originDefault) {
		this.originDefault = originDefault;
	}

}
