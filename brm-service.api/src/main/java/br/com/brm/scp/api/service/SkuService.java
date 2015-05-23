package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;

public interface SkuService {

	SkuResponseDTO create(SkuRequestDTO skuRequestSuccess);

}
