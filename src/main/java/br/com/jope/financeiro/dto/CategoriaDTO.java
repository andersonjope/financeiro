package br.com.jope.financeiro.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.jope.financeiro.model.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class CategoriaDTO {
	
	private Long idCategoria;
	private String descricao;
	private String mensagem;
	
	public CategoriaDTO() {}
	

	public CategoriaDTO(Categoria categoria) {
		this.idCategoria = categoria.getIdCategoria();
		this.descricao = categoria.getDescricao();
	}
	
	public CategoriaDTO(String descricao) {
		this.descricao = descricao;
		this.mensagem = "Categoria com essa descrição já foi cadastra.";
	}

	public static Page<CategoriaDTO> converte(Page<Categoria> findAll) {
		List<CategoriaDTO> list = findAll.stream().map(c -> new CategoriaDTO(c)).collect(Collectors.toList());
		
		return new PageImpl<>(list);
	}
	
	public static Optional<CategoriaDTO> converte(Optional<Categoria> optional) {
		if(optional.isPresent()) {
			return Optional.of(new CategoriaDTO(optional.get()));
		}
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setMensagem("Categoria não existente.");
		return Optional.of(categoriaDTO);
	}
	
	public boolean isMensagem() {
		return this.mensagem != null && !this.mensagem.isEmpty();
	}
	
}
