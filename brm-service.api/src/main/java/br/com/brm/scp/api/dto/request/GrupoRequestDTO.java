package br.com.brm.scp.api.dto.request;

import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;

public class GrupoRequestDTO {
private static final long serialVersionUID = -2946370998935414082L;
	
	private Long id;
	private String nome;
	@BindingClassMeta("PERFIS")
	private Collection<PerfilResponseDTO> perfis;
	private Date dataExcluido;
	
	public GrupoRequestDTO(Long id, String nome,
			Collection<PerfilResponseDTO> perfis) {
		super();
		this.id = id;
		this.nome = nome;
		this.perfis = perfis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<PerfilResponseDTO> getPerfis() {
		return perfis;
	}

	public void setPerfis(Collection<PerfilResponseDTO> perfis) {
		this.perfis = perfis;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}
}
