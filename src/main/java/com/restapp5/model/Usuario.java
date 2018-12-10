package com.restapp5.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {
	
	@NotNull
	private Long id;
	
	private String username;
	
	private String password;
	
	@Email
	private String email;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date fnac;

	public Usuario() {
		id=0L;
	}
	
}
