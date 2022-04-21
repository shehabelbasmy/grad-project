package com.mediacare.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AdminController {

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
}
