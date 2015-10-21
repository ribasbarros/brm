package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

import org.springframework.data.annotation.Id;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.request.RelacaoSkuResponseDTO;
import br.com.brm.scp.api.service.status.ClasseEnum;
import br.com.brm.scp.api.service.status.PlanejamentoDfu;

public class DfuResponseDTO implements Serializable {

	private static final long serialVersionUID = -975886870342616335L;

	@Id
	private String id;

	@BindingClassMeta("ITEM")
	private ItemResponseDTO item;

	@BindingClassMeta("TAGS")
	private Collection<TagResponseDTO> tags;

	private Date dataMaturidade;

	private Date dataLancamento;

	private Date dataDescontinuacao;

	private ClasseEnum classe;

	private PlanejamentoDfu modelo;

	private Date validadeModelo;

	private Date validadePrimeiraSaida;

	/**
	 * Indica a fase de vida da DFU (nova, madura, descontinuada ou em
	 * descontinuação).
	 */
	private String faseVida;

	private Date dataUltimaModelagem;

	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO responsavelUltimaModelagem;

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

	@BindingClassMeta("RELACAO")
	private Collection<RelacaoSkuResponseDTO> relacaoSku;

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

	public UsuarioResponseDTO getResponsavelUltimaModelagem() {
		return responsavelUltimaModelagem;
	}

	public void setResponsavelUltimaModelagem(UsuarioResponseDTO responsavelUltimaModelagem) {
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

	public Collection<RelacaoSkuResponseDTO> getRelacaoSku() {
		return relacaoSku;
	}

	public void setRelacaoSku(Collection<RelacaoSkuResponseDTO> relacaoSku) {
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
