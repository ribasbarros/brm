package br.com.brm.scp.mock.api.service.impl;

import br.com.brm.scp.api.service.DfuService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class DfuServiceMockImpl implements DfuService {

	private MockData dbMock;

	public DfuServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	
	
	
}
