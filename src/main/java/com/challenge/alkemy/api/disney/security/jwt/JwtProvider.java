package com.challenge.alkemy.api.disney.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.challenge.alkemy.api.disney.models.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Clase que genera el token y valida que este bien fornamdo y no este expirado
 */
@Component
public class JwtProvider {
	// Implementamos un logger para ver cual metodo da error en caso de falla
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	//Valores que tenemos en el aplicattion.properties
	//@Value("${jwt.secret}")
	private String secret="bauty";

	//@Value("${jwt.expiration}")
	private int expiration=86400;

	public String generateToken(final Usuario usuario){
		Map<String, Object> claims = new HashMap<>();
	    claims.put("usuario", usuario.getUserName());
		claims.put("id", usuario.getId());
		return Jwts.builder().setClaims(claims)
	            .setExpiration(new Date(new Date().getTime() + expiration * 1000))
	            .signWith(SignatureAlgorithm.HS256, secret)
	            .setHeaderParam("typ","JWT")
	            .compact();
	}
	
	//subject --> Nombre del usuario
	public String getNombreUsuarioFromToken(String token){
		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
		return claims.get("usuario", String.class);
	}
	
	public Boolean validateToken(String token){
	    try {
	        logger.error(token);

	    	Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
	        return true;
	    }catch (MalformedJwtException e){
	        logger.error("Token mal formado: {}", e);
	    }catch (UnsupportedJwtException e){
	        logger.error("Token no soportado");
	    }catch (ExpiredJwtException e){
	        logger.error("Token expirado");
	    }catch (IllegalArgumentException e){
	        logger.error("Token vacio");
	    }catch (SignatureException e){
	        logger.error("Fallo con la firma");
	    }
	    return false;
	}
	
	public Boolean validatePassword(Usuario usuario, String password) {
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);  
		return encoder.matches(password, usuario.getPassword()); 
	}
	
	public String encoderPassword(String password) {

		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);  
		return encoder.encode(password); 
	}
}
