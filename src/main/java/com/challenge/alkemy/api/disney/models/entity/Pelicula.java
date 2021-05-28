package com.challenge.alkemy.api.disney.models.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "peliculas")
public class Pelicula {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@JsonIgnore
	@Lob
	private byte[] imagen;
	
	@NotEmpty
	private String titulo;
	
	@Column(name="fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Min(1)
	@Max(5)
	private int calificacion;
	
	@JsonIgnoreProperties(value= {"peliculas", "personajes"}, allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="genero_id")
	@JsonIgnore
	private Genero genero;

	@JsonIgnoreProperties(value= {"peliculas"}, allowSetters=true)
	@ManyToMany(fetch=FetchType.LAZY,  mappedBy = "peliculas", cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	private List<Personaje> personajes;
	
	public Pelicula() {
		this.personajes=new ArrayList<>();
	}
	
	@PrePersist
	public void PrePersist() {
		this.fechaCreacion= new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Personaje> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(List<Personaje> listPersonajes) {
		this.personajes.clear();
		listPersonajes.forEach(this::addPersonaje);
	}
	
	public void addPersonaje(Personaje personaje) {
		this.personajes.add(personaje);
	}
	
	public void removePersonaje(Personaje personaje) {
		this.personajes.remove(personaje);
	}
	
	@JsonIgnore
	public Integer getImagenHashCode() {
		return (this.imagen!=null) ? this.imagen.hashCode() : null;
	}
	
	@JsonIgnoreProperties(value= {"genero","personajes"}, allowSetters=true)
	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Pelicula)) {
			return false;
		}
		
		Pelicula a  = (Pelicula) obj;
		
		return this.id !=null && this.id.equals(a.getId());
	}

	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", imagen=" + Arrays.toString(imagen) + ", titulo=" + titulo + ", fechaCreacion="
				+ fechaCreacion + ", calificacion=" + calificacion + ", genero=" + genero + ", personajes=" + personajes
				+ "]";
	}
	
}
