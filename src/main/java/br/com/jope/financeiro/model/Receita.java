package br.com.jope.financeiro.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter @Setter
@EqualsAndHashCode(callSuper = false, of = "idReceita")
public class Receita extends BaseEntity {

	private static final long serialVersionUID = 5322641382089092456L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RECEITA")
	private Long idReceita;
	private String descricao;
	private BigDecimal valor;
	@ManyToOne
	@JoinColumn(name = "ID_CATEGORIA", nullable = false)
	private Categoria categoria;

}
