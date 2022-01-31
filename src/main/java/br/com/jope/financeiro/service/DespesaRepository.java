package br.com.jope.financeiro.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.jope.financeiro.model.Despesa;
import br.com.jope.financeiro.model.projection.DespesaCategoriaValor;
import br.com.jope.financeiro.model.projection.DespesaValor;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
	
	List<Despesa> findByDescricao(String decricao);

	List<Despesa> findByDescricaoContainingIgnoreCase(String descricao);

	@Query("select d from Despesa d where year(d.dataCadastro) = :ano and month(d.dataCadastro) = :mes ")
	List<Despesa> findByAnoMes(Integer ano, Integer mes);

	@Query(value = "select sum(d.valor) as valor from despesa d where year(d.data_cadastro) = :ano and month(d.data_cadastro) = :mes ", nativeQuery = true)
	List<DespesaValor> loadDespesaPorAnoMes(Integer ano, Integer mes);

	@Query(value = "select sum(d.valor) as valor, d.id_categoria as categoria from despesa d "
			+ "inner join categoria c on c.id_categoria = d.id_categoria "
			+ "where year(d.data_cadastro) = :ano and month(d.data_cadastro) = :mes "
			+ "group by d.id_categoria ", nativeQuery = true)
	List<DespesaCategoriaValor> loadDespesaCategoriaPorAnoMes(Integer ano, Integer mes);
	
}
