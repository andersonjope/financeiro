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

import br.com.jope.financeiro.dto.ReceitaDTO;
import br.com.jope.financeiro.form.ReceitaForm;
import br.com.jope.financeiro.model.Receita;
import br.com.jope.financeiro.model.Usuario;
import br.com.jope.financeiro.security.config.IAuthenticationFacade;
import br.com.jope.financeiro.service.ReceitaService;

@RestController
@RequestMapping("receitas")
public class ReceitaController {
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private ReceitaService receitaService;

	@PostMapping
	public ResponseEntity<ReceitaDTO> cadastrar(@RequestBody @Valid ReceitaForm receitaForm) {
		Authentication authentication = authenticationFacade.getAuthentication();
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		Receita receita = receitaForm.converte(usuario);
		
		ReceitaDTO receitaDTO = receitaService.salvar(receita);
		
		if(receitaDTO.isMensagem()) {
			return new ResponseEntity<ReceitaDTO>(receitaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<ReceitaDTO>(receitaDTO, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page<ReceitaDTO>> listar(@PageableDefault(sort = "descricao", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
		
		Page<ReceitaDTO> list = receitaService.findAll(paginacao);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDTO> exibirReceita(@PathVariable("id") Long id){
		
		Optional<ReceitaDTO> optional = receitaService.findById(id);
		
		if(optional.isPresent() && optional.get().isMensagem()) {
			return new ResponseEntity<ReceitaDTO>(optional.get(), NOT_FOUND);
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReceitaDTO> atualizar(@PathVariable("id") Long id,  @RequestBody @Valid ReceitaForm receitaForm) {
		Receita receita = receitaForm.converte(id);
		
		ReceitaDTO receitaDTO = receitaService.atualizar(receita);
		
		if(receitaDTO.isMensagem()) {
			return new ResponseEntity<ReceitaDTO>(receitaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<ReceitaDTO>(receitaDTO, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ReceitaDTO> deletar(@PathVariable("id") Long id) {
		ReceitaDTO receitaDTO = receitaService.deletar(id);
		
		return new ResponseEntity<ReceitaDTO>(receitaDTO, OK);
	}
	
	@GetMapping(params = "descricao")
	public ResponseEntity<List<ReceitaDTO>> filtroPorDescricao(@Param("descricao") String descricao){
		List<ReceitaDTO> list = receitaService.findByDescricaoContainingIgnoreCase(descricao);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<List<ReceitaDTO>> filtroPorAnoMes(@PathVariable("ano") Integer ano, @PathVariable("mes") Integer mes){
		List<ReceitaDTO> list = receitaService.findByAnoMes(ano, mes);
		
		return ResponseEntity.ok(list);
	}
	
}
