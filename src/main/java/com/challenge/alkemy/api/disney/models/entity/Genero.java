package com.challenge.alkemy.api.disney.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="generos")
public class Genero {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotEmpty
	private String nombre;
	
	@Lob
	@JsonIgnore
	private byte[] imagen;

	@JsonIgnoreProperties(value= {"genero","personajes"}, allowSetters=true)
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "genero", cascade = CascadeType.ALL)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genero", cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	private List<Pelicula> peliculas;
	
	public Genero() {
		this.peliculas=new ArrayList<>();
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@JsonIgnoreProperties(value= {"genero"}, allowSetters=true)
	public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(List<Pelicula> listPeliculas) {
		this.peliculas.clear();
		listPeliculas.forEach(this::addPelicula);
	}

    public void addPelicula(Pelicula pelicula) {
        peliculas.add(pelicula);
        pelicula.setGenero(this);
    }
 
    public void removePelicula(Pelicula pelicula) {
        peliculas.remove(pelicula);
        pelicula.setGenero(null);
    }
    
    @JsonIgnore
	public Integer getImagenHashCode() {
		return (this.imagen!=null) ? this.imagen.hashCode() : null;
	}
	
    @Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Genero)) {
			return false;
		}
		
		Genero g  = (Genero) obj;
		
		return this.id !=null && this.id.equals(g.getId());
	}
}
