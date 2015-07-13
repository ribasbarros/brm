package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.SkuService;

public class SkuServiceImpl implements SkuService {

	@Override
	public SkuResponseDTO create(SkuRequestDTO skuRequestSuccess) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuResponseDTO ativar(SkuRequestDTO skuRequestSuccess) throws SkuNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SkuResponseDTO> findForOrigin(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuResponseDTO alterar(SkuRequestDTO request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuResponseDTO find(Long id) throws SkuNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
