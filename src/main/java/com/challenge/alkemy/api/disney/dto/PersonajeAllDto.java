package com.challenge.alkemy.api.disney.dto;

import java.io.Serializable;

public class PersonajeAllDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7840210715766584349L;

	private byte[] imagen;
	
	private String nombre;

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
	
	
	
}