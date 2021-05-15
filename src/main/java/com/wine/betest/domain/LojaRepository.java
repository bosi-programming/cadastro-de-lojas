package com.wine.betest.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LojaRepository extends CrudRepository <Loja, String> {}
