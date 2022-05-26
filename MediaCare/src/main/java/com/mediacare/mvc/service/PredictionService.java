package com.mediacare.mvc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediacare.entity.Prediction;
import com.mediacare.mvc.dao.PredictionRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PredictionService {

	private final PredictionRepo predictionRepo;
	
	@Transactional
	public void savePrediction(Prediction prediction) {
		
		predictionRepo.save(prediction);
	}
}
