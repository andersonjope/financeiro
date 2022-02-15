package br.com.jope.financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jope.financeiro.dto.ResumoDTO;
import br.com.jope.financeiro.service.ResumoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("resumo")
@SecurityRequirement(name = "financeiro-api")
public class ResumoController {

	@Autowired
	private ResumoService resumoService;
	
	@GetMapping("{ano}/{mes}")
	public ResponseEntity<ResumoDTO> resumoPorAnoMes(@PathVariable("ano") Integer ano, @PathVariable("mes") Integer mes) {
		
		ResumoDTO resumoDTO = resumoService.loadResumo(ano, mes);
		
		return ResponseEntity.ok(resumoDTO);
	}
	
}
