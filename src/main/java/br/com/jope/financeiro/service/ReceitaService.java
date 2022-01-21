package br.com.jope.financeiro.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.jope.financeiro.dto.ReceitaDTO;
import br.com.jope.financeiro.model.Receita;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository repository;
	
	public ReceitaDTO salvar(Receita receita) {
		if(!repository.validaMesCadastroCategoria(receita.getCategoria().getIdCategoria(), getMonth().getValue()).isEmpty()) {
			return new ReceitaDTO(receita.getDescricao());
		}
		receita.setDataCadastro(LocalDate.now());
		repository.save(receita);
		
		return new ReceitaDTO(receita);
	}

	public Page<ReceitaDTO> findAll(Pageable paginacao) {
		Page<Receita> findAll = repository.findAll(paginacao);
		
		return ReceitaDTO.converte(findAll);
	}

	public Optional<ReceitaDTO> findById(Long id) {
		Optional<Receita> optional = repository.findById(id);
		
		return ReceitaDTO.converte(optional);
	}

	public ReceitaDTO atualizar(Receita receita) {
		List<Receita> receitaList = repository.validaMesCadastroCategoria(receita.getCategoria().getIdCategoria(), getMonth().getValue());
		long existe = receitaList
			.stream()
			.filter(r -> !r.getIdReceita().equals(receita.getIdReceita()))
			.count();
		
		if(existe > 0) {
			return new ReceitaDTO(receita.getDescricao());
		}
		
		Optional<Receita> optional = repository.findById(receita.getIdReceita());
		if(optional.isPresent()) {
			Receita receitaRecuperada = optional.get();
			receitaRecuperada.setDescricao(receita.getDescricao());
			receitaRecuperada.setValor(receita.getValor());
			repository.save(receitaRecuperada);
			return new ReceitaDTO(receitaRecuperada);
		}
		return ReceitaDTO.converte(optional).get();
	}

	public ReceitaDTO deletar(Long id) {
		Optional<Receita> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.deleteById(id);			
		}		
		return ReceitaDTO.converte(optional).get();
	}

	private Month getMonth() {
		return LocalDate.now().getMonth();
	}
	
}
