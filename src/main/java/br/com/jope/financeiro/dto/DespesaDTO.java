package br.com.jope.financeiro.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
	private String nomeUsuario;
	private String mensagem;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public DespesaDTO() {}
	
	public DespesaDTO(Despesa despesa) {
		this.idDespesa = despesa.getIdDespesa();
		this.descricao = despesa.getDescricao();
		this.valor = String.valueOf(despesa.getValor());
		this.dataCadastro = despesa.getDataCadastro().format(formatter);
		this.categoria = despesa.getCategoria().getDescricao();
		this.nomeUsuario = despesa.getUsuario().getNome();
	}
	
	public DespesaDTO(String descricao) {
		this.descricao = descricao;
		this.mensagem = "Despesa já foi cadastra.";
	}

	public static Page<DespesaDTO> converte(Page<Despesa> findAll) {
		List<DespesaDTO> list = findAll.stream().map(DespesaDTO::new).collect(Collectors.toList());
		
		return new PageImpl<>(list);
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
