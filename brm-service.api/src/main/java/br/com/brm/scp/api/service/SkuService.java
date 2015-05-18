package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;

public interface SkuService {

	boolean hasSku(SkuRequestDTO skuRequestSuccess);

}
