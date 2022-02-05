package br.com.jope.financeiro.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import br.com.jope.financeiro.model.Receita;
import br.com.jope.financeiro.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReceitaForm {

	@NotBlank(message = "Descrição da receita obrigatória!")
	private String descricao;
	
	@NotBlank(message = "Valor da receita obrigatória!")
	private String valor;
	
	public ReceitaForm() {}
	
	public ReceitaForm(String descricao, String valor) {
		this.descricao = descricao;
		this.valor = valor;
	}

	public Receita converte(Usuario usuario) {
		Receita receita = new Receita();
		receita.setDescricao(descricao);
		receita.setValor(new BigDecimal(valor));
		receita.setUsuario(usuario);
		return receita;
	}

	public Receita converte(Long id) {
		Receita receita = new Receita();
		receita.setIdReceita(id);
		receita.setDescricao(descricao);
		receita.setValor(new BigDecimal(valor));
		return receita;
	}

}
