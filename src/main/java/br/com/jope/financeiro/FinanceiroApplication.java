package br.com.jope.financeiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.jope.financeiro.enums.EnumCategoria;
import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.service.CategoriaService;

@SpringBootApplication
public class FinanceiroApplication implements ApplicationRunner {

	@Autowired
	private CategoriaService categoriaService;
	
	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for(EnumCategoria enumCategoria : EnumCategoria.values()) {
			Categoria categoria = new Categoria();
			categoria.setIdCategoria(enumCategoria.getCodigo());
			categoria.setDescricao(enumCategoria.getDescricao());
			categoriaService.salvar(categoria);
		}
	}

}
