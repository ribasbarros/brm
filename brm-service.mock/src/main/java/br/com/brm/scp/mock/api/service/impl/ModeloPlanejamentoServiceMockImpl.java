package br.com.brm.scp.mock.api.service.impl;

import br.com.brm.scp.api.service.ModeloPlanejamentoService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class ModeloPlanejamentoServiceMockImpl implements ModeloPlanejamentoService {

	private MockData dbMock;

	public ModeloPlanejamentoServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

}
