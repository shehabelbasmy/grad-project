package com.mediacare.mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class NewUserDto {

	@Setter(AccessLevel.NONE)
	private int id;

	@NotBlank(message = "Firstname is required")
	@Size(min = 5, message = "At least 5 Character")
	private String firstName;
	
	@NotBlank(message = "Lastname is required")
	@Size(min = 5,message = "At least 5 Character")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8,message = "password must be at least 8 Character")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
}
