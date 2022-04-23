package com.mediacare.mvc.dto;

import lombok.Data;

@Data
public class PredictionDto {

	private ChestPain chestPain;
	
	private float restingBP;
	
	private float cholestrol;
	
	private boolean fastingBg;
	
	private int restECG;
	
	private int maxHR;
	
	private boolean exerciseAgina;
	
	private float oldPeak;
	
	private int st_slope;
}
