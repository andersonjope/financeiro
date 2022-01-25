package br.com.jope.financeiro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.jope.financeiro.dto.DespesaDTO;
import br.com.jope.financeiro.enums.EnumCategoria;
import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.model.Despesa;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public DespesaDTO salvar(Despesa despesa) {
		if(despesa.getCategoria().getIdCategoria() == null) {
			Categoria categoria = categoriaRepository.findByDescricao(EnumCategoria.OUTRAS.getDescricao());
			despesa.setCategoria(categoria);
		}
		
		despesa.setDataCadastro(LocalDate.now());
		repository.save(despesa);
		
		return new DespesaDTO(despesa);
	}

	public Page<DespesaDTO> findAll(Pageable paginacao) {
		Page<Despesa> findAll = repository.findAll(paginacao);
		
		return DespesaDTO.converte(findAll);
	}

	public Optional<DespesaDTO> findById(Long id) {
		Optional<Despesa> optional = repository.findById(id);
		
		return DespesaDTO.converte(optional);
	}

	public DespesaDTO atualizar(Despesa despesa) {
		Optional<Despesa> optional = repository.findById(despesa.getIdDespesa());
		if(optional.isPresent()) {
			Despesa despesaRecuperada = optional.get();
			despesaRecuperada.setDescricao(despesa.getDescricao());
			despesaRecuperada.setValor(despesa.getValor());
			repository.save(despesaRecuperada);
			return new DespesaDTO(despesaRecuperada);
		}
		return DespesaDTO.converte(optional).get();
	}

	public DespesaDTO deletar(Long id) {
		Optional<Despesa> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.deleteById(id);			
		}		
		return DespesaDTO.converte(optional).get();
	}

	public List<DespesaDTO> findByDescricaoContainingIgnoreCase(String descricao){
		List<Despesa> list = repository.findByDescricaoContainingIgnoreCase(descricao);
	
		return list.stream().map(DespesaDTO::new).collect(Collectors.toList());
	}

	public List<DespesaDTO> findByAnoMes(Integer ano, Integer mes) {
		List<Despesa> list = repository.findByAnoMes(ano, mes);
		
		return list.stream().map(DespesaDTO::new).collect(Collectors.toList());
	}

}
