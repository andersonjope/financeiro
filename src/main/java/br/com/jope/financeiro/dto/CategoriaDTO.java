package br.com.jope.financeiro.dto;

import br.com.jope.financeiro.model.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoriaDTO {
	
	private Long id;
	private String descricao;

	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getIdCategoria();
		this.descricao = categoria.getDescricao();
	}
	
}
