package com.challenge.alkemy.api.disney.models.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.challenge.alkemy.api.disney.models.entity.Personaje;

@Repository
public interface PersonajeRepository extends CrudRepository<Personaje, Long>, JpaSpecificationExecutor<Personaje>  {
	@Modifying
    @Transactional
	@Query(value = "delete from personajes_peliculas where personaje_id=?1", nativeQuery = true)
    void deleteRelation(Long personaje_id);
}
