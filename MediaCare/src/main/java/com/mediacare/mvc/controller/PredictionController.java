package com.mediacare.mvc.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mediacare.entity.Patient;
import com.mediacare.entity.Prediction;
import com.mediacare.mvc.dto.PredictionDto;
import com.mediacare.mvc.service.MlService;
import com.mediacare.mvc.service.PatientService;
import com.mediacare.mvc.service.PredictionService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class PredictionController {

	private final MlService mlService;
	private final PatientService patientService;
	private final PredictionService predictionService;
	
	@GetMapping("prediction")
    public ModelAndView makePredction() {
    	ModelAndView modelAndView=new ModelAndView();
    	
    	modelAndView.getModelMap().addAttribute("predictionTemp", new PredictionDto());
    	
    	modelAndView.setViewName("prediction-form");
    	
    	return modelAndView;
    }
    
    @PostMapping("result")
    public ModelAndView makePrediction(@ModelAttribute("predictionTemp") PredictionDto predictionDto) {
    	ModelAndView modelAndView= new ModelAndView(); 
    	
    	String result = getPrediction(predictionDto);
    	
    	modelAndView.getModelMap().addAttribute("result", result);
    	
    	modelAndView.setViewName("result");
    	
    	return modelAndView;
    }

	private String getPrediction(PredictionDto predictionDto) {
		
		String result=mlService.callMlModel(predictionDto);
		
    	Prediction prediction= new Prediction();
    	
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	Patient patient = patientService.getLoggedUser(username);
    	
    	prediction.setResult(result);
    	prediction.setPredictionDto(predictionDto);
    	prediction.setPatient(patient);
    	predictionService.savePrediction(prediction);
		return result;
	}
	
    @GetMapping("prediction-list")
    public ModelAndView getListOfPrediction() {
    	ModelAndView modelAndView=new ModelAndView();
    	List<Prediction> predictionList= patientService.getPredictionList(); 
    	modelAndView.getModelMap().addAttribute("predictionList", predictionList);
    	modelAndView.setViewName("list-prediction");
    	return modelAndView;
    }
    
    @GetMapping("survey")
    public ModelAndView survey() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("stages");
    	return modelAndView;
    }
    
    @GetMapping("drugs")
    public ModelAndView drugs() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("drugs");
    	return modelAndView;
	}
    @GetMapping("advices")
    public ModelAndView advices() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("advices");
    	return modelAndView;
	}
    
    
    
    
    
    
    
    
    
    
    
}
