package com.mediacare.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mediacare.mvc.dto.PredictionDto;
import com.mediacare.mvc.service.MlService;

@Controller
@RequestMapping("/")
public class PredictionController {

	@Autowired
	private MlService mlService;
	
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
    	predictionDto.setAge(37);
    	predictionDto.setSex(1);
    	String result=mlService.callMlModel(predictionDto);
    	
    	modelAndView.getModelMap().addAttribute("result", result);
    	
    	modelAndView.setViewName("result");
    	
    	return modelAndView;
    }
    @GetMapping("myPredictions")
    public ModelAndView getListOfPrediction() {
    	ModelAndView modelAndView=new ModelAndView();
    	
//    	List<Prediction> predictions= 
//    	
//    	modelAndView.getModelMap().addAttribute("listPrediction", predictions);
    	
    	modelAndView.setViewName("list-prediction");
    	
    	return modelAndView;
    }
}
