package javafive.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java5.hoc.Const;
import javafive.entity.User;
import javafive.service.CookieService;
import javafive.service.SessionService;
import javafive.service.UserServie;

@Controller
public class LoginController {
	private static final int MAX_FAILED_ATTEMPTS = 5;
	
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
		 sessionService.remove("failedAttempts");
		return "/home/login";
	}

	@PostMapping(value = "/cookie/login/check")
	public String loginCheck(HttpSession session, Model model, 
	                         @RequestParam("identifier") String identifier,
	                         @RequestParam("password") String password,
	                         @RequestParam(name = "remember", defaultValue = "false") boolean remember) {

	    Optional<User> user = userService.findByUsernameOrEmail(identifier);
	    if (user.isEmpty()) {
	        model.addAttribute("msg", "Invalid username or email!");
	        return "/home/login";
	    } 
	    
	    Integer failedAttempts = (Integer) sessionService.get("failedAttempts");
        failedAttempts = (failedAttempts == null) ? 0 : failedAttempts;

       
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            model.addAttribute("msg", "Tài khoản của bạn đã bị khóa do nhập sai quá nhiều lần!");
            return "/home/login";
        }
	    if (!user.get().getPassword().equalsIgnoreCase(password)) {
	        model.addAttribute("msg", "Invalid password!");
	        return handleFailedLogin(session, model, identifier);
	       
	    }
	   

	    sessionService.set("currentUser", user.get()); 
	    sessionService.remove("failedAttempts");
	    model.addAttribute("msg", "Login successfully!");

	  
	    if (remember) {
	        cookieService.create("un", identifier, 30 * 24 * 60 * 60);
	        cookieService.create("pw", password, 30 * 24 * 60 * 60);
	    } else {
	        cookieService.delete("un");
	        cookieService.delete("pw");
	    }

	  
	    String prevPage = (String) session.getAttribute(Const.SECAURED);
	    session.removeAttribute(Const.SECAURED); 

	    if (prevPage != null && !prevPage.contains("/login")) { 
	        return "redirect:" + prevPage;
	    }

	    String role = Optional.ofNullable(user.get().getAuthorities())
	            .orElse(Collections.emptyList())
	            .stream()
	            .map(auth -> auth.getRole().getId())
	            .findFirst()
	            .orElse("CUSTOMER");
 

	    if ("ADMIN".equalsIgnoreCase(role)) {
	        return "redirect:/devshop/admin/index"; 
	    } else {
	        return "redirect:/devshop/page/index"; 
	    }
	}
	private String handleFailedLogin(HttpSession session, Model model, String identifier) {
        Integer failedAttempts = (Integer) sessionService.get("failedAttempts");
        failedAttempts = (failedAttempts == null) ? 1 : failedAttempts + 1;

        sessionService.set("failedAttempts", failedAttempts);
       System.out.print(failedAttempts);
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            model.addAttribute("msg", "Tài khoản của bạn đã bị khóa do nhập sai quá nhiều lần!");
        }
        return "/home/login";
    }

	
}
