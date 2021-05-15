package com.wine.betest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOJA")
public class Loja {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	@Column(name = "CODIGO_LOJA")
	private String codigoLoja;
	private String faixaInicio, faixaFim;

	public Loja() {
	}

	public Loja(String codigoLoja, String faixaInicio, String faixaFim) {
		super();
		this.codigoLoja = codigoLoja;
		this.faixaInicio = faixaInicio;
		this.faixaFim = faixaFim;
	}

	public String getCodigoLoja() {
		return codigoLoja;
	}

	public String getFaixaInicio() {
		return faixaInicio;
	}

	public String getFaixaFim() {
		return faixaFim;
	}
}
