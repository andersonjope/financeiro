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
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jope.financeiro.dto.ReceitaDTO;
import br.com.jope.financeiro.dto.TokenDTO;
import br.com.jope.financeiro.enums.EnumUsuario;
import br.com.jope.financeiro.form.LoginForm;
import br.com.jope.financeiro.form.ReceitaForm;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReceitaControllerMockMvcTest extends AbstractTest {

	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final String DESCRICAO = "Salário teste";
	private static final String URL = "/receitas";
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	private ReceitaForm form;
	private Long idReceita;
	
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
	void cadastrarReceitaValidaCamposObrigatorios() throws Exception {
		form = new ReceitaForm("", "");

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
	void cadastrarReceitaDadosValidos() throws Exception {
		form = new ReceitaForm(DESCRICAO, "1000");
		
		String json = mapper.writeValueAsString(form);
		
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(post(URL)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated())
		.andReturn();
		
		String response = result.getResponse().getContentAsString();
		ReceitaDTO receitaDTO = mapper.readValue(response, ReceitaDTO.class);
		this.idReceita = receitaDTO.getIdReceita();
	}
	
	@Test
	@Order(2)
	void cadastrarReceitaValidaReceitaCadastrada() throws Exception {
		form = new ReceitaForm(DESCRICAO, "1000");
		String json = mapper.writeValueAsString(form);
		
		TokenDTO token = loadAutentificacao();
		
		this.mockMvc.perform(post(URL)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType)
				.content(json))
		.andExpect(status().isNotFound());		
	}
	
	@Test
	@Order(3)
	void listaReceitasCadastradas() throws Exception {
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
		.andExpect(status().isOk())
		.andReturn();
		
		String json = result.getResponse().getContentAsString();
		
		Assert.isTrue(json.contains("content"), "Retorno da lista não consta dados!");
	}
	
	@Test
	@Order(4)
	void exibeReceitasCadastrada() throws Exception {
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL + "/" + this.idReceita)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		ReceitaDTO receitaDTO = mapper.readValue(response, ReceitaDTO.class);
		
		Assert.isTrue(receitaDTO.getIdReceita().equals(this.idReceita), "Retorno não consta dados!");
	}
	
	@Test
	@Order(5)
	void atualizaReceitasCadastrada() throws Exception {
		String descricaoAlterado = DESCRICAO + " Alterado";
		form = new ReceitaForm(descricaoAlterado, "1000");
		String json = mapper.writeValueAsString(form);
		
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(put(URL + "/" + this.idReceita)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType)
				.content(json))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString(UTF_8);
		ReceitaDTO receitaDTO = mapper.readValue(response, ReceitaDTO.class);
		
		Assert.isTrue(receitaDTO.getDescricao().equals(descricaoAlterado), "Receita não alterado!");
	}
	
	@SuppressWarnings("static-access")
	@Test
	@Order(6)
	void consultaReceitasCadastradaPorDescricao() throws Exception {	
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL + "?descricao=" + this.DESCRICAO)
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		
		String json = result.getResponse().getContentAsString(UTF_8);
		
		List<ReceitaDTO> list = mapper.readValue(json, new TypeReference<List<ReceitaDTO>>() {});
		Assert.isTrue(list.size() > 0, "Retorno da lista por descrição não consta dados!");
	}
	
	@Test
	@Order(7)
	void consultaReceitasCadastradaPorAnoMes() throws Exception {	
		TokenDTO token = loadAutentificacao();
		
		MvcResult result = this.mockMvc.perform(get(URL + "/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonth().getValue())
				.header("Authorization", token.getTipo() + " " + token.getToken())
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		
		String json = result.getResponse().getContentAsString(UTF_8);
		
		List<ReceitaDTO> list = mapper.readValue(json, new TypeReference<List<ReceitaDTO>>() {});
		Assert.isTrue(list.size() > 0, "Retorno da lista por ano e mês não consta dados!");
	}
	
	@Test
	@Order(8)
	void removeReceitaCadastrada() throws Exception {
		TokenDTO token = loadAutentificacao();
		
		this.mockMvc.perform(delete(URL + "/" + this.idReceita)
			.header("Authorization", token.getTipo() + " " + token.getToken())
			.contentType(contentType))
			.andExpect(status().isOk())
			.andReturn();		
	}

}
