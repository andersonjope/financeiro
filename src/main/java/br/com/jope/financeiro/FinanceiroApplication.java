package br.com.jope.financeiro;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.jope.financeiro.enums.EnumCategoria;
import br.com.jope.financeiro.enums.EnumUsuario;
import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.model.Usuario;
import br.com.jope.financeiro.service.CategoriaService;
import br.com.jope.financeiro.service.UsuarioService;

@SpringBootApplication
public class FinanceiroApplication implements ApplicationRunner {

	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private UsuarioService usuarioService;
	
	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Arrays.asList(EnumCategoria.values()).forEach(ec -> salvar(ec));
		Arrays.asList(EnumUsuario.values()).forEach(eu -> salvar(eu));
	}

	private void salvar(EnumUsuario enumUsuario) {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(enumUsuario.getCodigo());
		usuario.setNome(enumUsuario.getNome());
		usuario.setLogin(enumUsuario.getLogin());
		usuario.setSenha(enumUsuario.getSenha());
		usuario.setPerfil(enumUsuario.getPerfil());
		usuarioService.salvar(usuario);
	}

	private void salvar(EnumCategoria enumCategoria) {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(enumCategoria.getCodigo());
		categoria.setDescricao(enumCategoria.getDescricao());
		categoriaService.salvar(categoria);
	}

}
