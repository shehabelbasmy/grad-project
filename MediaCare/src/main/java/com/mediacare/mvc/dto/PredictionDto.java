package com.mediacare.mvc.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class PredictionDto {

	@Column(name = "age")
	private int age;
	
	@Column(name = "sex")
	private int sex;
	
	@Column(name = "resting_BP")
	private int restingBP;
	
	@Column(name = "cholesterol")
	private int cholesterol;
	
	@Column(name = "fasting_BS")
	private int fastingBS;
	
	@Column(name = "max_HR")
	private int maxHR;
	
	@Column(name = "exercise_Angina")
	private int exerciseAngina;
	
	@Column(name = "old_Peak")
	private float oldPeak;
	
	@Column(name = "chest_Pain")
	private ChestPain chestPain;
	
	@Column(name = "resting_ECG")
	private String restingECG;
	
	@Column(name = "st_Slop")
	private STSlope stSlope;
}
