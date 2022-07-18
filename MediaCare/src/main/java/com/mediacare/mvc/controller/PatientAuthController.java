package com.mediacare.mvc.controller;

import com.mediacare.mvc.dto.NewUserDto;
import com.mediacare.mvc.service.MvcAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PatientAuthController {

	private final MvcAuthService authService;
	
	@GetMapping("/signup")
	public String signUp(Model theModel) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		theModel.addAttribute("newUser", new NewUserDto());
		
		return "signup";
	}

	@PostMapping("/processRegister")
	public String processSignUp(@ModelAttribute("newUser") @Valid NewUserDto theNewUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()){

			return "signup";
		}
		authService.registerNewAdmin(theNewUser);
		
		return "redirect:/";
	}
	@GetMapping("/login")
	public String login(){
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		return "login2";
	}


}