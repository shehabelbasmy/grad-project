package com.mediacare.mvc.dto;

import lombok.Data;

@Data
public class PredictionDto {

	private int age;
	
	private int sex;
	
	private int restingBP;
	
	private int cholesterol;
	
	
	private int fastingBS;
	
	private int maxHR;
	
	private int exerciseAngina;
	
	private float oldPeak;
	
	private ChestPain chestPain;

	private String restingECG;
	
	private STSlope stSlope;
}
