package com.mediacare.mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediacare.validation.FieldMatch;
import com.mediacare.validation.UniqueEmail;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldMatch.List({@FieldMatch(first = "password",second = "confirmPassword",message = "Password doesn't match")})
@Data
@NoArgsConstructor
public class NewUserDto {

	@Setter(AccessLevel.NONE)
	private int id;

	@NotBlank(message = "Firstname is required")
	@Size(min = 2, message = "At least 5 Character")
	private String firstName;
	
	@NotBlank(message = "Lastname is required")
	@Size(min = 2,message = "At least 5 Character")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid")
	@UniqueEmail
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8,message = "password must be at least 8 Character")
	private String password;

	@NotBlank(message = "Confirm Password is required")
	@Size(min = 8,message = "password must be at least 8 Character")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String confirmPassword;
}
