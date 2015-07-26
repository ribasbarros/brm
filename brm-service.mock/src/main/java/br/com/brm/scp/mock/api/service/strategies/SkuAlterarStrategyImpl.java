package br.com.brm.scp.mock.api.service.strategies;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.strategies.interfaces.SkuStrategy;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.db.SkuOperationDB;
import br.com.brm.scp.mock.api.service.document.SkuDocument;

public class SkuAlterarStrategyImpl implements SkuStrategy {

	private SkuOperationDB db;

	public SkuAlterarStrategyImpl(SkuOperationDB db) {
		super();
		this.db = db;
	}

	@Override
	public SkuResponseDTO save(SkuRequestDTO request) throws SkuException {
		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		
		document = db.update(document);
		
		if( document == null ){
			throw new SkuNotFoundException();
		}
		
		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
	}

	@Override
	public SkuResponseDTO save(SkuRequestDTO request, Long... idUsuarioLogado) throws SkuException {
		throw new IllegalArgumentException("Metodo invalido para a estrategia");
	}

}
