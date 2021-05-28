package com.challenge.alkemy.api.disney.models.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.challenge.alkemy.api.disney.models.entity.Genero;

public interface GeneroRepository extends CrudRepository<Genero, Long>, JpaSpecificationExecutor<Genero> {

}
