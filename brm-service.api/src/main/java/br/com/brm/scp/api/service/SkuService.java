package br.com.brm.scp.api.service;

import java.util.Collection;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;

public interface SkuService {

	SkuResponseDTO create(SkuRequestDTO request) throws SkuExistenteException;

	SkuResponseDTO ativar(SkuRequestDTO request) throws SkuException;

	Collection<SkuResponseDTO> findForOrigin(Long id) throws SkuNotFoundException;

	SkuResponseDTO alterar(SkuRequestDTO request) throws SkuException;

	SkuResponseDTO find(Long id) throws SkuNotFoundException;

	void reabastecimento(SkuRequestDTO request, UsuarioResponseDTO usuarioLogado) throws SkuException;

}
