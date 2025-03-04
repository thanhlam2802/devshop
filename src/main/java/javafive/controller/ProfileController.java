package javafive.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import javafive.entity.User;
import javafive.service.FileService;
import javafive.service.SessionService;
import javafive.service.UserServie;

@Controller
public class ProfileController {
	@Autowired
	UserServie userService;
	@Autowired
	FileService fileService;
	@Autowired
	SessionService sessionService;
	@GetMapping("/devshop/home/profile")
	public String showProfileForm(Model model) {
	    User currentUser = (User) sessionService.get("currentUser");
	    if (currentUser == null) {
	        return "redirect:/login";
	    }
	    System.out.println("Current user: " + currentUser);
	    System.out.println("User photo: " + currentUser.getPhoto());
	    model.addAttribute("user", currentUser);
	    return "home/profile";
	}

	
	

	@PostMapping("/devshop/profile/update")
	public String updateProfile(@ModelAttribute @Valid User user, 
	                            BindingResult result,
	                            @RequestParam("photo") MultipartFile photoFile, 
	                            HttpSession session, 
	                            Model model) {
	    User currentUser = (User) session.getAttribute("currentUser");

	    if (result.hasErrors()) {
	        model.addAttribute("error", "Dữ liệu nhập vào không hợp lệ!");
	        return "/home/profile";
	    }

	   
	    Optional<User> existingEmail = userService.findByEmail(user.getEmail());
	    if (existingEmail.isPresent() && !existingEmail.get().getUsername().equals(currentUser.getUsername())) {
	        model.addAttribute("error", "Email đã tồn tại trong hệ thống!");
	        return "/home/profile";
	    }

	    currentUser.setFullname(user.getFullname());
	    currentUser.setEmail(user.getEmail());
	    currentUser.setMobile(user.getMobile());

	    if (!photoFile.isEmpty()) {
	        File savedFile = fileService.save(photoFile, "avatars");
	        currentUser.setPhoto(savedFile.getName());
	    }

	    userService.update(currentUser);
	    session.setAttribute("currentUser", currentUser);
	    model.addAttribute("message", "Cập nhật thành công!");

	    return "redirect:/devshop/home/profile";
	}
    
}
