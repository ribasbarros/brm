package br.com.brm.scp.mock.api.service.impl;

import java.util.Date;
import java.util.Map.Entry;

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.objects.RandomHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.FornecedorDocument;

public class FornecedorServiceMockImpl implements FornecedorService{

	private MockData mockdb;
	
	public FornecedorServiceMockImpl(MockData mockdb) {
		super();
		this.mockdb = mockdb;
	}

	@Override
	public FornecedorResponseDTO create(FornecedorRequestDTO request) throws FornecedorExistenteException {
		if(findByCnpj(request.getCnpj()) != null){
			throw new FornecedorExistenteException("brm.fornecedor.existente");
		}
		FornecedorDocument document = (FornecedorDocument) ConverterHelper.convert(request, FornecedorDocument.class);
		document.setId(RandomHelper.UUID());
		mockdb.getFornecedorCollection().put(document.getId(), document);
		return (FornecedorResponseDTO) ConverterHelper.convert(document, FornecedorResponseDTO.class);
	}
	
	
	@Override
	public FornecedorResponseDTO findByCnpj(String cnpj) {
		for(FornecedorDocument fornecedor : mockdb.getFornecedorCollection().values()){
			if(fornecedor.getCnpj().equals(cnpj)){
				return (FornecedorResponseDTO) ConverterHelper.convert(fornecedor, FornecedorResponseDTO.class);
			}
		}
		return null;
	}

	@Override
	public void delete(FornecedorRequestDTO request) {
		FornecedorResponseDTO response = findByCnpj(request.getCnpj());
		FornecedorDocument document = (FornecedorDocument) ConverterHelper.convert(response, FornecedorDocument.class);
		document.setDataExcluido(new Date());
	}

	@Override
	public FornecedorResponseDTO update(FornecedorRequestDTO request) {
		FornecedorDocument document = (FornecedorDocument) ConverterHelper.convert(request, FornecedorDocument.class);
		for(Entry<Long,FornecedorDocument> entry : mockdb.getFornecedorCollection().entrySet()){
			if(document.equals(entry.getKey())){
				return (FornecedorResponseDTO) ConverterHelper.convert(entry.setValue(document),FornecedorResponseDTO.class);
			}			
		}
		return null;
	}

	@Override
	public FornecedorResponseDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
