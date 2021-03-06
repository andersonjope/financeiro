package br.com.jope.financeiro;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.jope.financeiro.dto.DespesaDTO;
import br.com.jope.financeiro.dto.TokenDTO;
import br.com.jope.financeiro.enums.EnumUsuario;
import br.com.jope.financeiro.form.DespesaForm;
import br.com.jope.financeiro.form.LoginForm;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DespesaControllerMockMvcTest extends AbstractTest {

	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final String DESCRICAO = "Despesa teste";
	private static final String URL = "/despesas";
	
	@Autowired
	private MockMvc mockMvc;
	
	private DespesaForm form;
	private Long idDespesa;
	
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
	@Order(0)
	void cadastrarDespesaValidaCamposObrigatorios() throws Exception {
		form = new DespesaForm("", "");

		String json = mapper.writeValueAsString(form);
		
		TokenDTO token = loadAutentificacao();
		
		this.mockMvc.perform(post(URL)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType)
				.content(json))
			.andExpect(status().isBadRequest())
			.andReturn()
		;
	}
	
	@Test
	@Order(1)
	void cadastrarDespesaDadosValidos() throws Exception {
		form = new DespesaForm(DESCRICAO, "1000");
		
		String json = mapper.writeValueAsString(form);
		
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(post(URL)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated())
		.andReturn();
		
		String response = result.getResponse().getContentAsString();
		DespesaDTO receitaDTO = mapper.readValue(response, DespesaDTO.class);
		this.idDespesa = receitaDTO.getIdDespesa();
	}
	
	@Test
	@Order(2)
	void listaDespesasCadastradas() throws Exception {
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
		.andExpect(status().isOk())
		.andReturn();
		
		String json = result.getResponse().getContentAsString();
		
		Assert.isTrue(json.contains("content"), "Retorno da lista n??o consta dados!");
	}
	
	@Test
	@Order(3)
	void exibeDespesasCadastrada() throws Exception {
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL + "/" + this.idDespesa)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		DespesaDTO receitaDTO = mapper.readValue(response, DespesaDTO.class);
		
		Assert.isTrue(receitaDTO.getIdDespesa().equals(this.idDespesa), "Retorno n??o consta dados!");
	}
	
	@Test
	@Order(4)
	void atualizaDespesasCadastrada() throws Exception {
		TokenDTO token = loadAutentificacao();
		
		String descricaoAlterado = DESCRICAO + " Alterado";
		form = new DespesaForm(descricaoAlterado, "1000");
		String json = mapper.writeValueAsString(form);
		
		MvcResult result = this.mockMvc.perform(put(URL + "/" + this.idDespesa)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType)
				.content(json))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString(UTF_8);
		DespesaDTO receitaDTO = mapper.readValue(response, DespesaDTO.class);
		
		Assert.isTrue(receitaDTO.getDescricao().equals(descricaoAlterado), "Receita n??o alterado!");
	}
	
	@SuppressWarnings("static-access")
	@Test
	@Order(5)
	void consultaDespesasCadastradaPorDescricao() throws Exception {		
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL + "?descricao=" + this.DESCRICAO)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		
		String json = result.getResponse().getContentAsString(UTF_8);
		
		List<DespesaDTO> list = mapper.readValue(json, new TypeReference<List<DespesaDTO>>() {});
		Assert.isTrue(list.size() > 0, "Retorno da lista por descri????o n??o consta dados!");
	}
	
	@Test
	@Order(6)
	void consultaDespesasCadastradaPorAnoMes() throws Exception {	
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL + "/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonth().getValue())
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		
		String json = result.getResponse().getContentAsString(UTF_8);
		
		List<DespesaDTO> list = mapper.readValue(json, new TypeReference<List<DespesaDTO>>() {});
		Assert.isTrue(list.size() > 0, "Retorno da lista por ano e m??s n??o consta dados!");
	}
	
	@Test
	@Order(7)
	void removeDespesaCadastrada() throws Exception {
		TokenDTO token = loadAutentificacao();
		
		this.mockMvc.perform(delete(URL + "/" + this.idDespesa)
			.header("Authorization", token.getTipo() + " " + token.getToken())
			.contentType(contentType))
			.andExpect(status().isOk())
			.andReturn();		
	}

}
