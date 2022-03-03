package com.mediacare.entity;

import com.mediacare.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Time;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Patient extends User {
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
