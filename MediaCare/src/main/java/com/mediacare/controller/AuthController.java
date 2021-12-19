package com.mediacare.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mediacare.dto.NewUserForm;
import com.mediacare.service.AuthService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService; 
	
	@GetMapping("/register")
	public String signUp(Model theModel) {
		
		NewUserForm theNewUser=new NewUserForm(); 
		
		theModel.addAttribute("newUser", theNewUser);
		
		return "register";
	}

	@PostMapping("/processRegister")
	public String processSignUp(@ModelAttribute("newUser") @Valid NewUserForm theNewUser,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		authService.register(theNewUser);
		
		return "redirect:/";
	}
}
