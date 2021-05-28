package com.challenge.alkemy.api.disney.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PersonajeDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2662888507801229806L;

	@JsonIgnore
	private byte[] imagen;
	
	@NotEmpty
	private String nombre;
	
	@NotNull
	@Min(1)
	private int edad;
	
	@NotNull
	@Min(1)
	private int peso;
	
	@NotEmpty
	private String historia;

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getHistoria() {
		return historia;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
	}
}
