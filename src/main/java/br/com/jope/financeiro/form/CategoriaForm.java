package br.com.jope.financeiro.form;

import br.com.jope.financeiro.model.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoriaForm {

	private String descricao;
	
	public Categoria converte() {
		Categoria categoria = new Categoria();
		categoria.setDescricao(descricao);
		return categoria;
	}

}
