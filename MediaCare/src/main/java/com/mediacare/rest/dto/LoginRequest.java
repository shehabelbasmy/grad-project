package com.mediacare.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@NotBlank(message = "Email Is Required")
	@Email(message = "This Email is not Valid")
	private String email;
	@NotBlank(message = "Password Is Required")
	private String password;
}
