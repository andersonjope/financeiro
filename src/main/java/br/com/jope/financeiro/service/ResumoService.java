package br.com.jope.financeiro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jope.financeiro.dto.ResumoCategoriaDTO;
import br.com.jope.financeiro.dto.ResumoDTO;
import br.com.jope.financeiro.enums.EnumCategoria;
import br.com.jope.financeiro.model.projection.DespesaCategoriaValor;
import br.com.jope.financeiro.model.projection.DespesaValor;
import br.com.jope.financeiro.model.projection.ReceitaValor;

@Service
public class ResumoService {

	@Autowired
	private ReceitaRepository receitaRepository;
	@Autowired
	private DespesaRepository despesaRepository;
	
	public ResumoDTO loadResumo(Integer ano, Integer mes) {
		
		List<ReceitaValor> receitaValor = receitaRepository.loadReceitaPorAnoMes(ano, mes);
		List<DespesaValor> despesaValor = despesaRepository.loadDespesaPorAnoMes(ano, mes);
		List<DespesaCategoriaValor> despesaCategoriaValor = despesaRepository.loadDespesaCategoriaPorAnoMes(ano, mes);
		
		List<ResumoCategoriaDTO> resumoCategoriaList = new ArrayList<>();
		
		despesaCategoriaValor.stream().forEach(new Consumer<DespesaCategoriaValor>() {

			@Override
			public void accept(DespesaCategoriaValor t) {
				ResumoCategoriaDTO build = ResumoCategoriaDTO.builder()
						.valor(t.getValor().toString())
						.descricaoCategoria(EnumCategoria.getCategoria(t.getCategoria()).getDescricao())
					.build();
				resumoCategoriaList.add(build);
			}
		});
		
		ResumoDTO resumoDTO = ResumoDTO.builder()
				.valorTotalReceitas(receitaValor.get(0).getValor().toString())
				.valorTotalDespesas(despesaValor.get(0).getValor().toString())
				.resumoCategoriaList(resumoCategoriaList)				
			.build();
		
		resumoDTO.calculoSaldo();
		
		return resumoDTO;
	}

}
