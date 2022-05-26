package com.mediacare.mvc.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediacare.enums.Gender;
import com.mediacare.validation.FieldMatch;
import com.mediacare.validation.UniqueEmail;

import lombok.Data;
import lombok.NoArgsConstructor;

@FieldMatch.List({@FieldMatch(first = "password",second = "confirmPassword",message = "Password doesn't match")})
@Data
@NoArgsConstructor
public class NewUserDto {

//	@Setter(AccessLevel.NONE)
//	private int id;

	@NotBlank(message = "Firstname is required")
	@Size(min = 5, message = "At least 5 Character")
	private String firstName;
	
	@NotBlank(message = "Lastname is required")
	@Size(min = 5,message = "At least 5 Character")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid")
	@UniqueEmail
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 2,message = "password must be at least 2 Character")
	private String password;

	@NotBlank(message = "Confirm Password is required")
	@Size(min = 2,message = "password must be at least 2 Character")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String confirmPassword;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd",iso = ISO.DATE)
	private Date birthDate;
	
	private Gender gender;
}
