package com.mediacare.entity;

import java.sql.Time;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Patient extends MyUser {

	@Column(name="sign_up_time")
	@DateTimeFormat(pattern = "yyyy-mm-dd : hh:mm")
	private Time signUpTime;
	
	@Column(name="update_state")
	@DateTimeFormat(pattern = "yyyy-mm-dd : hh:mm")
	private Time updateState;

}
