package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.request.OrigemSkuResponseDTO;
import br.com.brm.scp.api.service.status.ClasseEnum;
import br.com.brm.scp.api.service.status.PlanejamentoSku;
import br.com.brm.scp.api.service.status.StatusReposicaoEnum;

public class SkuResponseDTO implements Serializable {

	private static final long serialVersionUID = 8549468897805592125L;

	private String id;

	private ClasseEnum classe;

	@BindingClassMeta("ITEM")
	private ItemResponseDTO item;

	@BindingClassMeta("TAGS")
	private Collection<TagResponseDTO> tags;

	private Date dataMaturidade;

	private Date dataDescontinuacao;
	private PlanejamentoSku modelo;

	private Integer[] frequenciaAnalise;
	private boolean automatica;
	private StatusReposicaoEnum status;
	private String descricao;

	private double nivelServico;

	private Long estoqueMaximo;
	private Long estoqueSeguranca;
	private Long estoqueAtual;

	private BigDecimal custoUnitario;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataCriacao;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataAlteracao;

	@BindingClassMeta("ORIGEM")
	private Collection<OrigemSkuResponseDTO> origens;

	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;

	private Integer loteReposicao;

	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Date getDataMaturidade() {
		return dataMaturidade;
	}

	public void setDataMaturidade(Date dataMaturidade) {
		this.dataMaturidade = dataMaturidade;
	}

	public Date getDataDescontinuacao() {
		return dataDescontinuacao;
	}

	public void setDataDescontinuacao(Date dataDescontinuacao) {
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

	public Long getEstoqueMaximo() {
		return estoqueMaximo;
	}

	public void setEstoqueMaximo(Long estoqueMaximo) {
		this.estoqueMaximo = estoqueMaximo;
	}

	public Long getEstoqueSeguranca() {
		return estoqueSeguranca;
	}

	public void setEstoqueSeguranca(Long estoqueSeguranca) {
		this.estoqueSeguranca = estoqueSeguranca;
	}

	public Long getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(Long estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public BigDecimal getCustoUnitario() {
		return custoUnitario;
	}

	public void setCustoUnitario(BigDecimal custoUnitario) {
		this.custoUnitario = custoUnitario;
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

	public Collection<OrigemSkuResponseDTO> getOrigens() {
		return origens;
	}

	public void setOrigens(Collection<OrigemSkuResponseDTO> origens) {
		this.origens = origens;
	}

	public ClasseEnum getClasse() {
		return classe;
	}

	public void setClasse(ClasseEnum classe) {
		this.classe = classe;
	}

	public double getNivelServico() {
		return nivelServico;
	}

	public void setNivelServico(double nivelServico) {
		this.nivelServico = nivelServico;
	}

	public Integer getLoteReposicao() {
		return loteReposicao;
	}

	public void setLoteReposicao(Integer loteReposicao) {
		this.loteReposicao = loteReposicao;
	}

}
