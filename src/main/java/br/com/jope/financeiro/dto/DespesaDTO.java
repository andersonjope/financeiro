package br.com.jope.financeiro.dto;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.jope.financeiro.model.Despesa;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class DespesaDTO {
	
	private Long idDespesa;
	private String descricao;
	private String valor;
	private String categoria;
	private String dataCadastro;
	private String mensagem;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public DespesaDTO() {}
	
	public DespesaDTO(Despesa despesa) {
		this.idDespesa = despesa.getIdDespesa();
		this.descricao = despesa.getDescricao();
		this.valor = String.valueOf(despesa.getValor());
		this.dataCadastro = despesa.getDataCadastro().format(formatter);
		this.categoria = despesa.getCategoria().getDescricao();
	}
	
	public DespesaDTO(String descricao) {
		this.descricao = descricao;
		this.mensagem = "Despesa já foi cadastra.";
	}

	public static Page<DespesaDTO> converte(Page<Despesa> findAll) {
		return findAll.map(new Function<Despesa, DespesaDTO>() {

			@Override
			public DespesaDTO apply(Despesa receita) {
				return new DespesaDTO(receita);
			}
		});
	}
	
	public static Optional<DespesaDTO> converte(Optional<Despesa> optional) {
		if(optional.isPresent()) {
			return Optional.of(new DespesaDTO(optional.get()));
		}
		DespesaDTO categoriaDTO = new DespesaDTO();
		categoriaDTO.setMensagem("Despesa não existente.");
		return Optional.of(categoriaDTO);
	}
	
	public boolean isMensagem() {
		return this.mensagem != null && !this.mensagem.isEmpty();
	}
	
}
