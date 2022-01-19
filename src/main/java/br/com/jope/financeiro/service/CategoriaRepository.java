package br.com.jope.financeiro.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jope.financeiro.model.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

}
