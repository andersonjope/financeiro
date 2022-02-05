package br.com.jope.financeiro.enums;

import lombok.Getter;

@Getter
public enum EnumUsuario {

	USUARIO_ADMIN(1L, "Administrador", "usuario.admin", "$2a$10$IsmFXAOsNe4Tprh0Wuu/qebuAFgSrTsOH2PFnnCUCv6iSzuKDqc5S", EnumPerfil.ROLE_ADMIN.getDescricao()),
	USUARIO_USER(2L, "User", "usuario.user", "$2a$10$IsmFXAOsNe4Tprh0Wuu/qebuAFgSrTsOH2PFnnCUCv6iSzuKDqc5S", EnumPerfil.ROLE_USER.getDescricao());
	
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
