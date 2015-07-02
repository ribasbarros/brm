package br.com.brm.scp.api.dto.request;

import java.math.BigDecimal;
import java.sql.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

public class DfuRequestDTO {

	public Long id;
	@BindingClassMeta("ITEM")
	public ItemRequestDTO item;
	public String dim1;
	public String dim2;
	public Date dataMaturidade;
	public Date dataLancamento;
	public Date dataDescontinuacao;
	public Date validadeModelo;
	public BigDecimal precoUnitarioMedio;
	public BigDecimal margemUnitariaMedia;
	public double fatorAjustePeriodoAtual;
	public double percMaximoAjuste;
	public double percMinimoAjuste;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemRequestDTO getItem() {
		return item;
	}

	public void setItem(ItemRequestDTO item) {
		this.item = item;
	}

	public String getDim1() {
		return dim1;
	}

	public void setDim1(String dim1) {
		this.dim1 = dim1;
	}

	public String getDim2() {
		return dim2;
	}

	public void setDim2(String dim2) {
		this.dim2 = dim2;
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

	public BigDecimal getMargemUnitariaMedia() {
		return margemUnitariaMedia;
	}

	public void setMargemUnitariaMedia(BigDecimal margemUnitariaMedia) {
		this.margemUnitariaMedia = margemUnitariaMedia;
	}

	public double getFatorAjustePeriodoAtual() {
		return fatorAjustePeriodoAtual;
	}

	public void setFatorAjustePeriodoAtual(double fatorAjustePeriodoAtual) {
		this.fatorAjustePeriodoAtual = fatorAjustePeriodoAtual;
	}

	public double getPercMaximoAjuste() {
		return percMaximoAjuste;
	}

	public void setPercMaximoAjuste(double percMaximoAjuste) {
		this.percMaximoAjuste = percMaximoAjuste;
	}

	public double getPercMinimoAjuste() {
		return percMinimoAjuste;
	}

	public void setPercMinimoAjuste(double percMinimoAjuste) {
		this.percMinimoAjuste = percMinimoAjuste;
	}

}
