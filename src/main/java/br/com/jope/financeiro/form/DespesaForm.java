package br.com.jope.financeiro.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.model.Despesa;
import br.com.jope.financeiro.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DespesaForm {

	@NotBlank(message = "Descrição da despesa obrigatória!")
	private String descricao;
	
	private Long categoria;
	
	@NotBlank(message = "Valor da despesa obrigatória!")
	private String valor;
	
	public DespesaForm() {}
	
	public DespesaForm(String descricao, String valor) {
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public DespesaForm(String descricao, String valor, Long categoria) {
		this.descricao = descricao;
		this.valor = valor;
		this.categoria = categoria;
	}

	public Despesa converte(Usuario usuario) {
		Despesa despesa = new Despesa();
		despesa.setDescricao(descricao);
		despesa.setValor(new BigDecimal(valor));
		despesa.setCategoria(getCategoria());
		despesa.setUsuario(usuario);
		return despesa;
	}

	private Categoria getCategoria() {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(this.categoria);
		return categoria;
	}

	public Despesa converte(Long id) {
		Despesa despesa = new Despesa();
		despesa.setIdDespesa(id);
		despesa.setDescricao(descricao);
		despesa.setValor(new BigDecimal(valor));
		despesa.setCategoria(getCategoria());
		return despesa;
	}

}
