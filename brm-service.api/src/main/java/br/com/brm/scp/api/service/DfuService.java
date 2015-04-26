package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.DfuRequestDTO;

public interface DfuService {

	DfuRequestDTO create(DfuRequestDTO dfuSuccess);

	Collection<DfuRequestDTO> all();

	DfuRequestDTO find(Long id);

	Boolean delete(Long id);

}
