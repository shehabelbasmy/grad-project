	package com.mediacare.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mediacare.mvc.dto.PredictionDto;

@Controller
@RequestMapping("/")
public class PatientController {

    @GetMapping("profile")
    public ModelAndView profile(){
        ModelAndView modelAndView  = new ModelAndView();
        modelAndView.getModelMap().addAttribute("profile","From Profile Controller");
        modelAndView.setViewName("profile");
        return modelAndView;
    }
    @GetMapping("home")
    public String home(){

        return "home";
    }
    @GetMapping("makePrediction")
    public ModelAndView makePredction() {
    	ModelAndView modelAndView=new ModelAndView();
    	
    	modelAndView.getModelMap().addAttribute("predictionTemp", new PredictionDto());
    	
    	modelAndView.setViewName("make-prediction");
    	
    	return modelAndView;
    }
    
    @GetMapping("myPrediction")
    public ModelAndView getListOfPrediction() {
    	ModelAndView modelAndView=new ModelAndView();
    	
//    	List<Prediction> predictions= 
//    	
//    	modelAndView.getModelMap().addAttribute("listPrediction", predictions);
    	
    	modelAndView.setViewName("list-prediction");
    	
    	return modelAndView;
    }
}
