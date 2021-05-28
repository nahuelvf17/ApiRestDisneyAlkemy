package com.challenge.alkemy.api.disney.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.challenge.alkemy.api.disney.models.entity.Usuario;


/**
 * Clase Encargada de generar la seguridad
 * Clase que implementa los privilegios de cada usuario
 * UserDetails es una clase propia de Spring Security
 */
public class UsuarioMain implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String usuario;
    private String password;
    // Variable que nos da la autorización (no confundir con autenticación)
    // Coleccion de tipo generico que extendiende
    // de GranthedAuthority de Spring security
    private Collection<? extends GrantedAuthority> authorities;

    //Constructor
    public UsuarioMain(String userName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.usuario = userName;
        this.password = password;
        this.authorities = authorities;
    }

    //Metodo que asigna los privilegios (autorización)
    public static UsuarioMain build(Usuario usuario){
        //Convertimos la clase Rol a la clase GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
               
        return new UsuarioMain(usuario.getUserName(), usuario.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return usuario;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
