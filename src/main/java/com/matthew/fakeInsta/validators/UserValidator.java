package com.matthew.fakeInsta.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.matthew.fakeInsta.models.User;
import com.matthew.fakeInsta.repositories.UserRepository;


@Component
public class UserValidator {
	@Autowired
	private UserRepository uRepo;
	
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("password", "Match", "Passwords do not match!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}		
		
		if(this.uRepo.existsByEmail(user.getEmail())) {
			errors.rejectValue("email", "Unique", "Sorry... That email has already been registered. Try a different one NOW!");
		}
		
		if(user.getFirstName().equals("Matt")) {
			errors.rejectValue("firstName", "noMattsAllowed", "Sorry, we're not accepting matts at this time");
		}
	}
}
