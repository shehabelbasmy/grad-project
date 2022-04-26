package com.mediacare.mvc.dto;

import lombok.Getter;

public enum STSlope {
	Up(0),Down(2),Flat(1);
	
	@Getter
	private int value;
	
	STSlope(int value){
		this.value=value;
	}
}
