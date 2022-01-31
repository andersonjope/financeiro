package br.com.jope.financeiro.enums;

import lombok.Getter;

@Getter
public enum EnumCategoria {

	ALIMENTACAO(1L,"Alimentação"),
	SAUDE(2L,"Saúde"),
	MORADIA(3L,"Moradia"),
	TRANPORTE(4L,"Transporte"),
	EDUCACAO(5L,"Educação"),
	LAZER(6L,"Lazer"),
	IMPREVISTOS(7L,"Imprevistos"),
	OUTRAS(8L,"Outras");
	
	private final Long codigo;
	private final String descricao;
	
	private EnumCategoria(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static EnumCategoria getCategoria(Long codigo) {
		for(EnumCategoria enumCategoria : values()) {
			if(enumCategoria.getCodigo().equals(codigo)) {
				return enumCategoria;
			}
		}
		return null;
	}
}
