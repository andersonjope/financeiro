package br.com.jope.financeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jope.financeiro.enums.EnumCategoria;
import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.service.CategoriaRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FinanceiroApplicationTests {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	private List<Categoria> categoriaList;
	
	@BeforeEach
	void carregaLista() {
		categoriaList = categoriaRepository.findAll();		
	}
	
	@Test
	@Order(0)
	void validaTodasCategoriasForamCadastradas() {
		int length = EnumCategoria.values().length;
		assertEquals(length, categoriaList.size());
	}
	
	@Test
	@Order(1)
	void validaDadosCategoriaEstaoCorretas() {
		categoriaList.forEach(new Consumer<Categoria>() {
			public void accept(Categoria categoria) {
				for(EnumCategoria enumCategoria : EnumCategoria.values()) {
					if(enumCategoria.getCodigo().equals(categoria.getIdCategoria())) {
						assertEquals(enumCategoria.getDescricao(), categoria.getDescricao());										
					}
				}
			}
		});
	}
	
}
