package br.com.brm.scp.api.service.strategies.interfaces;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;

public interface SkuStrategy {

	public SkuResponseDTO save(SkuRequestDTO request) throws SkuException;
	public SkuResponseDTO save(SkuRequestDTO request, Long ... idUsuarioLogado) throws SkuException;
	
}
