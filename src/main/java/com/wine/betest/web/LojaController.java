package com.wine.betest.web;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wine.betest.domain.Loja;
import com.wine.betest.domain.LojaRepository;

@RestController
public class LojaController {
	@Autowired
	private LojaRepository repository;

	@RequestMapping(value="/loja", method = RequestMethod.POST)
	public Loja saveLoja (@RequestBody String bodyJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> body = mapper.readValue(bodyJson, Map.class);
			Loja newLoja = new Loja(body);
			repository.save(newLoja);
		} catch (JsonProcessingException e) {
			e.getMessage();
			e.getStackTrace();
		}
	}
}
