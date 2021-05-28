package com.challenge.alkemy.api.disney.config.controllers;

import java.io.IOException;
import java.util.Optional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.challenge.alkemy.api.disney.commons.ControllerUtils;
import com.challenge.alkemy.api.disney.dto.GeneroDto;
import com.challenge.alkemy.api.disney.dto.GeneroUpdateDto;
import com.challenge.alkemy.api.disney.models.entity.Genero;
import com.challenge.alkemy.api.disney.models.entity.Pelicula;
import com.challenge.alkemy.api.disney.services.GeneroService;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/movies/gender")
@RestController
public class GeneroController {
	
	private final static Logger logger = LoggerFactory.getLogger(GeneroController.class);
	
	@Autowired
	private GeneroService service;
	
	@Autowired
	private ControllerUtils util;
	
	@GetMapping()
	public ResponseEntity<?> listar(){
		
		return ResponseEntity.ok(service.findAll());
	}
	
	@ApiOperation(value = "Detalle", notes = "Muestra el detalle del genero con sus peliculas asociadas. Necesita ingresar TOKEN.")
	@GetMapping("/details/{id}")
	public ResponseEntity<?> detalle(@PathVariable Long id ){
		GeneroDto genero;
		
		try {
			genero = service.findByIdDto(id).get();
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().body(genero);
	}
	
	@ApiOperation(value = "Crear", notes = "Crear genero, se utiliza RequestPart para subir la imagen y por otro lado subir el nombre del genero(String). Necesita ingresar TOKEN.")
	@PostMapping(value="/create", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> crear(@Valid @RequestPart("genero") String genero, BindingResult result, 
									@RequestPart("imagen") MultipartFile imagen ) throws IOException {
		
		if(result.hasErrors()) {
			return util.validar(result);
		}
		
		Genero generoEntity = new Genero();
		
		try {
			generoEntity.setNombre(genero);
			generoEntity.setImagen(imagen.getBytes());

		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().body(service.save(generoEntity));
	}
	
	@ApiOperation(value = "Modificar", notes = "Modificar genero, no admite imagen, la imagen solo se cargan cuando se crean. Necesita ingresar TOKEN.")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> modificar(@Valid @RequestBody GeneroUpdateDto generoUpdateDto, 
			BindingResult result, @PathVariable Long id) {
		
		if(result.hasErrors()) {
			return util.validar(result);
		}
		
		Optional<Genero> p = this.service.findById(id);
		if(!p.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		
		// Actualizo las personajes, si no estan tengo que eliminarlas.
		Genero generoDb = p.get();

		generoDb.setNombre(generoUpdateDto.getNombre());
		
		generoDb.getPeliculas()
		.stream()
		.filter(pdb->!generoUpdateDto.getPeliculas().contains(pdb))
		.forEach(generoDb::removePelicula);
		
		generoUpdateDto.getPeliculas().forEach(pel->{
			Pelicula peliculaAux = new Pelicula();
			BeanUtils.copyProperties(pel, peliculaAux);
			generoDb.addPelicula(peliculaAux);
		});		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(generoDb));
	}
	
	@ApiOperation(value = "Borrar", notes = "Borrar genero. Necesita ingresar TOKEN.")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id ){
		try {
			service.deleteById(id);
		}catch(Exception e) {
			logger.info("aca es por error");
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().body("Finalizo ok!!");
	}
}
