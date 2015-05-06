package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;

import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.mock.api.mockdata.MockData;

public class TagServiceMockImpl implements TagService {

	private MockData dbMock;

	public TagServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	@Override
	public Collection<TagResponseDTO> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TagResponseDTO> find(Object[] array) {
		// TODO Auto-generated method stub
		return null;
	}
}
