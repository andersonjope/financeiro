package br.com.jope.financeiro.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.model.Receita;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReceitaForm {

	@NotBlank(message = "Descrição da receita obrigatória!")
	private String descricao;
	
	private Long classificacao;
	
	@NotBlank(message = "Valor da receita obrigatória!")
	private String valor;
	
	public Receita converte() {
		Receita receita = new Receita();
		receita.setDescricao(descricao);
		receita.setValor(new BigDecimal(valor));
		receita.setCategoria(getCategoria());
		return receita;
	}

	private Categoria getCategoria() {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(classificacao);
		return categoria;
	}

	public Receita converte(Long id) {
		Receita receita = new Receita();
		receita.setIdReceita(id);
		receita.setDescricao(descricao);
		receita.setValor(new BigDecimal(valor));
		receita.setCategoria(getCategoria());
		return receita;
	}

}
