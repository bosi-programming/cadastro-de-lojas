package com.wine.betest.web;

import java.util.List;
import java.util.Map;

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
	public ResponseEntity<?> getLojaByCep(@RequestParam(value = "cep", required = false) String cep,
			@RequestParam(value = "codigoLoja", required = false) String codigoLoja) throws ResourceNotFoundException {
		if (cep != null) {
			Loja findLoja = repository.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(cep, cep)
					.orElseThrow(() -> new ResourceNotFoundException("Nenhuma loja encontrada que antenda este cep"));
			return ResponseEntity.ok(findLoja);
		} else if (codigoLoja != null) {
			Iterable<Loja> lojaList = repository.findByCodigoLoja(codigoLoja)
					.orElseThrow(() -> new ResourceNotFoundException("Loja não existente em nosso sistema"));
			return ResponseEntity.ok().body(lojaList);
		} else {
			List<Loja> allLojas = repository.findAll();
			return ResponseEntity.ok().body(allLojas);
		}
	};

	@RequestMapping(value = "/loja/{id}", method = RequestMethod.GET)
	public ResponseEntity<Loja> getLojaById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Loja loja = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Loja não existente em nosso sistema"));
		return ResponseEntity.ok().body(loja);
	};

	@RequestMapping(value = "/loja", method = RequestMethod.POST)
	public ResponseEntity<?> saveLoja(@RequestBody Loja newLoja) {
		Boolean noLojaOnCep = repository
				.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(newLoja.getFaixaInicio(), newLoja.getFaixaInicio())
				.isEmpty()
				& repository
						.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(newLoja.getFaixaFim(), newLoja.getFaixaFim())
						.isEmpty();

		if (Integer.parseInt(newLoja.getFaixaInicio()) > Integer.parseInt(newLoja.getFaixaFim())) {
			return ResponseEntity.status(400)
					.body(new Exception("A request não contém todos os dados necessários ou os dados estão errados"));
		}

		if (!noLojaOnCep) {
			return ResponseEntity.status(400).body(new Exception("Cep utilizado por outra loja"));
		}

		Loja loja = repository.save(newLoja);
		return ResponseEntity.ok(loja);
	}

	@RequestMapping(value = "/loja/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLoja(@PathVariable(value = "id") Long lojaId, @RequestBody Map<String, String> newLoja)
			throws ResourceNotFoundException {
		Loja loja = repository.findById(lojaId).orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

		String newFaixaInicio = newLoja.get("faixaInicio");
		String newFaixaFim = newLoja.get("faixaFim");

		if (newFaixaInicio == null || newFaixaFim == null
				|| Integer.parseInt(newFaixaInicio) > Integer.parseInt(newFaixaFim)) {
			return ResponseEntity.status(400)
					.body(new Exception("A request não contém todos os dados necessários ou os dados estão errados"));
		}

		Boolean noLojaOnCep = repository
				.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(newFaixaInicio, newFaixaInicio).isEmpty()
				& repository.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(newFaixaFim, newFaixaFim).isEmpty();

		if (!noLojaOnCep) {
			return ResponseEntity.status(400).body(new Exception("Cep utilizado por outra loja"));
		}

		loja.setFaixaInicio(newFaixaInicio);
		loja.setFaixaFim(newFaixaFim);
		final Loja savedLoja = repository.save(loja);
		return ResponseEntity.ok(savedLoja);
	};

	@RequestMapping(value = "/loja/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Loja> deleteLoja(@PathVariable(value = "id") Long lojaId) throws ResourceNotFoundException {
		Loja loja = repository.findById(lojaId).orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

		repository.delete(loja);
		return ResponseEntity.ok().body(loja);
	};
}
