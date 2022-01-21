package br.com.jope.financeiro.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.jope.financeiro.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
	
	List<Receita> findByDescricao(String decricao);
	
	@Query("select r from Receita r join r.categoria c where c.idCategoria = :idCategoria and month(r.dataCadastro) = :mes ")
	List<Receita> validaMesCadastroCategoria(Long idCategoria, Integer mes);
	
}
