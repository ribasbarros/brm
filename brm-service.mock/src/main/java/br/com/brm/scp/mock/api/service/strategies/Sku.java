package br.com.brm.scp.mock.api.service.strategies;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.mock.api.service.strategies.interfaces.SkuStrategy;

public class Sku implements SkuStrategy {

	private SkuStrategy strategy;

	public Sku(SkuStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public SkuResponseDTO create(SkuRequestDTO request) {
		return strategy.create(request);
	}
	
	
	
}
