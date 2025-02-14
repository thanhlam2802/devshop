package javafive.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import javafive.entity.User;
import javafive.service.UserServie;

@Controller
public class adminController {
	@Autowired
	UserServie UserService;
	@RequestMapping("/devshop/admin/index")
    public String home(Model model, HttpServletRequest request) {
        
        return "/admin/index";
    }
	@RequestMapping("/devshop/admin/customer")
    public String customer(Model model, HttpServletRequest request) {
		List<User> list = UserService.findAll();
        model.addAttribute("users",list);
        return "/admin/customer";
    }
	@RequestMapping("/devshop/admin/product")
    public String product(Model model, HttpServletRequest request) {
        
        return "/admin/products";
    }
	@RequestMapping("/devshop/admin/category")
    public String category(Model model, HttpServletRequest request) {
        
        return "/admin/category";
    }
	@RequestMapping("/devshop/admin/order")
    public String order(Model model, HttpServletRequest request) {
        
        return "/admin/order";
    }


    
}
