package com.wine.betest.web;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wine.betest.domain.Loja;
import com.wine.betest.domain.LojaRepository;

@RestController
public class LojaController {
	@Autowired
	private LojaRepository repository;

	@RequestMapping(value = "/loja", method = RequestMethod.GET)
	public ResponseEntity<?> getLojaByCep(@RequestParam(value = "cep", required = false) String cep)
			throws ResourceNotFoundException {
			if(cep != null) {
		Loja findLoja = repository.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(cep, cep)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma loja encontrada que antenda este cep"));
		return ResponseEntity.ok(findLoja);
			} else {
				List<Loja> allLojas = repository.findAll();
				return ResponseEntity.ok().body(allLojas);
			}
	};

	@RequestMapping(value = "/loja/{codigoLoja}", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Loja>> getLojaByCodigo(@PathVariable(value = "codigoLoja") String codigoLoja)
			throws ResourceNotFoundException {
		Iterable<Loja> lojaList = repository.findByCodigoLoja(codigoLoja)
				.orElseThrow(() -> new ResourceNotFoundException("Loja não existente em nosso sistema"));
		return ResponseEntity.ok().body(lojaList);
	};

	@RequestMapping(value = "/loja", method = RequestMethod.POST)
	public void saveLoja(@RequestBody String bodyJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Loja loja = mapper.readValue(bodyJson, Loja.class);
			System.out.println(loja.getCodigoLoja());
			repository.save(loja);
		} catch (JsonProcessingException e) {
			e.getMessage();
			e.getStackTrace();
		}
	}

	@RequestMapping(value = "/loja/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Loja> deleteLoja(@PathVariable(value = "id") Long lojaId) throws ResourceNotFoundException {
		Loja loja = repository.findById(lojaId).orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

		repository.delete(loja);
		return ResponseEntity.ok().body(loja);
	};
}
