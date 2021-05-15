package com.wine.betest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Loja {
	@Column(name = "CODIGO_LOJA", unique = true)
	private String codigoLoja, faixaInicio, faixaFim;

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
