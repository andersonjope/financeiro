package br.com.jope.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
	
	public TokenDTO() {}
	
	private String token;
	private String tipo;
	
}
