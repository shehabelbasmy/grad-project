package com.mediacare.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NewUserForm {

	@NotBlank(message = "This field is required")
	@Size(min = 5, message = "At least 5 Character")
	private String firstName;
	
	@NotBlank(message = "This field is required")
	@Size(min = 5,message = "At least 5 Character")
	private String lastName;

	@NotBlank(message = "This field is required")
	@Size(min = 5,message = "At least 5 Character")
	@Email(message = "Invalid")
	private String email;
	
	@NotBlank(message = "This field is required")
	@Size(min = 8,message = "password must be at least 8 Character")
	private String password;
}
