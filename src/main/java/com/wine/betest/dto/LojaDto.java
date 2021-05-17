package com.wine.betest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wine.betest.domain.Loja;

public class LojaDto {
	private Long id;
	@NotNull
	private String codigoLoja;
	@NotNull
	@Size(min = 9, max = 9, message = "Campo relativo a cep deve ter exatamente 9 números")
	private String faixaInicio;
	@NotNull
	@Size(min = 9, max = 9, message = "Campo relativo a cep deve ter exatamente 9 números")
	private String faixaFim;

	public LojaDto() {
	}

	public LojaDto(Loja loja) {
		super();
		this.id = loja.getId();
		this.codigoLoja = loja.getCodigoLoja();
		this.faixaInicio = loja.getFaixaInicio();
		this.faixaFim = loja.getFaixaFim();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCodigoLoja(String codigoLoja) {
		this.codigoLoja = codigoLoja;
	}

	public void setFaixaFim(String faixaFim) {
		this.faixaFim = faixaFim;
	}

	public void setFaixaInicio(String faixaInicio) {
		this.faixaInicio = faixaInicio;
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
