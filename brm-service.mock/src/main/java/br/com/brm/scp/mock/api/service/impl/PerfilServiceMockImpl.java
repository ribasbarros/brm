package br.com.brm.scp.mock.api.service.impl;

import br.com.brm.scp.api.service.PerfilService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class PerfilServiceMockImpl implements PerfilService {

	private MockData dbMock;

	public PerfilServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}
}