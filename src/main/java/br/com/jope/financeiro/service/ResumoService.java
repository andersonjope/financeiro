package br.com.jope.financeiro.service;

import java.util.List;
import java.util.stream.Collectors;

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
		
		if(!receitaValor.isEmpty() && receitaValor.get(0) != null) {
			List<ResumoCategoriaDTO> resumoCategoriaList = despesaCategoriaValor.stream().map(dcv -> 
			ResumoCategoriaDTO.builder()
				.valor(dcv.getValor().toString())
				.descricaoCategoria(EnumCategoria.getCategoria(dcv.getCategoria()).getDescricao())
				.build()
					).collect(Collectors.toList());
			
			ResumoDTO resumoDTO = ResumoDTO.builder()
					.valorTotalReceitas(receitaValor.get(0).getValor().toString())
					.valorTotalDespesas(despesaValor.get(0).getValor().toString())
					.resumoCategoriaList(resumoCategoriaList)				
					.build();
			
			resumoDTO.calculoSaldo();
			
			return resumoDTO;
		}
		return ResumoDTO.builder().build();
	}

}
