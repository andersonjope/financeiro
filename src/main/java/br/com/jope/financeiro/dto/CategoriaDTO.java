package br.com.jope.financeiro.dto;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;

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
		return findAll.map(new Function<Categoria, CategoriaDTO>() {

			@Override
			public CategoriaDTO apply(Categoria categoria) {
				return new CategoriaDTO(categoria);
			}
		});
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
