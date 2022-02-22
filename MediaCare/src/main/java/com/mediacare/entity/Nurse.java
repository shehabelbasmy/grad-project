package com.mediacare.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.mediacare.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Nurse extends MyUser{

	@Column(name = "phone_number",length = 11)
	private Integer phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

	@DateTimeFormat(pattern = "hh:mm")
	@Column(name = "start_shift")
	private Time startShiftTime;
	
	@DateTimeFormat(pattern = "hh:mm")
	@Column(name = "end_shift")
	private Time endShiftTime;
}
