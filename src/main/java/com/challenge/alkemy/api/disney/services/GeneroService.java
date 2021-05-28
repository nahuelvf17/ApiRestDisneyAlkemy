package com.challenge.alkemy.api.disney.services;

import java.util.Optional;

import com.challenge.alkemy.api.disney.dto.GeneroDto;
import com.challenge.alkemy.api.disney.models.entity.Genero;

public interface GeneroService {
	public Iterable<Genero> findAll();
	
	public Optional<Genero> findById(Long id);
	
	public Genero save(Genero genero);
	
	public void deleteById(Long id) throws Exception;
	
	public Optional<GeneroDto> findByIdDto(Long id) throws Exception;
}
