package com.challenge.alkemy.api.disney.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UsuarioDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8062358618627167210L;

	@NotEmpty
    private String name;
	
    @Email()
    private String userName;
	
	@NotEmpty
    private String password;

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
