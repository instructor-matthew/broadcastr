package com.matthew.fakeInsta.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matthew.fakeInsta.models.User;
import com.matthew.fakeInsta.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository uRepo;
	
	
	public List<User> getAllUsers(){
		return this.uRepo.findAll();
	}
	
	public User getOneUser(Long id) {
		return this.uRepo.findById(id).orElse(null);
	}
	
	public User registerUser(User user) {
		// Generate a Hash
		String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		// Set the hashed password on the user's password field
		user.setPassword(hash);
		// Save the new user password to the database
		return this.uRepo.save(user);
	}
	
	public boolean authenicateUser(String email, String password) {
		// Make sure the user logging in is who they say they are
		// query for the user by email
		User user = this.uRepo.findByEmail(email);
		if(user == null) {
			return false;
		}
		// Step 2: check provided email against email in the datbaase
		return BCrypt.checkpw(password, user.getPassword());
	}
	
	public User getByEmail(String email) {
		return this.uRepo.findByEmail(email);
	}
}
