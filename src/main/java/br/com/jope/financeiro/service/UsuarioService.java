package br.com.jope.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jope.financeiro.model.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void salvar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
}
