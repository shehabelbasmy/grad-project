package com.mediacare.mvc.controller;

import javax.validation.Valid;

import com.mediacare.mvc.dto.NewUserDto;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mediacare.service.RestAuthService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AuthController {

	private final RestAuthService authService; 
	
	@GetMapping("/signup")
	public String signUp(Model theModel) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:/admin/profile";
		}
		
		NewUserDto theNewUser=new NewUserDto();
		
		theModel.addAttribute("newUser", theNewUser);
		
		return "signup";
	}

	@PostMapping("/processRegister")
	public String processSignUp(
			@ModelAttribute("newUser") 
			@Valid NewUserDto theNewUser,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "signup";
		}
		
		authService.register(theNewUser);
		
		return "redirect:/";
	}
	@GetMapping("/login")
	public String login(){
		
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!	(authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:/admin/profile";
		}
		return"login";
	}
	
	@GetMapping("/test")
	@Secured("PATIENT")
	public String home(){
		return "home";
	}


}