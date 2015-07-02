package br.com.brm.scp.mock.api.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.objects.RandomHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.document.TagDocument;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;

public class SkuServiceMockImpl implements SkuService {

	private Logger logger = Logger.getLogger(SkuServiceMockImpl.class);
	
	private MockData dbMock;
	
	public SkuServiceMockImpl(MockData dbMock) {
		super();
		this.dbMock = dbMock;
	}

	@Override
	public SkuResponseDTO create(SkuRequestDTO request) throws SkuExistenteException {
		
		Assert.notNull(request.getItem(), "sku.itemnaopreenchido");
		Assert.notEmpty(request.getTags(), "sku.tagnaopreenchida");
		
		hasSku(request);
		
		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		
		document.setStatus(StatusReposicaoEnum.RASCUNHO);
		
		document = insert(document);
		
		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
		
	}

	@Override
	public SkuResponseDTO ativar(SkuRequestDTO request) throws SkuNotFoundException {
		
		Assert.notNull(request.getId(), "sku.idinvalido");
		Assert.notNull(request.getItem(), "sku.itemnaopreenchido");
		Assert.notEmpty(request.getTags(), "sku.tagnaopreenchida");
		
		try {
			hasSku(request);
		} catch (SkuExistenteException e) {
		}
		
		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		document.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		
		document = update(document);
		
		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
		
	}
	
	private SkuDocument update(SkuDocument document) {
		dbMock.getSkuCollection().put(document.getId(), document);
		return document;
	}

	private void hasSku(SkuRequestDTO request) throws SkuExistenteException {
		try {
			
			SkuDocument loaded = null;
			if(request.getId() != null){
				loaded = find(request.getId(), request.getItem(), request.getTags());
			}else{
				loaded = find(request.getItem(), request.getTags());
			}
			
			if(loaded == null){
				logger.debug(String.format("Sku já cadastrada na base de id=%s", loaded.getId()));
				throw new SkuExistenteException();
			}
		} catch (SkuNotFoundException e) {
			logger.info("Sku não cadastrada, cadastro autorizado");
		}
	}
	
	private SkuDocument insert(SkuDocument document) {
		
		long uid = RandomHelper.UUID();
		document.setId(uid);
		
		dbMock.getSkuCollection().put(uid, document);
		
		return document;
		
	}

	private SkuDocument find(Long id, ItemResponseDTO item, Collection<TagResponseDTO> tags) throws SkuNotFoundException {
		
		SkuDocument loaded = find(item, tags);
		
		if(!loaded.getId().equals(id)){
			throw new SkuNotFoundException();
		}
		
		return loaded;
		
	}
	
	private SkuDocument find(ItemResponseDTO item, Collection<TagResponseDTO> tags) throws SkuNotFoundException {
		for( SkuDocument document : dbMock.getSkuCollection().values() ){
			Collection<TagDocument> collection = ConverterHelper.convert(tags, TagDocument.class);
			if(document.getItem().getId().equals(item.getId()) && collection.equals(document.getTags())){
				return document;
			}
		}
		throw new SkuNotFoundException();
	}
	
}
