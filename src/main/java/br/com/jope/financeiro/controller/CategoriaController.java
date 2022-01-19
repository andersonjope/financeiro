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

import br.com.jope.financeiro.dto.CategoriaDTO;
import br.com.jope.financeiro.form.CategoriaForm;
import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.service.CategoriaService;

@RestController
@RequestMapping("categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@PostMapping
	public ResponseEntity<CategoriaDTO> cadastrar(@RequestBody @Valid CategoriaForm cadastroForm) {
		Categoria categoria = cadastroForm.converte();
		
		CategoriaDTO categoriaDTO = categoriaService.salvar(categoria);
		
		if(categoriaDTO.isMensagem()) {
			return new ResponseEntity<CategoriaDTO>(categoriaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<CategoriaDTO>(categoriaDTO, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page<CategoriaDTO>> listar(@PageableDefault(sort = "descricao", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
		
		Page<CategoriaDTO> list = categoriaService.findAll(paginacao);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> exibirCategoria(@PathVariable("id") Long id){
		
		Optional<CategoriaDTO> optional = categoriaService.findById(id);
		
		if(optional.isPresent() && optional.get().isMensagem()) {
			return new ResponseEntity<CategoriaDTO>(optional.get(), NOT_FOUND);
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> atualizar(@PathVariable("id") Long id,  @RequestBody @Valid CategoriaForm cadastroForm) {
		Categoria categoria = cadastroForm.converte(id);
		
		CategoriaDTO categoriaDTO = categoriaService.atualizar(categoria);
		
		if(categoriaDTO.isMensagem()) {
			return new ResponseEntity<CategoriaDTO>(categoriaDTO, NOT_FOUND);
		}
		
		return new ResponseEntity<CategoriaDTO>(categoriaDTO, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CategoriaDTO> deletar(@PathVariable("id") Long id) {
		CategoriaDTO categoriaDTO = categoriaService.deletar(id);
		
		return new ResponseEntity<CategoriaDTO>(categoriaDTO, OK);
	}
	
}
