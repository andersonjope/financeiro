package br.com.jope.financeiro.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jope.financeiro.dto.DespesaDTO;
import br.com.jope.financeiro.form.DespesaForm;
import br.com.jope.financeiro.model.Despesa;
import br.com.jope.financeiro.service.DespesaService;

@RestController
@RequestMapping("despesas")
public class DespesaController {
	
	@Autowired
	private DespesaService service;

	@PostMapping
	public ResponseEntity<DespesaDTO> cadastrar(@RequestBody @Valid DespesaForm receitaForm) {
		Despesa receita = receitaForm.converte();
		
		DespesaDTO receitaDTO = service.salvar(receita);
		
		if(receitaDTO.isMensagem()) {
			return new ResponseEntity<DespesaDTO>(receitaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<DespesaDTO>(receitaDTO, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page<DespesaDTO>> listar(@PageableDefault(sort = "descricao", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
		
		Page<DespesaDTO> list = service.findAll(paginacao);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DespesaDTO> exibirDespesa(@PathVariable("id") Long id){
		
		Optional<DespesaDTO> optional = service.findById(id);
		
		if(optional.isPresent() && optional.get().isMensagem()) {
			return new ResponseEntity<DespesaDTO>(optional.get(), NOT_FOUND);
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DespesaDTO> atualizar(@PathVariable("id") Long id,  @RequestBody @Valid DespesaForm receitaForm) {
		Despesa receita = receitaForm.converte(id);
		
		DespesaDTO receitaDTO = service.atualizar(receita);
		
		if(receitaDTO.isMensagem()) {
			return new ResponseEntity<DespesaDTO>(receitaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<DespesaDTO>(receitaDTO, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DespesaDTO> deletar(@PathVariable("id") Long id) {
		DespesaDTO receitaDTO = service.deletar(id);
		
		return new ResponseEntity<DespesaDTO>(receitaDTO, OK);
	}
	
}