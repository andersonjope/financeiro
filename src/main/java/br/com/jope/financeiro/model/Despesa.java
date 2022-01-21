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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false, of = "idDespesa")
public class Despesa extends BaseEntity {

	private static final long serialVersionUID = 4606157339857658748L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DESPESA")
	private Long idDespesa;
	private String descricao;
	private BigDecimal valor;
	@ManyToOne
	@JoinColumn(name = "ID_CATEGORIA", nullable = false)
	private Categoria categoria;

}
