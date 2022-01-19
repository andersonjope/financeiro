package br.com.jope.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jope.financeiro.dto.CategoriaDTO;
import br.com.jope.financeiro.model.Categoria;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public CategoriaDTO salvar(Categoria categoria) {
		repository.save(categoria);
		
		return new CategoriaDTO(categoria);
	}

}
