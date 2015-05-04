package br.com.brm.scp.mock.api.service.impl;

import br.com.brm.scp.api.service.GrupoService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class CalculoServiceMockImpl implements GrupoService {

	private MockData dbMock;

	public CalculoServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}
}
