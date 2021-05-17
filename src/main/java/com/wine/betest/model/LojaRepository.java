package com.wine.betest.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LojaRepository extends JpaRepository<Loja, Long> {
	Optional<Loja> findByFaixaInicioLessThanEqualAndFaixaFimGreaterThanEqual(String cep, String cep2);

	Optional<Iterable<Loja>> findByCodigoLoja(String codigoLoja);
}
