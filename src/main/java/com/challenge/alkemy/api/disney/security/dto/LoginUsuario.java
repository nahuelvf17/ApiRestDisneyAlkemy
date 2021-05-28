package com.challenge.alkemy.api.disney.security.dto;

import java.io.Serializable;

public class LoginUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1007810749510610940L;
	
	private String usuario;
    private String password;

    public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
