package br.com.jope.financeiro;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.jope.financeiro.enums.EnumCategoria;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerMockMvcTest extends AbstractTest {
	
	private static final String URL = "/categorias";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void validaRetornoCategoria() throws Exception {
		this.mockMvc
			.perform(get(URL).contentType(contentType))			
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].descricao", containsString(EnumCategoria.ALIMENTACAO.getDescricao())));
	}
	
	@Test
	void validaCategoriaPorCodigo() throws Exception {
		this.mockMvc
			.perform(get(URL + "/" + EnumCategoria.ALIMENTACAO.getCodigo()).contentType(contentType))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.descricao", containsString(EnumCategoria.ALIMENTACAO.getDescricao())));
	}
	
}
