package com.wine.betest.web;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wine.betest.domain.Loja;
import com.wine.betest.domain.LojaRepository;

@RestController
public class LojaController {
	@Autowired
	private LojaRepository repository;

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
}
