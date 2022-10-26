package com.matthew.fakeInsta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matthew.fakeInsta.models.Picture;
import com.matthew.fakeInsta.models.User;
import com.matthew.fakeInsta.repositories.PictureRepository;

@Service
public class PictureService {
	@Autowired
	private PictureRepository pRepo;
	
	
	// Creaete Image Object, Save To Database
	public void uploadPic(User user, String url, String description) {
		Picture newPic = new Picture(url, description, user);
		this.pRepo.save(newPic);
	}
	
	public List<Picture> userPictures(User user){
		return this.pRepo.findAllByUser(user);
	}
}
