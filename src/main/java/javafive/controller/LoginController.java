package javafive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import javafive.service.SessionService;

@Controller
public class LoginController {

    @Autowired
    SessionService sessionService;

    // Hiển thị form login
    @GetMapping("/cookie/login/form")
    public String loginForm() {
        return "/home/login"; 
    }

    // Xử lý logout thủ công (nếu cần làm thêm gì ngoài invalidate session)
    @GetMapping("/devshop/home/logout")
    public String logoutForm(HttpSession session) {
        sessionService.remove("currentUser");
        session.removeAttribute("cart");
        sessionService.remove("failedAttempts");
        return "redirect:/cookie/login/form?logout"; 
    }
}
