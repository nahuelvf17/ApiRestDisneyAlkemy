package com.challenge.alkemy.api.disney.services;

import java.util.List;
import java.util.Optional;

import com.challenge.alkemy.api.disney.models.entity.Personaje;

public interface PersonajeService {
	public Iterable<Personaje> findAll();
	
	
	public Optional<Personaje> findById(Long id);
	
	public Personaje save(Personaje personaje);
	
	public void deleteById(Long id) throws Exception;

	public Iterable<Personaje> getPersonajesFilter(String name, Integer age, List<Long> moviesId); 

}
