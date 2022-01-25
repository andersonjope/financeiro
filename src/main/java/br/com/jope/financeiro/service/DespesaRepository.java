package br.com.jope.financeiro.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.jope.financeiro.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
	
	List<Despesa> findByDescricao(String decricao);

	List<Despesa> findByDescricaoContainingIgnoreCase(String descricao);

	@Query("select d from Despesa d where year(d.dataCadastro) = :ano and month(d.dataCadastro) = :mes ")
	List<Despesa> findByAnoMes(Integer ano, Integer mes);
	
}
