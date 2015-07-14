package br.com.brm.scp.mock.api.service.db;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.objects.RandomHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.ItemDocument;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.document.TagDocument;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;

public class SkuOperationDB {

	private Logger logger = Logger.getLogger(SkuOperationDB.class);

	private MockData dbMock;

	public SkuOperationDB(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	public SkuDocument update(SkuDocument document) {
		dbMock.getSkuCollection().put(document.getId(), document);
		return document;
	}

	public void hasSku(SkuRequestDTO request) throws SkuExistenteException, SkuNotFoundException {
		try {

			SkuDocument loaded = null;
			if (request.getId() != null) {
				loaded = find(request.getId(), request.getItem(), request.getTags());
			} else {
				loaded = find(request.getItem(), request.getTags());
			}

			if (loaded != null) {
				logger.debug(String.format("Sku já cadastrada na base de id=%s", loaded.getId()));
				throw new SkuExistenteException();
			}
		} catch (SkuNotFoundException e) {
			throw new SkuNotFoundException();
		}
	}

	public SkuDocument insert(SkuDocument document) {

		long uid = RandomHelper.UUID();
		document.setId(uid);

		dbMock.getSkuCollection().put(uid, document);

		return document;

	}

	public SkuDocument find(Long id, ItemResponseDTO item, Collection<TagResponseDTO> tags)
			throws SkuNotFoundException {

		SkuDocument loaded = find(item, tags);

		if (!loaded.getId().equals(id)) {
			throw new SkuNotFoundException();
		}

		return loaded;

	}

	public SkuDocument find(ItemResponseDTO item, Collection<TagResponseDTO> tags) throws SkuNotFoundException {
		for (SkuDocument document : dbMock.getSkuCollection().values()) {
			Collection<TagDocument> collection = ConverterHelper.convert(tags, TagDocument.class);
			if ((!document.getStatus().equals(StatusReposicaoEnum.FINALIZADA)
					|| document.getStatus().equals(StatusReposicaoEnum.CANCELADA))
					&& document.getItem().getId().equals(item.getId()) && collection.equals(document.getTags())) {
				return document;
			}
		}
		throw new SkuNotFoundException();
	}
	
	public Collection<SkuDocument> findByItem(ItemDocument item){
		
		Collection<SkuDocument> result = new ArrayList<SkuDocument>();
		
		for (SkuDocument document : dbMock.getSkuCollection().values()) {
			if ((!document.getStatus().equals(StatusReposicaoEnum.FINALIZADA)
					|| document.getStatus().equals(StatusReposicaoEnum.CANCELADA))
					&& document.getItem().getId().equals(item.getId())) {
				result.add(document);
			}
		}
		
		return result;
		
	}

	public SkuDocument findOne(Long id) throws SkuNotFoundException {
		for (SkuDocument document : dbMock.getSkuCollection().values()) {
			if(document.getId().equals(id)){
				return document;
			}
		}
		throw new SkuNotFoundException();
	}

}
