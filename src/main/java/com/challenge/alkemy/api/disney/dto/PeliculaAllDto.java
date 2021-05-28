package com.challenge.alkemy.api.disney.dto;

import java.io.Serializable;
import java.util.Date;


public class PeliculaAllDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6849513790321739067L;

	private byte[] imagen;
	
	private String titulo;
	
	private Date fechaCreacion;

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public byte[] getImagen() {
		return imagen;
	}
}
