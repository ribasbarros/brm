package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;

import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.TagDocument;

public class TagServiceMockImpl implements TagService {

	private Logger logger = Logger.getLogger(TagServiceMockImpl.class);
	
	private MockData dbMock;

	public TagServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	@Override
	public Collection<TagResponseDTO> find() {
		Collection<TagDocument> all = findAll();
		return ConverterHelper.convert(all, TagResponseDTO.class);
		
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
