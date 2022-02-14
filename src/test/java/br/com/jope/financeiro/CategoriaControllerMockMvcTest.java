package br.com.jope.financeiro;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.jope.financeiro.dto.TokenDTO;
import br.com.jope.financeiro.enums.EnumCategoria;
import br.com.jope.financeiro.enums.EnumUsuario;
import br.com.jope.financeiro.form.LoginForm;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerMockMvcTest extends AbstractTest {
	
	private static final String URL = "/categorias";
	
	@Autowired
	private MockMvc mockMvc;
	
	private synchronized TokenDTO loadAutentificacao() throws Exception {
		LoginForm loginForm = new LoginForm(EnumUsuario.USUARIO_ADMIN.getLogin(), "123456");
		
		String json = mapper.writeValueAsString(loginForm);
		
		MvcResult result = this.mockMvc
			.perform(post(ENDPOINT_AUTH)
				.contentType(contentType)
				.content(json))
			.andExpect(status().isOk())
			.andReturn();
				
		String dadosLogin = result.getResponse().getContentAsString();
		TokenDTO token = mapper.readValue(dadosLogin, TokenDTO.class);
		return token;
	}
	
	@Test
	void validaRetornoCategoria() throws Exception {
		TokenDTO token = loadAutentificacao();
		this.mockMvc
			.perform(
					get(URL)
					.header("Authorization", token.getTipo() + " " + token.getToken())
					.contentType(contentType))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].descricao", containsString(EnumCategoria.ALIMENTACAO.getDescricao())));
	}
	
	@Test
	void validaCategoriaPorCodigo() throws Exception {
		TokenDTO token = loadAutentificacao();
		this.mockMvc
			.perform(get(URL + "/" + EnumCategoria.ALIMENTACAO.getCodigo()).contentType(contentType).header("Authorization", token.getTipo() + " " + token.getToken()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.descricao", containsString(EnumCategoria.ALIMENTACAO.getDescricao())));
	}
	
}
