package br.com.brm.scp.mock.api.service.impl;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class SkuServiceMockImpl implements SkuService {

	private MockData dbMock;
	
	public SkuServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	@Override
	public boolean hasSku(SkuRequestDTO skuRequestSuccess) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
