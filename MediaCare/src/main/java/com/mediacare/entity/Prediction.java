package com.mediacare.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediacare.mvc.dto.PredictionDto;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="prediction")
public class Prediction {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int id;
	
	@CreationTimestamp
	@Column(name="created_at")
	@DateTimeFormat(pattern = "yyyy-mm-dd : hh")
	private Date createdTime;
	
	@Embedded
	private PredictionDto predictionDto ;
	
	@ManyToOne(cascade = {CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH},
				fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	@ToString.Exclude
	@JsonBackReference
	private Patient patient;
	
	@Column(name="result")
	private String result;
}
