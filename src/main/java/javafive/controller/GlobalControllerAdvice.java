package javafive.controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.Cookie;

import org.springframework.web.bind.annotation.ControllerAdvice;

import javafive.entity.Category;
import javafive.entity.Product;
import javafive.service.CategoryService;
import javafive.service.CookieService;
import javafive.service.ProductService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CookieService cookieService;

    @Autowired
    private ProductService productService;
    
    @ModelAttribute("categories")
    public List<Category> loadCategories() {
        return categoryService.getParentCategories();
    }
    
    @ModelAttribute("productViewed")
    public List<Product> loadProducts(){
    	Cookie  cookie =  cookieService.get("productIdViewed");
    	String productId = (cookie != null) ? cookieService.getValue("productIdViewed"):"";
    	List<Product> list = new ArrayList<>();
    	 if (!productId.isEmpty()) {
    	        List<Integer> productIds = Arrays.stream(productId.split("~"))
    	                                          .map(Integer::parseInt)
    	                                          .collect(Collectors.toList());
    	        list = productService.getProductByIdList( productIds);
    	    }

    	return list;
    	
    }
   
}
