package br.com.jope.financeiro.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jope.financeiro.dto.CategoriaDTO;
import br.com.jope.financeiro.form.CategoriaForm;
import br.com.jope.financeiro.model.Categoria;
import br.com.jope.financeiro.service.CategoriaService;

@RestController
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@PostMapping
	public ResponseEntity<CategoriaDTO> cadastro(@RequestBody @Valid CategoriaForm cadastroForm) {
		Categoria categoria = cadastroForm.converte();
		
		CategoriaDTO categoriaDTO = categoriaService.salvar(categoria);
		
		return ResponseEntity.ok(categoriaDTO);
	}
	
}
