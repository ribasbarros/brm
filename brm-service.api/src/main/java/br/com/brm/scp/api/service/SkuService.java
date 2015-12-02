package br.com.brm.scp.api.service;

import java.util.Collection;
import java.util.Map;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotSuchMuchQuantityException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.status.SkuFiltroEnum;
import br.com.brm.scp.api.vo.PedidoVO;

public interface SkuService {

	SkuResponseDTO create(SkuRequestDTO request) throws SkuExistenteException;

	SkuResponseDTO update(SkuRequestDTO request) throws SkuNotFoundException;
	
	void delete(String id) throws SkuNotFoundException;

	SkuResponseDTO find(SkuFiltroEnum filtro, Object value)
			throws SkuNotFoundException;

	Pageable<SkuResponseDTO> all(int pageIndex, int size)
			throws SkuNotFoundException;

	Pageable<SkuResponseDTO> search(String searchTerm, int pageIndex, int size)
			throws SkuNotFoundException;

	Collection<SkuResponseDTO> all();

	Collection<SkuResponseDTO> chain(String idItem) throws SkuNotFoundException;

	void addEstoque(String id, Integer quantidade) throws SkuNotFoundException, SkuNotSuchMuchQuantityException;

	void estoqueSeguranca(String id, Double es) throws SkuNotFoundException;

	void estoqueMaximo(String id, Double em) throws SkuNotFoundException;

}
