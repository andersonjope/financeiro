package br.com.jope.financeiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false, of = "idCategoria")
public class Categoria extends BaseEntity{

	private static final long serialVersionUID = 5812259349271844508L;
	
	@Id
	@Column(name = "ID_CATEGORIA")
	private Long idCategoria;
	
	@Column(unique = true, nullable = false)
	private String descricao;
	
}
