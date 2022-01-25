package br.com.jope.financeiro.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.model.Despesa;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DespesaForm {

	@NotBlank(message = "Descrição da despesa obrigatória!")
	private String descricao;
	
	@NotNull(message = "Categoria da despesa obrigatória, ex: 'RECEITA, DESPESA'!")
	private Long classificacao;
	
	@NotBlank(message = "Valor da despesa obrigatória!")
	private String valor;
	
	public Despesa converte() {
		Despesa receita = new Despesa();
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

	public Despesa converte(Long id) {
		Despesa receita = new Despesa();
		receita.setIdDespesa(id);
		receita.setDescricao(descricao);
		receita.setValor(new BigDecimal(valor));
		receita.setCategoria(getCategoria());
		return receita;
	}

}
