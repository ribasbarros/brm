package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.brm.scp.api.dto.RelacionamentoSkuDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.service.status.ClasseEnum;
import br.com.brm.scp.api.service.status.PlanejamentoDfu;
import br.com.brm.scp.fw.annotations.BindingClass;

@Document
public class DfuDocument implements Serializable {

	private static final long serialVersionUID = -474555671538384916L;

	@Id
	private String id;

	@DBRef
	@BindingClass(ItemResponseDTO.class)
	private ItemDocument item;

	@DBRef
	@BindingClass(TagResponseDTO.class)
	private Collection<TagDocument> tags;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataMaturidade;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataLancamento;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataDescontinuacao;

	private ClasseEnum classe;

	private PlanejamentoDfu modelo;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date validadeModelo;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date validadePrimeiraSaida;

	/**
	 * Indica a fase de vida da DFU (nova, madura, descontinuada ou em
	 * descontinuação).
	 */
	private String faseVida;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataUltimaModelagem;

	@DBRef
	@BindingClass(UsuarioResponseDTO.class)
	private UsuarioDocument responsavelUltimaModelagem;

	private double ddv;

	private double desvioPadrao;

	/**
	 * Coeficiente de Variação: Coeficiente de variação do DDV (Desvio
	 * Padrão/DDV).
	 */
	private double variacao;

	/**
	 * Intermitência Diária: Porcentagem de dias no mes em que não há venda.
	 */
	private double percDiaMesSemVendas;

	/**
	 * Intermitência Mensal: Porcentagem de meses no ano que não há venda.
	 */
	private double percMesSemVendas;
	
	//TODO RIBAS REVER ESSA PORRA QUE TA ERRADA
	@DBRef
	@BindingClass(RelacionamentoSkuDTO.class)
	private Collection<RelacaoSkuDocument> relacaoSku;

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

	public ClasseEnum getClasse() {
		return classe;
	}

	public void setClasse(ClasseEnum classe) {
		this.classe = classe;
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

	public Date getValidadePrimeiraSaida() {
		return validadePrimeiraSaida;
	}

	public void setValidadePrimeiraSaida(Date validadePrimeiraSaida) {
		this.validadePrimeiraSaida = validadePrimeiraSaida;
	}

	public String getFaseVida() {
		return faseVida;
	}

	public void setFaseVida(String faseVida) {
		this.faseVida = faseVida;
	}

	public Date getDataUltimaModelagem() {
		return dataUltimaModelagem;
	}

	public void setDataUltimaModelagem(Date dataUltimaModelagem) {
		this.dataUltimaModelagem = dataUltimaModelagem;
	}

	public UsuarioDocument getResponsavelUltimaModelagem() {
		return responsavelUltimaModelagem;
	}

	public void setResponsavelUltimaModelagem(UsuarioDocument responsavelUltimaModelagem) {
		this.responsavelUltimaModelagem = responsavelUltimaModelagem;
	}

	public double getDdv() {
		return ddv;
	}

	public void setDdv(double ddv) {
		this.ddv = ddv;
	}

	public double getDesvioPadrao() {
		return desvioPadrao;
	}

	public void setDesvioPadrao(double desvioPadrao) {
		this.desvioPadrao = desvioPadrao;
	}

	public double getVariacao() {
		return variacao;
	}

	public void setVariacao(double variacao) {
		this.variacao = variacao;
	}

	public double getPercDiaMesSemVendas() {
		return percDiaMesSemVendas;
	}

	public void setPercDiaMesSemVendas(double percDiaMesSemVendas) {
		this.percDiaMesSemVendas = percDiaMesSemVendas;
	}

	public double getPercMesSemVendas() {
		return percMesSemVendas;
	}

	public void setPercMesSemVendas(double percMesSemVendas) {
		this.percMesSemVendas = percMesSemVendas;
	}

	public Collection<RelacaoSkuDocument> getRelacaoSku() {
		return relacaoSku;
	}

	public void setRelacaoSku(Collection<RelacaoSkuDocument> relacaoSku) {
		this.relacaoSku = relacaoSku;
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

	public ItemDocument getItem() {
		return item;
	}

	public void setItem(ItemDocument item) {
		this.item = item;
	}

}
