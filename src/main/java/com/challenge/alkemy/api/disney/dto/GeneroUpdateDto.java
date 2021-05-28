package com.challenge.alkemy.api.disney.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class GeneroUpdateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -635761005134585970L;

	@NotEmpty
	private String nombre;
	
	List<PeliculaDto> peliculas;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<PeliculaDto> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(List<PeliculaDto> peliculas) {
		this.peliculas = peliculas;
	}	
}
