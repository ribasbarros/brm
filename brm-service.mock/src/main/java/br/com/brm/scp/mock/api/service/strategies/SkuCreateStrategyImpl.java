package br.com.brm.scp.mock.api.service.strategies;

import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.strategies.interfaces.SkuStrategy;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.db.SkuOperationDB;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;

public class SkuCreateStrategyImpl implements SkuStrategy {

	private SkuOperationDB db;
	
	public SkuCreateStrategyImpl(SkuOperationDB db) {
		super();
		this.db = db;
	}

	@Override
	public SkuResponseDTO save(SkuRequestDTO request) throws SkuExistenteException {
		Assert.notNull(request.getItem(), "sku.itemnaopreenchido");
		Assert.notEmpty(request.getTags(), "sku.tagnaopreenchida");

		try {
			db.hasSku(request);
		} catch (SkuNotFoundException e) {
		} catch (SkuExistenteException e) {
			throw new SkuExistenteException();
		}

		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		document.setStatus(StatusReposicaoEnum.RASCUNHO);
		document = db.insert(document);

		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
	}

	@Override
	public SkuResponseDTO save(SkuRequestDTO request, Long... idUsuarioLogado) throws SkuException {
		throw new IllegalArgumentException("Metodo invalido para a estrategia");
	}


}
