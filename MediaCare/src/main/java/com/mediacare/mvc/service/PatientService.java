package com.mediacare.mvc.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediacare.entity.Patient;
import com.mediacare.entity.Prediction;
import com.mediacare.rest.dao.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	
	@Transactional
	public void save(Patient patient) {
		
		patientRepository.save(patient);
		
	}

	@Transactional
	public Patient getLoggedUser(String username) {
		// TODO Auto-generated method stub
		return patientRepository.findByEmail(username).get();
	}

	@Transactional
	public List<Prediction> getPredictionList() {
		var email=SecurityContextHolder.getContext().getAuthentication().getName();
		List<Prediction> predictionList = patientRepository.findByEmail(email).get().getPrediction();
		System.out.println(email);
		return predictionList;
	}
}
