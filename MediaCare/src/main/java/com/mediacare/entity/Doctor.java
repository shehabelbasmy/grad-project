package com.mediacare.entity;

import com.mediacare.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Doctor extends User {

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

	@Column(name = "phone_number",length = 11)
	private Integer phoneNumber;

	@Column(name = "des")
	private String designation;

}
