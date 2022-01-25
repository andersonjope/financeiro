package br.com.jope.financeiro.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jope.financeiro.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	boolean existsByDescricao(String descricao);
	
	Categoria findByDescricao(String decricao);
	
}
