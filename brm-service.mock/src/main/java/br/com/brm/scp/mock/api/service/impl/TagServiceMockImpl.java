package br.com.brm.scp.mock.api.service.impl;

import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class TagServiceMockImpl implements TagService {

	private MockData dbMock;

	public TagServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}
}
