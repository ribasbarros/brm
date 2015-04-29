package br.com.brm.scp.mock.api.service.impl;

import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class SkuServiceMockImpl implements SkuService {

	private MockData dbMock;
	
	public SkuServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}
	
}
