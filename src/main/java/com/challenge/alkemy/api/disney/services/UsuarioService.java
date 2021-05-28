package com.challenge.alkemy.api.disney.services;

import java.util.Optional;

import com.challenge.alkemy.api.disney.models.entity.Usuario;

public interface UsuarioService {

	public Iterable<Usuario> findAll();
			
	public Usuario save(Usuario usuario) throws Exception;
		
	public Optional<Usuario> findUsuarioByUserName(String userName);
	
}
