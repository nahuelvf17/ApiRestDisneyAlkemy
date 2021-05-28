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
import org.springframework.http.HttpStatus;
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
import com.challenge.alkemy.api.disney.dto.PeliculaAllDto;
import com.challenge.alkemy.api.disney.dto.PeliculaDto;
import com.challenge.alkemy.api.disney.models.entity.Pelicula;
import com.challenge.alkemy.api.disney.services.PeliculaService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/movies")
@RestController
public class PeliculaController {
	
	private final static Logger logger = LoggerFactory.getLogger(PersonajeController.class);

	@Autowired
	private ControllerUtils util;
	
	@Autowired
	private PeliculaService service;
	
	@ApiOperation(value = "Listar", notes = "Lista tosas las peliculas. Necesita ingresar TOKEN.")
	@GetMapping()
	public ResponseEntity<?> listar(){
		
		List<Pelicula> peliculas = (List<Pelicula>) service.findAll();
		ModelMapper modelMapper = new ModelMapper();
				
		List<PeliculaAllDto> pelisDto = peliculas
				  .stream()
				  .map(peli -> modelMapper.map(peli, PeliculaAllDto.class))
				  .collect(Collectors.toList());

		
		return ResponseEntity.ok(pelisDto);
	}
	
	@ApiOperation(value = "Lista detalle", notes = "Muesta detalle de la pelicula. Necesita ingresar TOKEN.")
	@GetMapping("/details/{id}")
	public ResponseEntity<?> detalle(@PathVariable Long id ){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@ApiOperation(value = "Creacion", notes = "Creacion de pelicula, no esta implementado la carga de la imagen. Necesita ingresar TOKEN.")
	@PostMapping("/create")
	public ResponseEntity<?> crear(@Valid @RequestBody PeliculaDto peliculaDto, BindingResult result ){
		
		if(result.hasErrors()) {
			return util.validar(result);
		}
		
		Pelicula peliculaEntity = new Pelicula();
		BeanUtils.copyProperties(peliculaDto, peliculaEntity);
		
		return ResponseEntity.ok().body(service.save(peliculaEntity));
	}

	@ApiOperation(value = "Modificacion", notes = "Puedo modicar datos de la pelicula y asignar personajes. Necesita ingresar TOKEN.")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> modificar(@Valid @RequestBody Pelicula pelicula, 
			BindingResult result, @PathVariable Long id) {
		
		if(result.hasErrors()) {
			return util.validar(result);
		}
		
		Optional<Pelicula> p = this.service.findById(id);
		if(!p.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		
		// Actualizo las personajes, si no estan tengo que eliminarlas.
		Pelicula peliculaDb = p.get();

		peliculaDb.setTitulo(pelicula.getTitulo());
		peliculaDb.setFechaCreacion(pelicula.getFechaCreacion());
		peliculaDb.setCalificacion(pelicula.getCalificacion());
		
		peliculaDb.getPersonajes()
		.stream()
		.filter(pdb->!pelicula.getPersonajes().contains(pdb))
		.forEach(peliculaDb::removePersonaje);
		
		
		peliculaDb.setPersonajes(pelicula.getPersonajes());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(peliculaDb));
	}
	
	@ApiOperation(value = "Borrar", notes ="Borrar pelicula. Necesita ingresar TOKEN.")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id ){
		try {
			service.deleteById(id);
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().build();

	}

	@ApiOperation(value = "Filtrar", notes = "Lista peliculas por filtros ingresados. Parametro order (ASC-DESC) Necesita ingresar TOKEN.")
	@GetMapping("/filter")
	public ResponseEntity<?> filtrar(@RequestParam(value="name", required=false) String name, 
									 @RequestParam(value="genre", required=false) Integer genre,  
									 @RequestParam(value="order", defaultValue = "ASC") String order){
		
		logger.info(String.format("Aca es nombre: %s edad: %s ListaMovies: %s", name, genre, order));

		if(order.compareTo(ControllerUtils.FILTER_ORDER_ASC)!=0 && order.compareTo(ControllerUtils.FILTER_ORDER_DESC)!=0) {
			return ResponseEntity.badRequest().body("Parametro order incorrecto, solo se acpta ASC or DESC");
		}
		
		return ResponseEntity.ok().body(service.getPeliculasFilter(name, genre, order));
	}
}
