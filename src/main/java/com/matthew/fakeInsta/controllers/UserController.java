package com.matthew.fakeInsta.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matthew.fakeInsta.models.User;
import com.matthew.fakeInsta.services.UserService;
import com.matthew.fakeInsta.validators.UserValidator;

@Controller
public class UserController {
	@Autowired
	private UserService uService;
	@Autowired
	private UserValidator validator;
	
	@GetMapping("/")
	public String login(@ModelAttribute("user") User user) {
		return "index.jsp";
	}
	
	@PostMapping("/login")
	public String loggingIn(@RequestParam("loginemail") String email, @RequestParam("loginpassword") String password, HttpSession session, RedirectAttributes redirectAttr) {
		if(!this.uService.authenicateUser(email, password)) {
			redirectAttr.addFlashAttribute("error", "Invalid Credentials");
			return "redirect:/";
		}		

		User user = this.uService.getByEmail(email);
		session.setAttribute("user___id", user.getId());
		return "redirect:/dashboard";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		validator.validate(user, result);
		if(result.hasErrors()) {
			return "index.jsp";
		} 
		User newUser = this.uService.registerUser(user);
		session.setAttribute("user___id", newUser.getId());
		return "redirect:/dashboard";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttr) {
		redirectAttr.addFlashAttribute("message", "You have been successfully logged out!");
		session.invalidate();
		return "redirect:/";
	}
}
