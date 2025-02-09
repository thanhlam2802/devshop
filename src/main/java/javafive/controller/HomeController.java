package javafive.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/devshop/home/index")
    public String home(Model model, HttpServletRequest request) {
        showBanner(request, model);
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

    @RequestMapping("/devshop/home/nam")
    public String men(Model model) {
        return "/home/nam";
    }

    @RequestMapping("/devshop/home/nu")
    public String women(Model model) {
        return "/home/nu";
    }

    @RequestMapping("/devshop/home/tinhot")
    public String news(Model model) {
        return "/home/tin";
    }
    @RequestMapping("/devshop/home/login")
    public String login(Model model) {
        return "/home/login";
    }

   
    private void showBanner(HttpServletRequest request, Model model) {
        String uri = request.getRequestURI();
        boolean showBanner = uri.equals("/devshop/home/index") || 
                             uri.equals("/devshop/home/banchay") || 
                             uri.equals("/devshop/home/sanphammoi");
        model.addAttribute("showBanner", showBanner);
    }
}
