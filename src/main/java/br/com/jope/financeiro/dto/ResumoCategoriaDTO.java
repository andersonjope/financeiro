package br.com.jope.financeiro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class ResumoCategoriaDTO {

	private String valor;
	private String descricaoCategoria;
	
}
