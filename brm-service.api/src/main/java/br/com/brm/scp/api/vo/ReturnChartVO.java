package br.com.brm.scp.api.vo;

import java.io.Serializable;
import java.util.Collection;

import br.com.brm.scp.api.dto.response.SkuResponseDTO;

public class ReturnChartVO implements Serializable {

	private static final long serialVersionUID = -6643932762947761846L;

	private SkuResponseDTO response;

	private Collection<PedidoVO> demanda;

	public SkuResponseDTO getResponse() {
		return response;
	}

	public void setResponse(SkuResponseDTO response) {
		this.response = response;
	}

	public Collection<PedidoVO> getDemanda() {
		return demanda;
	}

	public void setDemanda(Collection<PedidoVO> demanda) {
		this.demanda = demanda;
	}

}
