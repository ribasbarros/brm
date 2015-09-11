package br.com.brm.scp.mock.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.strategies.interfaces.Sku;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.db.SkuOperationDB;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.strategies.SkuAlterarStrategyImpl;
import br.com.brm.scp.mock.api.service.strategies.SkuAtivandoStrategyImpl;
import br.com.brm.scp.mock.api.service.strategies.SkuCreateStrategyImpl;
import br.com.brm.scp.mock.api.service.strategies.SkuReabastecimentoStrategyImpl;

public class SkuServiceMockImpl implements SkuService {

	@Override
	public SkuResponseDTO create(SkuRequestDTO request) throws SkuExistenteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuResponseDTO ativar(SkuRequestDTO request) throws SkuException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SkuResponseDTO> findForOrigin(Long id) throws SkuNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuResponseDTO alterar(SkuRequestDTO request) throws SkuException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SkuResponseDTO find(Long id) throws SkuNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reabastecimento(SkuRequestDTO request, UsuarioResponseDTO usuarioLogado) throws SkuException {
		// TODO Auto-generated method stub
		
	}

	/*@Autowired
	private SkuOperationDB db;
	
	private Logger logger = Logger.getLogger(SkuServiceMockImpl.class);

	@Override
	public SkuResponseDTO create(SkuRequestDTO request) throws SkuException {
		logger.info(String.format("Criando SKU para o item %s", request.getItem().getNome()));
		return new Sku(new SkuCreateStrategyImpl(db)).save(request);
	}

	@Override
	public SkuResponseDTO ativar(SkuRequestDTO request) throws SkuException {
		logger.info(String.format("Ativando SKU do item %s", request.getItem().getNome()));
		return new Sku(new SkuAtivandoStrategyImpl(db)).save(request);
	}

	@Override
	public Collection<SkuResponseDTO> findForOrigin(Long id) throws SkuNotFoundException {
		
		SkuDocument findOne = db.findOne(id);
		
		Collection<SkuDocument> all = db.findByItem(findOne.getItem());
		Collection<SkuDocument> remove = new ArrayList<SkuDocument>();
		
		for(SkuDocument doc : all){
			if(doc.getId().equals(id)){
				remove.add(doc);
			}
		}
		
		all.removeAll(remove);
		
		return ConverterHelper.convert(all, SkuResponseDTO.class);
		
	}

	@Override
	public SkuResponseDTO alterar(SkuRequestDTO request) throws SkuException {
		logger.info(String.format("Alterando uma SKU para o item %s", request.getItem().getNome()));
		return new Sku(new SkuAlterarStrategyImpl(db)).save(request);
	}

	@Override
	public SkuResponseDTO find(Long id) throws SkuNotFoundException {
		logger.info(String.format("Procurando SKU por id = %s", id));
		SkuDocument document = db.findOne(id);
		return (SkuResponseDTO) ConverterHelper.convert(document, SkuResponseDTO.class);
	}

	@Override
	public void reabastecimento(SkuRequestDTO request, UsuarioResponseDTO usuarioLogado) throws SkuException {
		
		Assert.notNull(request, "brm.sku.notnull");
		Assert.notNull(request.getId(), "brm.sku.id");
		
		logger.info(String.format("Reabastecimento manual de SKU %s", request.getId()));
		
		new Sku(new SkuReabastecimentoStrategyImpl(db)).save(request, usuarioLogado.getId());
		
	}*/

}
