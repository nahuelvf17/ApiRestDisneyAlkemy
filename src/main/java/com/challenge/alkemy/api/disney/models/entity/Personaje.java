package com.challenge.alkemy.api.disney.models.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity()
@Table(name = "personajes")
public class Personaje {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@JsonIgnore
	@Lob
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
	
	@JsonIgnoreProperties(value= {"personajes"}, allowSetters=true)
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "personajes_peliculas",
            joinColumns = {
                    @JoinColumn(name = "personaje_id", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "pelicula_id", nullable = false)})
	private List<Pelicula> peliculas;
	
	public Personaje() {
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

	public void setImagen(byte[] foto) {
		this.imagen = foto;
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

	public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(List<Pelicula> peliculas) {
		this.peliculas.clear();
		peliculas.forEach(this::addPelicula);
	}
	
	public void addPelicula(Pelicula pelicula) {
		this.peliculas.add(pelicula);
		pelicula.getPersonajes().add(this);
	}
	
	public void removePelicula(Pelicula pelicula) {
		this.peliculas.remove(pelicula);
		pelicula.getPersonajes().remove(this);
	}
	
	@JsonIgnore
	public Integer getImagenHashCode() {
		return (this.imagen!=null) ? this.imagen.hashCode() : null;
	}


	
	@Override
	public String toString() {
		return "Personaje [id=" + id + ", imagen=" + Arrays.toString(imagen) + ", nombre=" + nombre + ", edad=" + edad
				+ ", peso=" + peso + ", historia=" + historia + ", peliculas=" + peliculas + "]";
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Personaje)) {
			return false;
		}
		
		Personaje a  = (Personaje) obj;
		
		return this.id !=null && this.id.equals(a.getId());
	}
	
}
