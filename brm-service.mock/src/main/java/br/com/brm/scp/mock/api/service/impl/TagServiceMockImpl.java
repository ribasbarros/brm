package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;

import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.TagDocument;

public class TagServiceMockImpl implements TagService {

	private MockData dbMock;

	public TagServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	@Override
	public Collection<TagResponseDTO> find() {
		
		Collection<TagDocument> all = findAll();
		return null;
		
		//return convert2Response(all);
	}

	private <T> Collection<Object> convert2Response(Collection<T> all) {

		for(T element : all){
			
		}
		
		return null;
	}

	private Collection<TagDocument> findAll() {
		return dbMock.getTagDocument().values();
	}

	@Override
	public Collection<TagResponseDTO> find(Object[] array) {
		// TODO Auto-generated method stub
		return null;
	}
}
