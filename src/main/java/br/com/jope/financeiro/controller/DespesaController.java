package br.com.jope.financeiro.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import br.com.jope.financeiro.model.Usuario;
import br.com.jope.financeiro.security.config.IAuthenticationFacade;
import br.com.jope.financeiro.service.DespesaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("despesas")
@SecurityRequirement(name = "financeiro-api")
public class DespesaController {
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private DespesaService service;

	@PostMapping
	public ResponseEntity<DespesaDTO> cadastrar(@RequestBody @Valid DespesaForm receitaForm) {
		Authentication authentication = authenticationFacade.getAuthentication();
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		Despesa receita = receitaForm.converte(usuario);
		
		DespesaDTO receitaDTO = service.salvar(receita);
		
		if(receitaDTO.isMensagem()) {
			return new ResponseEntity<>(receitaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<>(receitaDTO, HttpStatus.CREATED);
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
			return new ResponseEntity<>(optional.get(), NOT_FOUND);
		}
		
		return ResponseEntity.ok(optional.orElse(new DespesaDTO()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DespesaDTO> atualizar(@PathVariable("id") Long id,  @RequestBody @Valid DespesaForm receitaForm) {
		Despesa receita = receitaForm.converte(id);
		
		DespesaDTO receitaDTO = service.atualizar(receita);
		
		if(receitaDTO.isMensagem()) {
			return new ResponseEntity<>(receitaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<>(receitaDTO, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DespesaDTO> deletar(@PathVariable("id") Long id) {
		DespesaDTO receitaDTO = service.deletar(id);
		
		return new ResponseEntity<>(receitaDTO, OK);
	}
	
	@GetMapping(params = "descricao")
	public ResponseEntity<List<DespesaDTO>> filtroPorDescricao(@Param("descricao") String descricao){
		List<DespesaDTO> list = service.findByDescricaoContainingIgnoreCase(descricao);
		
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<List<DespesaDTO>> filtroPorAnoMes(@PathVariable("ano") Integer ano, @PathVariable("mes") Integer mes){
		List<DespesaDTO> list = service.findByAnoMes(ano, mes);
		
		return ResponseEntity.ok(list);
	}
	
}
