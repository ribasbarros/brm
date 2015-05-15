package br.com.brm.scp.mock.api.service.strategies.impl;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.mock.api.service.strategies.interfaces.SkuStrategy;

public class SkuCreateStrategyImpl implements SkuStrategy {

	@Override
	public SkuResponseDTO create(SkuRequestDTO request) {
		System.out.println("CRIANDO SKU !");
		return null;
	}

}
