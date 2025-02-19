package javafive.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javafive.service.CookieService;





@Controller
public class LoginController {
	@Autowired 
	CookieService cookieService;
	
	@RequestMapping("/cookie/login/form")
	public String loginForm() {
		return "/home/login";
	}
	@RequestMapping("/cookie/login/check")
	public String loginCheck(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(name="remember", defaultValue = "false") boolean remember) {
		if(!username.equalsIgnoreCase("poly")) {
			model.addAttribute("msg", "Invalid username!");
		} else if(!password.equals("123")) {
			model.addAttribute("msg", "Invalid password!");
		} else {
			model.addAttribute("msg", "Login successfully!");
			if(remember) { 
				cookieService.create("un", username, 30 * 24 * 60 * 60);
				cookieService.create("pw", password, 30 * 24 * 60 * 60);
			} else { // xóa cookie
				cookieService.delete("un");
				cookieService.delete("pw");
			}
		}
		return "/home/login";
	}
}
