package br.com.jope.financeiro.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.jope.financeiro.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
	
	List<Receita> findByDescricao(String decricao);
	
	@Query("select r from Receita r where r.descricao = :descricao and month(r.dataCadastro) = :mes ")
	List<Receita> validaMesCadastroCategoria(String descricao, Integer mes);

	List<Receita> findByDescricaoContainingIgnoreCase(String descricao);

	@Query("select r from Receita r where year(r.dataCadastro) = :ano and month(r.dataCadastro) = :mes ")
	List<Receita> findByAnoMes(Integer ano, Integer mes);
	
}
