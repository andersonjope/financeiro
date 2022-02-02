package br.com.jope.financeiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false, of = "idUsuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(unique = true, nullable = false)
	private String login;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private String perfil;

}
