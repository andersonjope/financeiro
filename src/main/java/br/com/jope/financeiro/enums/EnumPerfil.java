package br.com.jope.financeiro.enums;

import lombok.Getter;

@Getter
public enum EnumPerfil {
	
	ADMIN(1l, "ADMIN"),
	USER(2l, "USER");
	
	private final Long codigo;
	private final String descricao;
	
	private EnumPerfil(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

}
