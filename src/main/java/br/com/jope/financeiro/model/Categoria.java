package br.com.jope.financeiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.jope.financeiro.enums.EnumClassificacaoCategoria;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false, of = "idCategoria")
public class Categoria extends BaseEntity{

	private static final long serialVersionUID = 5812259349271844508L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CATEGORIA")
	private Long idCategoria;
	
	@Column(unique = true, nullable = false)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "classificacao", nullable = false)
	private EnumClassificacaoCategoria classificacaoCategoria;
	
}
