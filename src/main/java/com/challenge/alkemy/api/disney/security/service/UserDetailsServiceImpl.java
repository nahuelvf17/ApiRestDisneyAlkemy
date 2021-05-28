package com.challenge.alkemy.api.disney.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.alkemy.api.disney.models.entity.Usuario;
import com.challenge.alkemy.api.disney.security.dto.UsuarioMain;
import com.challenge.alkemy.api.disney.services.UsuarioService;


/**
 * Clase que convierte la clase usuario en un UsuarioMain
 * UserDetailsService es propia de Spring Security
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = service.findUsuarioByUserName(nombreUsuario).get();
        return UsuarioMain.build(usuario);
    }
}
