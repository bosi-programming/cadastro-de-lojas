package com.wine.betest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import com.wine.betest.dto.LojaDto;
import com.wine.betest.domain.Loja;
import com.wine.betest.domain.LojaRepository;

@Service
public class LojaService {
	@Autowired
	private LojaRepository repository;
	private ModelMapper modelMapper = new ModelMapper();
	private Type listType = new TypeToken<List<LojaDto>>() {
	}.getType();

	public ResponseEntity<List<LojaDto>> getAllLojas() {
		List<Loja> allLojas = repository.findAll();
		List<LojaDto> lojaDtoList = modelMapper.map(allLojas, listType);
		return ResponseEntity.ok().body(lojaDtoList);
	};

	public ResponseEntity<LojaDto> getLojaById(Long id) {
		Loja loja = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Loja não existente em nosso sistema"));
		return ResponseEntity.ok(new LojaDto(loja));
	};

	public ResponseEntity<?> getLojaByCep(String cep) {
		if (cep.length() != 9) {
			return ResponseEntity.status(400).body(new Exception("Cep inválido"));
		}
		Loja findLoja = repository.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(cep, cep)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma loja encontrada que antenda este cep"));
		return ResponseEntity.ok(new LojaDto(findLoja));
	};

	public ResponseEntity<List<LojaDto>> getLojaByCodigo(String codigoLoja) {
		Iterable<Loja> lojaList = repository.findByCodigoLoja(codigoLoja)
				.orElseThrow(() -> new ResourceNotFoundException("Loja não existente em nosso sistema"));

		List<LojaDto> lojaDtoList = modelMapper.map(lojaList, listType);
		return ResponseEntity.ok().body(lojaDtoList);
	};

	public ResponseEntity<?> saveLoja(LojaDto newLoja) {
		if (testLojaForCep(newLoja)) {
			return ResponseEntity.status(400)
					.body(new Exception("A request não contém todos os dados necessários ou os dados estão errados"));
		}
		if (hasNoLojaOnCep(newLoja)) {
			return ResponseEntity.status(400).body(new Exception("Cep utilizado por outra loja"));
		}

		repository.save(new Loja(newLoja));
		return ResponseEntity.ok(newLoja);
	};

	public ResponseEntity<?> updateLoja(Long id, LojaDto newLoja) {
		Loja loja = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
		if (loja.getId() != newLoja.getId()) {
			return ResponseEntity.status(400).body(
					new Exception("Os dados enviados não batem com a da loja que você quer atualizar, favor conferir os dados"));
		}

		return this.saveLoja(newLoja);
	};

	public ResponseEntity<LojaDto> deleteLoja(Long id) {
		Loja loja = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
		repository.delete(loja);
		return ResponseEntity.ok().body(new LojaDto(loja));
	}

	private Boolean testLojaForCep(LojaDto loja) {
		return Integer.parseInt(loja.getFaixaInicio()) > Integer.parseInt(loja.getFaixaFim());
	};

	private Boolean hasNoLojaOnCep(LojaDto loja) {
		return repository
				.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(loja.getFaixaInicio(), loja.getFaixaInicio())
				.isEmpty()
				& repository.findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(loja.getFaixaFim(), loja.getFaixaFim())
						.isEmpty();
	};
}
