package com.mediacare.enums;

import lombok.Getter;

public enum Gender {

	MALE("MALE",1),FEMALE("FEMALE",0);
	
	@Getter
	private int sex;
	
	@Getter
	private String name;
	
	Gender(String name,int sex){
		this.name=name;
		this.sex=sex;
	}
}
