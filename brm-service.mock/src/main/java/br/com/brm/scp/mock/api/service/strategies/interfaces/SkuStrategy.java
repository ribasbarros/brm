package br.com.brm.scp.mock.api.service.strategies.interfaces;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;

public interface SkuStrategy {

	public SkuResponseDTO create(SkuRequestDTO request);
	
}
