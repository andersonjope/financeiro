package br.com.jope.financeiro.form;

import javax.validation.constraints.NotBlank;

import br.com.jope.financeiro.enums.EnumClassificacaoCategoria;
import br.com.jope.financeiro.model.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoriaForm {

	@NotBlank(message = "Descrição da categoria obrigatória!")
	private String descricao;
	
	@NotBlank(message = "Classificação da categoria obrigatória, ex: 'RECEITA, DESPESA'!")
	private String classificacao;
	
	public Categoria converte() {
		Categoria categoria = new Categoria();
		categoria.setDescricao(descricao);
		categoria.setClassificacaoCategoria(EnumClassificacaoCategoria.valueOf(classificacao));
		return categoria;
	}

	public Categoria converte(Long id) {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(id);
		categoria.setDescricao(descricao);
		categoria.setClassificacaoCategoria(EnumClassificacaoCategoria.valueOf(classificacao));
		return categoria;
	}

}
