package com.wine.betest.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wine.betest.domain.LojaRepository;
import com.wine.betest.dto.LojaDto;
import com.wine.betest.service.LojaService;

@RestController
public class LojaController {
	@Autowired
	LojaService service;
	@Autowired
	LojaRepository repository;

	@RequestMapping(value = "/loja", method = RequestMethod.GET)
	public ResponseEntity<?> getLoja(@RequestParam(value = "cep", required = false) String cep,
			@RequestParam(value = "codigoLoja", required = false) String codigoLoja) throws ResourceNotFoundException {
		if (cep != null) {
			return service.getLojaByCep(cep);
		} else if (codigoLoja != null) {
			return service.getLojaByCodigo(codigoLoja);
		} else {
			return service.getAllLojas();
		}
	};

	@RequestMapping(value = "/loja/{id}", method = RequestMethod.GET)
	public ResponseEntity<LojaDto> getLojaById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		return service.getLojaById(id);
	};

	@RequestMapping(value = "/loja", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> saveLoja(@Valid @RequestBody LojaDto newLoja) {
		return service.saveLoja(newLoja);
	}

	@RequestMapping(value = "/loja/{id}", method = RequestMethod.PUT)
	public synchronized ResponseEntity<?> updateLoja(@PathVariable(value = "id") Long lojaId, @Valid @RequestBody LojaDto newLoja)
			throws ResourceNotFoundException {
		return service.updateLoja(lojaId, newLoja);
	};

	@RequestMapping(value = "/loja/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<LojaDto> deleteLoja(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		return service.deleteLoja(id);
	};
}
