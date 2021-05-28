package com.challenge.alkemy.api.disney.models.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.challenge.alkemy.api.disney.models.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	public Optional<Usuario> findUsuarioByUserName(String userName);
}
