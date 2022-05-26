package com.mediacare.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediacare.entity.Prediction;
import com.mediacare.mvc.service.PatientService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/prediction")
@AllArgsConstructor
public class PredictionControllerRest {

	private final PatientService patientService;
	
	@GetMapping("/list")
	public List<Prediction> getPredictionList(){
		
		List<Prediction> predictionList = patientService.getPredictionList();
		
		System.out.println(predictionList);
		
		return predictionList;
	}
}
