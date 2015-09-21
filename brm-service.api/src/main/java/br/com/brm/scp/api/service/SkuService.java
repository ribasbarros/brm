package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;

public interface SkuService {

	SkuResponseDTO create(SkuRequestDTO request) throws SkuExistenteException;

	Collection<SkuResponseDTO> search(Object value) throws SkuNotFoundException;

}
