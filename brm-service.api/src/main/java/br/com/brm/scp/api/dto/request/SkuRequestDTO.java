package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.service.status.ClasseEnum;
import br.com.brm.scp.api.service.status.PlanejamentoSku;
import br.com.brm.scp.api.service.status.StatusReposicaoEnum;

public class SkuRequestDTO implements Serializable {

	private static final long serialVersionUID = 5177237361996096611L;

	private String id;

	private ClasseEnum classe;

	@BindingClassMeta("ITEM")
	private ItemResponseDTO item;

	@BindingClassMeta("TAGS")
	private Collection<TagResponseDTO> tags;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataMaturidade;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataDescontinuacao;
	private PlanejamentoSku modelo;

	private Integer[] frequenciaAnalise;
	private boolean automatica;
	private StatusReposicaoEnum status;
	private String descricao;

	private Integer estoqueMaximo;
	private Integer estoqueMinimo;
	private Integer estoqueIdeal;
	private Integer estoqueAtual;

	private BigDecimal custoUnitario;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataCriacao;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataAlteracao;

	@BindingClassMeta("ORIGEM")
	private Collection<OrigemSkuResponseDTO> origens;

	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;

	
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

	public ObjectId[] toTagArray() {
		ObjectId[] tags = new ObjectId[getTags().size()];
		int cont = 0;
		for (TagResponseDTO dto : getTags())
			tags[cont++] = new ObjectId(dto.getId());

		return tags;
	}

}