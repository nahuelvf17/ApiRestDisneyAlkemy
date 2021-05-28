package com.challenge.alkemy.api.disney.security.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alkemy.api.disney.commons.ControllerUtils;
import com.challenge.alkemy.api.disney.dto.UsuarioDto;
import com.challenge.alkemy.api.disney.models.entity.Usuario;
import com.challenge.alkemy.api.disney.security.dto.JwtDto;
import com.challenge.alkemy.api.disney.security.dto.LoginUsuario;
import com.challenge.alkemy.api.disney.security.jwt.JwtProvider;
import com.challenge.alkemy.api.disney.services.UsuarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	ControllerUtils util;

	// Espera un json y lo convierte a tipo clase NuevoUsuario
	@ApiOperation(value = "Registro", notes = "Registro de usuario. El usuario va ser el mail para poder enviar el mensaje de bienvenida. No Necesita TOKEN ingresar cualquierr valor para validar.")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result) {
		
		if(result.hasErrors()) {
			return util.validar(result);
		}
		
		usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
		
		Usuario usuarioEntity = new Usuario();
		BeanUtils.copyProperties(usuarioDto, usuarioEntity);
		
		try {
			usuarioService.save(usuarioEntity);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		try {
			util.sendMail(usuarioDto.getUserName());
		}catch(Exception e) {
	        logger.info("Error enviendo mail");
	        logger.info(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}
	
	@ApiOperation(value = "Login", notes = "Login para validar ingreso. No Necesita TOKEN ingresar cualquierr valor para validar.")
	@PostMapping("/login")
    public ResponseEntity<JwtDto> login( @RequestBody LoginUsuario loginData){

		final Usuario usuarioLogin = usuarioService.findUsuarioByUserName(loginData.getUsuario()).get();

		Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginData.getUsuario(),
                                loginData.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(usuarioLogin);
        JwtDto jwtDto = new JwtDto(jwt);

        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
}
