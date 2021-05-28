package com.challenge.alkemy.api.disney.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.alkemy.api.disney.dto.GeneroDto;
import com.challenge.alkemy.api.disney.models.entity.Genero;
import com.challenge.alkemy.api.disney.models.entity.Pelicula;
import com.challenge.alkemy.api.disney.models.repository.GeneroRepository;

@Service
public class GeneroImpl implements GeneroService {

	private final static Logger logger = LoggerFactory.getLogger(GeneroImpl.class);

	
	@Autowired
	private GeneroRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Genero> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Genero> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Optional<GeneroDto> findByIdDto(Long id) throws Exception {
		Optional<Genero> genero = repository.findById(id);
		
		if(!genero.isPresent()) {
			throw new Exception("No se encontro el genero");
		}
		
		ModelMapper modelMapper = new ModelMapper();
				
		GeneroDto generoDto = modelMapper.map(genero.get(), GeneroDto.class);	
		
		return Optional.of(generoDto);
	}

	@Override
	@Transactional
	public Genero save(Genero genero) {
		return repository.save(genero);
	}

	@Override
	@Transactional
	public void deleteById(Long id) throws Exception {
		Optional<Genero> genero = repository.findById(id);
		
		if(!genero.isPresent()) {
			throw new Exception("Error al intentar borrar, no existe el id");
		}
		Genero genre = genero.get();


		List<Pelicula> peliRemove =  genre.getPeliculas()
		.stream()
		.collect(Collectors.toList());	
		
		peliRemove
		.stream()
		.forEach(genre::removePelicula);
				
		repository.save(genre);
		
		repository.deleteById(id);
		
	}
}
