package com.mediacare.mvc.dto;

import lombok.Getter;

public enum ChestPain {
	
	TA(0),ATA(1),NAP(2),ASY(3);
	
	@Getter
	private int value;

	private ChestPain(int value) {
		this.value = value;
	}
	
}
