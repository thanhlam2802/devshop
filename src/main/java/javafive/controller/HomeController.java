package javafive.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javafive.entity.CartItem;
import javafive.entity.Product;
import javafive.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
    
	@Autowired
	ProductService productService;
    @RequestMapping("/devshop/page/index")
    public String home(Model model, HttpServletRequest request) {
        
        return "forward:/devshop/page/0";
    }
    @RequestMapping("/devshop/page/{number}")
    public String homepage(Model model, HttpServletRequest request,@PathVariable("number") Integer number) {
        showBanner(request, model);
        Page <Product> listall = productService.getAllProductsPageAndSort(number);
        model.addAttribute("listallproduct", listall);
        return "/home/index";
    }
    
    
    @RequestMapping("/devshop/home/banchay")
    public String bestSellers(Model model, HttpServletRequest request) {
        showBanner(request, model);
        return "/home/banchay";
    }

    @RequestMapping("/devshop/home/sanphammoi")
    public String newProducts(Model model, HttpServletRequest request) {
        showBanner(request, model);
        return "/home/sanphammoi";
    }

 

    @RequestMapping("/devshop/home/tin")
    public String news(Model model) {
        return "/home/pageviewed";
    }
    @RequestMapping("/devshop/home/login")
    public String login(Model model) {
        return "/home/login";
    }
    
    
    @GetMapping("/cart/show")
    public String showCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        model.addAttribute("cart", cart);
        if (cart.isEmpty()) {
            return "/home/cartnull";
        }
        
        return "/home/cart";
    }


   
    private void showBanner(HttpServletRequest request, Model model) {
        String uri = request.getRequestURI();
        boolean showBanner = uri.matches("/devshop/page(/\\d+)?") || 
                             uri.equals("/devshop/home/banchay") || 
                             uri.equals("/devshop/home/sanphammoi");
        model.addAttribute("showBanner", showBanner);
    }
}
