package com.challenge.alkemy.api.disney.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class GeneroDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5541301416512448797L;

	
	@NotEmpty
	private String nombre;
	
	private List<PeliculaDto> peliculas;

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
