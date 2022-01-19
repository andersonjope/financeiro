package br.com.jope.financeiro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.jope.financeiro.dto.CategoriaDTO;
import br.com.jope.financeiro.model.Categoria;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public CategoriaDTO salvar(Categoria categoria) {
		if(repository.existsByDescricao(categoria.getDescricao())) {
			return new CategoriaDTO(categoria.getDescricao());
		}
		repository.save(categoria);
		
		return new CategoriaDTO(categoria);
	}

	public Page<CategoriaDTO> findAll(Pageable paginacao) {
		Page<Categoria> findAll = repository.findAll(paginacao);
		
		return CategoriaDTO.converte(findAll);
	}

	public Optional<CategoriaDTO> findById(Long id) {
		Optional<Categoria> optional = repository.findById(id);
		
		return CategoriaDTO.converte(optional);
	}

	public CategoriaDTO atualizar(Categoria categoria) {
		if(repository.existsByDescricao(categoria.getDescricao())) {
			return new CategoriaDTO(categoria.getDescricao());
		}
		
		Optional<Categoria> optional = repository.findById(categoria.getIdCategoria());
		if(optional.isPresent()) {
			optional.get().setDescricao(categoria.getDescricao());
			repository.save(optional.get());
			return new CategoriaDTO(optional.get());
		}
		return CategoriaDTO.converte(optional).get();
	}

	public CategoriaDTO deletar(Long id) {
		Optional<Categoria> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.deleteById(id);			
		}		
		return CategoriaDTO.converte(optional).get();
	}

}
