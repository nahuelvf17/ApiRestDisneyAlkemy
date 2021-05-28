package com.challenge.alkemy.api.disney.commons;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.alkemy.api.disney.mailclient.EmailSenderWelcomeService;
import com.challenge.alkemy.api.disney.models.entity.Genero;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ControllerUtils {
	
	@Autowired
	private EmailSenderWelcomeService service;
	
	public ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err->{
			errores.put(err.getField(), "El campo " + err.getField() +  " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
	
	
	public Genero getJsonGeneroFromString(String genero, MultipartFile imagen) throws Exception {
		Genero generoJson = new Genero();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			generoJson = objectMapper.readValue(genero, Genero.class);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		generoJson.setImagen(imagen.getBytes());

		return generoJson;
	}
	
	public void sendMail(String email) throws MessagingException{

		service.sendSimpleEmailWithImage(email, "Bienvenido");

	}
	
	public static String FILTER_ORDER_ASC = "ASC";
	public static String FILTER_ORDER_DESC = "DESC";

}
