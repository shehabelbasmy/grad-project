package com.mediacare.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Nurse extends MyUser{

	
	@DateTimeFormat(pattern = "hh:mm")
	@Column(name = "start_shift")
	private Time startShiftTime;
	
	@DateTimeFormat(pattern = "hh:mm")
	@Column(name = "end_shift")
	private Time endShiftTime;
}
