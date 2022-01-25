package br.com.jope.financeiro.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.jope.financeiro.dto.DespesaDTO;
import br.com.jope.financeiro.model.Despesa;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repository;
	
	public DespesaDTO salvar(Despesa despesa) {
		if(!repository.validaMesCadastroCategoria(despesa.getCategoria().getIdCategoria(), getMonth().getValue()).isEmpty()) {
			return new DespesaDTO(despesa.getDescricao());
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
		List<Despesa> despesaList = repository.validaMesCadastroCategoria(despesa.getCategoria().getIdCategoria(), getMonth().getValue());
		long existe = despesaList
			.stream()
			.filter(r -> !r.getIdDespesa().equals(despesa.getIdDespesa()))
			.count();
		
		if(existe > 0) {
			return new DespesaDTO(despesa.getDescricao());
		}
		
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

	private Month getMonth() {
		return LocalDate.now().getMonth();
	}
	
}
