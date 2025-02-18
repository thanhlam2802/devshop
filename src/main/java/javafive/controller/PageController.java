package javafive.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import javafive.entity.Product;
import javafive.entity.Category;
import javafive.service.CategoryService;
import javafive.service.ProductService;

@Controller
public class PageController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	 @RequestMapping("/devshop/home/{product_id}")
	    public String home(Model model, @PathVariable("product_id") Integer productId,
	    		@RequestParam(value = "sort", required = false, defaultValue = "default") String sort) {

	        List<Product> listbyCategory = productService.getAllProductsByCategory(productId,sort);
	        Optional<Category> category = categoryService.getCategoryById(productId);
	        if (category.isPresent()) {
	            model.addAttribute("category", category.get());
	        }
	        model.addAttribute("selectedSort", sort);
	        model.addAttribute("listbyCategory", listbyCategory);
	        System.out.print(sort);
	        model.addAttribute("category",category);
	        return "/home/page";
	    }
}
