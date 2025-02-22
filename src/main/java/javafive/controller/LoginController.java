package javafive.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import javafive.entity.User;
import javafive.service.CookieService;
import javafive.service.SessionService;
import javafive.service.UserServie;

@Controller
public class LoginController {
	@Autowired
	CookieService cookieService;
	@Autowired
	UserServie userService;
	
	@Autowired
	SessionService sessionService;
	

	@RequestMapping("/cookie/login/form")
	public String loginForm( ) {

		return "/home/login";
	}
	@RequestMapping("/devshop/home/logout")
	public String logoutForm(HttpSession session) {
		sessionService.remove("currentUser");
		return "/home/login";
	}

	@RequestMapping("/cookie/login/check")
	public String loginCheck(HttpSession session, Model model, 
	                         @RequestParam("username") String username,
	                         @RequestParam("password") String password,
	                         @RequestParam(name = "remember", defaultValue = "false") boolean remember) {

	    Optional<User> user = userService.findById(username);
	    if (user.isEmpty()) {
	        model.addAttribute("msg", "Invalid username!");
	        return "/home/login";
	    } else if (!user.get().getPassword().equals(password)) {
	        model.addAttribute("msg", "Invalid password!");
	        return "/home/login";
	    } else {
	        model.addAttribute("msg", "Login successfully!");
	        sessionService.set("currentUser", user.get()); 

	        if (remember) {
	            cookieService.create("un", username, 30 * 24 * 60 * 60);
	            cookieService.create("pw", password, 30 * 24 * 60 * 60);
	        } else {
	            cookieService.delete("un");
	            cookieService.delete("pw");
	        }

	        String role = user.get().getAuthorities().stream()
	                 .map(auth -> auth.getRole().getId()) 
	                 .findFirst()
	                 .orElse("CUSTOMER"); 

	        if ("ADMIN".equalsIgnoreCase(role)) {
	            return "redirect:/devshop/admin/index"; 
	        } else {
	            return "redirect:/devshop/page/index"; 
	        }
	    }
	}

	
}
