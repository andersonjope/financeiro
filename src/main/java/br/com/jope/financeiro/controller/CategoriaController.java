package br.com.jope.financeiro.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jope.financeiro.dto.CategoriaDTO;
import br.com.jope.financeiro.service.CategoriaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("categorias")
@SecurityRequirement(name = "financeiro-api")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
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
	
}
