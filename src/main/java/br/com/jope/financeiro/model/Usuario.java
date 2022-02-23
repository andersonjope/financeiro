package br.com.jope.financeiro.model;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 7755686008377203785L;

	@Id
	@Column(name = "ID_USUARIO")
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(unique = true, nullable = false)
	private String login;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private String perfil;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Stream.of(new Perfil(this.perfil)).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Getter
	class Perfil implements GrantedAuthority {

		private static final long serialVersionUID = -3776022473453839508L;
		
		private String nome;
		
		public Perfil(String nome) {
			super();
			this.nome = nome;
		}
		
		@Override
		public String getAuthority() {
			return this.nome;
		}
	}

}
