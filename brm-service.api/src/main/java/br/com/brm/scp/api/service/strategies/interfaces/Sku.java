package br.com.brm.scp.api.service.strategies.interfaces;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;

public class Sku implements SkuStrategy {

	private SkuStrategy strategy;

	public Sku(SkuStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public SkuResponseDTO save(SkuRequestDTO request) throws SkuException {
		return strategy.save(request);
	}

}
