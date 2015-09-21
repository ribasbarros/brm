package br.com.brm.scp.api.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;

/**
 * @author Ribas
 *
 */
@Service
public class SkuServiceImpl implements SkuService {

	private static Logger logger = Logger.getLogger(SkuServiceImpl.class);

	private static final String SKU_NOTNULL = "sku.notnull";
	private static final String SKU_ITEM = "sku.item";
	private static final String SKU_TAG = "sku.tag";
	private static final String SKU_DESCRICAO = "sku.descricao";
	private static final String SKU_FREQUENCIA_ANALISE = "sku.frequenciaanalise";
	private static final String SKU_MODELO = "sku.modelo";
	private static final String SKU_ORIGEM = "sku.origem";

	private static final String SKU_EXISTENTE = "sku.existente";
	
	@Autowired
	private SkuRepository repository;

	@Override
	public SkuResponseDTO create(SkuRequestDTO request) throws SkuExistenteException {
		
		Assert.notNull(request, SKU_NOTNULL);
		Assert.notNull(request.getItem(), SKU_ITEM);
		Assert.notNull(request.getTags(), SKU_TAG);
		Assert.notNull(request.getDescricao(), SKU_DESCRICAO);
		Assert.notNull(request.getFrequenciaAnalise(), SKU_FREQUENCIA_ANALISE);
		Assert.notNull(request.getModelo(), SKU_MODELO);
		Assert.notNull(request.getOrigens(), SKU_ORIGEM);
		
		hasSkuRegistered(request);
		
		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		
		document = repository.save(document);
		
		SkuResponseDTO response = invokeResponse(document);
		
		return response;
		
	}

	private void hasSkuRegistered(SkuRequestDTO request) throws SkuExistenteException {
		ObjectId[] tags = request.toTagArray();
		String id = request.getItem().getId();
		
		SkuDocument document = repository.findSku(id, tags, tags.length);
		
		if(document != null){
			throw new SkuExistenteException(SKU_EXISTENTE);
		}
	}

	private SkuResponseDTO invokeResponse(SkuDocument document) {
		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
	}

	@Override
	public Collection<SkuResponseDTO> search(Object value) throws SkuNotFoundException {
		
		logger.info(String.format("Buscando %s na base", value));
		
		Collection<SkuDocument> document = repository.search(value);
		
		if(document == null)
			throw new SkuNotFoundException(SKU_NOTNULL);
		
		return invokeResponse(document);
		
	}

	private Collection<SkuResponseDTO> invokeResponse(Collection<SkuDocument> collection) {
		return ConverterHelper.convert(collection, SkuResponseDTO.class);
	}

}
