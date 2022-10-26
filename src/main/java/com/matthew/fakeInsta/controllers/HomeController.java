package com.matthew.fakeInsta.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matthew.fakeInsta.models.User;
import com.matthew.fakeInsta.services.PictureService;
import com.matthew.fakeInsta.services.UserService;


@Controller
@RequestMapping("/dashboard")
public class HomeController {
	@Autowired
	private UserService uService;
	@Autowired
	private PictureService pService;
	
	private static String UPLOADED_FOLDER = "src/main/resources/static/img/";
	
	@GetMapping("")
	public String index(HttpSession session, Model viewModel) {
		viewModel.addAttribute("currentUser", this.uService.getOneUser((Long)session.getAttribute("user___id")));
		return "dashboard.jsp";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam("pic") MultipartFile file, @RequestParam("description") String desc, HttpSession session, RedirectAttributes redirectAttr) {
		User user = this.uService.getOneUser((Long)session.getAttribute("user___id"));
		// Save the uploaded file to our static folder
		System.out.println(file.getContentType());
		
		if(!file.getContentType().startsWith("image/")) {
			redirectAttr.addFlashAttribute("message", "invalid format");
			return "redirect:/dashboard";
		}
		if(file.isEmpty()) {
			redirectAttr.addFlashAttribute("message", "Field cannot be empty!!!!!");
			return "redirect:/dashboard";
		}
		try {
			// get the file and throw it into the server foldre
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			// Get URL of the file we just uploaded
			String url = "/img/" + file.getOriginalFilename();
			this.pService.uploadPic(user, url, desc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/dashboard";
	}
}

