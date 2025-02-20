package javafive.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import javafive.entity.Product;

import javafive.entity.ProductVariant;
import javafive.entity.Category;
import javafive.entity.Color;
import javafive.entity.Image;
import javafive.service.CategoryService;
import javafive.service.CookieService;
import javafive.service.ImageService;
import javafive.service.ProductService;
import javafive.service.ProductVariantService;

@Controller
public class PageController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductVariantService productVariantService;
	@Autowired
	ImageService imageService;
	@Autowired 
	CookieService cookieService;
	
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
	 @RequestMapping("/devshop/product/{product_id}")
	 public String pageDetail(Model model, @PathVariable("product_id") Integer productId,
			 								@RequestParam (value ="color",required = false) Integer index) {
		 Product product = productService.getProductById(productId).orElse(null);
		 if (index == null && product != null && !product.getProductColors().isEmpty()) {
		        index = product.getProductColors().get(0).getColor().getColor_id();
		    }
	
		
		 String producIdV = cookieService.getValue("productIdViewed").replace("~", ",");
		 List<String> productList = new ArrayList<>(Arrays.asList(producIdV.split(",")));
		    if (!productList.contains(String.valueOf(productId))) {
		        productList.add(0, String.valueOf(productId)); 
		        
		    }
		    String newCookieValue = String.join("~", productList);
		    cookieService.create("productIdViewed", newCookieValue, 24*60*60);
		 
		 
		 List<Image> listImage =  imageService.getImagebyProductIdAngColorId(productId, index);
	     List<ProductVariant> list = productVariantService.getVariantsByProductId(productId);
	     model.addAttribute("listImage", listImage);
	     model.addAttribute("product", product);
	     model.addAttribute("listProductVariant", list);
	     model.addAttribute("selectedColor", index);
	     return "/home/pagedetail";
	 }

}
