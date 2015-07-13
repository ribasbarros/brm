package br.com.brm.scp.mock.api.service.strategies;

import java.util.Calendar;

import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.strategies.interfaces.SkuStrategy;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.db.SkuOperationDB;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;

public class SkuAtivandoStrategyImpl implements SkuStrategy {

	private SkuOperationDB db;

	public SkuAtivandoStrategyImpl(SkuOperationDB db) {
		super();
		this.db = db;
	}

	@Override
	public SkuResponseDTO save(SkuRequestDTO request) throws SkuNotFoundException {
		Assert.notNull(request.getId(), "sku.idinvalido");
		Assert.notNull(request.getItem(), "sku.itemnaopreenchido");
		Assert.notEmpty(request.getTags(), "sku.tagnaopreenchida");

		try {
			db.hasSku(request);
		} catch (SkuExistenteException e) {
		} catch (SkuNotFoundException e) {
			throw new SkuNotFoundException();
		}

		SkuDocument document = (SkuDocument) ConverterHelper.convert(request, SkuDocument.class);
		document.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		document.setDataAlteracao(Calendar.getInstance());

		document = db.update(document);

		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
	}

}
