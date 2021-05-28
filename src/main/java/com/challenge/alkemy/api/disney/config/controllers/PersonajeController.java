package com.challenge.alkemy.api.disney.config.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alkemy.api.disney.commons.ControllerUtils;
import com.challenge.alkemy.api.disney.dto.PersonajeAllDto;
import com.challenge.alkemy.api.disney.dto.PersonajeDto;
import com.challenge.alkemy.api.disney.dto.PersonajeUpdateDto;
import com.challenge.alkemy.api.disney.models.entity.Pelicula;
import com.challenge.alkemy.api.disney.models.entity.Personaje;
import com.challenge.alkemy.api.disney.services.PersonajeService;

import io.swagger.annotations.ApiOperation;


@RequestMapping("/characters")
@RestController
public class PersonajeController {
	
	private final static Logger logger = LoggerFactory.getLogger(PersonajeController.class);

	@Autowired
	private PersonajeService service;
	
	@Autowired
	private ControllerUtils util;
	
	@ApiOperation(value = "Creacion", notes = "Creacion de personaje, no esta implementado la carga de la imagen. Necesita ingresar TOKEN.")
	@PostMapping("/create")
	public ResponseEntity<?> crear(@Valid @RequestBody PersonajeDto personajeDto, BindingResult result) {
		if(result.hasErrors()) {
			return util.validar(result);
		}
		
		Personaje personajeEntity = new Personaje();
		BeanUtils.copyProperties(personajeDto, personajeEntity);
		
		return ResponseEntity.ok().body(service.save(personajeEntity));
	}
	
	@ApiOperation(value = "Modificacion", notes = "Modificacion de personaje, tambien se puede cargar las peliculas asociadas. Necesita ingresar TOKEN.")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> modificar(@Valid @RequestBody PersonajeUpdateDto personajeUpdateDto, 
										BindingResult result, @PathVariable Long id) {
		
		if(result.hasErrors()) {
			return util.validar(result);
		}
				
		Optional<Personaje> p = this.service.findById(id);
		if(!p.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		// Actualizo las peliulas, si no estan tengo que eliminarlas.
		Personaje personajeDb = p.get();

		personajeDb.setNombre(personajeUpdateDto.getNombre());
		personajeDb.setEdad(personajeUpdateDto.getEdad());
		personajeDb.setPeso(personajeUpdateDto.getPeso());
		personajeDb.setHistoria(personajeUpdateDto.getHistoria());
		
		List<Pelicula> peliRemove =  personajeDb.getPeliculas()
		.stream()
		.filter(pdb->!personajeUpdateDto.getPeliculas().contains(pdb)).collect(Collectors.toList());	
		
		personajeDb.getPeliculas().removeAll(peliRemove);
		
		personajeUpdateDto.getPeliculas().forEach(pel->{
			Pelicula peliculaAux = new Pelicula();
			BeanUtils.copyProperties(pel, peliculaAux);
			personajeDb.addPelicula(peliculaAux);
		});

		service.save(personajeDb);
		
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/details/{id}")
	public ResponseEntity<?> detalle(@PathVariable Long id ){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> listar(){
		
		List<Personaje> personajes = (List<Personaje>) service.findAll();
		ModelMapper modelMapper = new ModelMapper();
		
		
		List<PersonajeAllDto> personajesDto = personajes
				  .stream()
				  .map(per -> modelMapper.map(per, PersonajeAllDto.class))
				  .collect(Collectors.toList());
		
		
		return ResponseEntity.ok(personajesDto);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id ){
		try {
			service.deleteById(id);
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/filter")
	public ResponseEntity<?> filtrar(@RequestParam(value="name", required=false) String name, 
									 @RequestParam(value="age", required=false) Integer age,  
									 @RequestParam(value="movies", required = false) List<Long> idMovies){
		return ResponseEntity.ok().body(service.getPersonajesFilter(name, age, idMovies));
	}
}
