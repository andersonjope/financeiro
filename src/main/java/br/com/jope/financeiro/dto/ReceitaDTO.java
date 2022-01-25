package br.com.jope.financeiro.dto;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.jope.financeiro.model.Receita;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class ReceitaDTO {
	
	private Long idReceita;
	private String descricao;
	private String valor;
	private String dataCadastro;
	private String mensagem;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public ReceitaDTO() {}
	
	public ReceitaDTO(Receita receita) {
		this.idReceita = receita.getIdReceita();
		this.descricao = receita.getDescricao();
		this.valor = String.valueOf(receita.getValor());
		this.dataCadastro = receita.getDataCadastro().format(formatter);
	}
	
	public ReceitaDTO(String descricao) {
		this.descricao = descricao;
		this.mensagem = "Receita já foi cadastra.";
	}

	public static Page<ReceitaDTO> converte(Page<Receita> findAll) {
		return findAll.map(new Function<Receita, ReceitaDTO>() {

			@Override
			public ReceitaDTO apply(Receita receita) {
				return new ReceitaDTO(receita);
			}
		});
	}
	
	public static Optional<ReceitaDTO> converte(Optional<Receita> optional) {
		if(optional.isPresent()) {
			return Optional.of(new ReceitaDTO(optional.get()));
		}
		ReceitaDTO categoriaDTO = new ReceitaDTO();
		categoriaDTO.setMensagem("Receita não existente.");
		return Optional.of(categoriaDTO);
	}
	
	public boolean isMensagem() {
		return this.mensagem != null && !this.mensagem.isEmpty();
	}
	
}
