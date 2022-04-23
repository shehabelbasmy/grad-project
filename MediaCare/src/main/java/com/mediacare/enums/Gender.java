package com.mediacare.enums;

import lombok.Getter;

public enum Gender {

	MALE("Male"),FEMALE("Female");
	
	@Getter
	private String name;
	
	Gender(String name){
		this.name=name;
	}
}
