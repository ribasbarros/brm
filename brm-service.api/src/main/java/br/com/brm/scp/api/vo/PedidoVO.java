package br.com.brm.scp.api.vo;

import br.com.brm.scp.api.dto.response.SkuResponseDTO;

public class PedidoVO {

	private String origem;;
	private String quantidade;

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

}
