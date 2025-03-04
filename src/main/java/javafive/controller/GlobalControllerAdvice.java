package javafive.controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.Cookie;

import org.springframework.web.bind.annotation.ControllerAdvice;

import javafive.entity.CartItem;
import javafive.entity.Category;
import javafive.entity.Product;
import javafive.service.CategoryService;
import javafive.service.CookieService;
import javafive.service.ProductService;
import javafive.service.SessionService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CookieService cookieService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private SessionService sessionService;
    
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
    @ModelAttribute("cartCount")
    public int getCartItemCount() {
        List<CartItem> cart = (List<CartItem>) sessionService.get("cart");
        if (cart == null) {
            return 0;
        }
        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
   
}
