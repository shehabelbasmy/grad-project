package com.mediacare.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id ;
	
	@Column(name = "token")
	private String token;

}
