package br.com.brm.scp.api.service;

import br.com.brm.scp.api.dto.request.DfuRequestDTO;
import br.com.brm.scp.api.dto.response.DfuResponseDTO;
import br.com.brm.scp.api.exceptions.DfuExistenteException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.mock.api.service.status.DfuFiltroEnum;


public interface DfuService {
	DfuResponseDTO create(DfuRequestDTO request) throws DfuExistenteException, DfuNotFoundException;

	void delete(String id) throws DfuNotFoundException;

	void update(DfuRequestDTO request) throws DfuNotFoundException;

	DfuResponseDTO find(DfuFiltroEnum filtro, Object value) throws DfuNotFoundException;

}
