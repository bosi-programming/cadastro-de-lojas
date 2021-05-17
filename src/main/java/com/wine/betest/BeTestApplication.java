package com.wine.betest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.wine.betest.model.*;

@SpringBootApplication
public class BeTestApplication {
	@Autowired
	private LojaRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BeTestApplication.class, args);
	}

	@Bean

	CommandLineRunner runner() {
		return args -> {
			Loja loja1 = new Loja("LOJA_PINHEIROS", "100000000", "200000000");
			Loja loja2 = new Loja("LOJA_PINHEIROS", "200000001", "300000000");
			Loja loja3 = new Loja("LOJA_JARDINS", "300000001", "400000000");
			Loja loja4 = new Loja("LOJA_JARDINS", "400000001", "500000000");
			repository.save(loja1);
			repository.save(loja2);
			repository.save(loja3);
			repository.save(loja4);
		};
	}
}
