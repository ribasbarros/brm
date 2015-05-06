package br.com.brm.scp.api.dto.request;

import java.util.Collection;

import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;

public class SkuRequestDTO {

	public ChaveComposta key;
	
	public Collection<String> pedidos;

	public void setItem(ItemResponseDTO itemResponseDTO) {
		// TODO Auto-generated method stub
		
	}

	public Collection<TagResponseDTO> getTags() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
