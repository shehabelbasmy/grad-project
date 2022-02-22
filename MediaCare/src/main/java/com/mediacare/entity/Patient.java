package com.mediacare.entity;

import java.sql.Time;

import javax.persistence.*;

import com.mediacare.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Patient extends MyUser {
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

	@Column(name = "phone_number",length = 11)
	private Integer phoneNumber;

	@Column(name="sign_up_time")
	@DateTimeFormat(pattern = "yyyy-mm-dd : hh:mm")
	private Time signUpTime;
	
	@Column(name="update_state")
	@DateTimeFormat(pattern = "yyyy-mm-dd : hh:mm")
	private Time updateState;

}
