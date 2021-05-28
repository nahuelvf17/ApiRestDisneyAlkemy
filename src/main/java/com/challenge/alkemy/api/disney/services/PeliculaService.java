package com.challenge.alkemy.api.disney.services;

import java.util.Optional;

import com.challenge.alkemy.api.disney.models.entity.Pelicula;

public interface PeliculaService {

	public Iterable<Pelicula> findAll();
		
	public Optional<Pelicula> findById(Long id);
	
	public Pelicula save(Pelicula pelicula);
	
	public void deleteById(Long id) throws Exception;
	
	public Iterable<Pelicula> getPeliculasFilter(String name, Integer genre, String order); 
}
