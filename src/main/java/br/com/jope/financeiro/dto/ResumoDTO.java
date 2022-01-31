package br.com.jope.financeiro.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class ResumoDTO {

	private String valorTotalReceitas;
	private String valorTotalDespesas;
	private String saldo;
	private List<ResumoCategoriaDTO> resumoCategoriaList;
	
	public void calculoSaldo() {
		this.saldo = new BigDecimal(valorTotalReceitas).subtract(new BigDecimal(valorTotalDespesas)).toString();
	}
	
}
