package br.com.jope.financeiro.enums;

import lombok.Getter;

@Getter
public enum EnumUsuario {

	USUARIO_ADMIN(1L, "Administrador", "usuario.admin", "123456", EnumPerfil.ADMIN.getDescricao()),
	USUARIO_USER(2L, "User", "usuario.user", "123456", EnumPerfil.USER.getDescricao());
	
	private final Long codigo;
	private final String nome;
	private final String login;
	private final String senha;
	private final String perfil;
	
	private EnumUsuario(Long codigo, String nome, String login, String senha, String perfil) {
		this.codigo = codigo;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.perfil = perfil;
	}
	
}
