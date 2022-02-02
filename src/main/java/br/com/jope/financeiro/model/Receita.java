package br.com.jope.financeiro.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false, of = "idReceita")
public class Receita extends BaseEntity {

	private static final long serialVersionUID = 5322641382089092456L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RECEITA")
	private Long idReceita;
	
	private String descricao;
	
	private BigDecimal valor;
	
	private LocalDate dataCadastro;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private Usuario usuario;
	
}
