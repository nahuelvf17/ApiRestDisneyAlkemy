package com.challenge.alkemy.api.disney.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class PeliculaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422229733811235306L;

	@JsonIgnore
	private byte[] imagen;
	
	@NotEmpty
	private String titulo;
	
	@ApiModelProperty(notes = "Fecha de creacion",example = "2020-05-09", position = 2)
    @DateTimeFormat(iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Min(1)
	@Max(5)
	private int calificacion;

	public byte[] getImagen() {
		return imagen;
	}

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

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

}
