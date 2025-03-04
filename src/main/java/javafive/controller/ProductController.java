package javafive.controller;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import javafive.entity.Product;
import javafive.service.CategoryService;
import javafive.service.FileService;
import javafive.service.ProductService;

@Controller
@RequestMapping("/devshop/admin/product")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
CategoryService categoryServicevice;
	@GetMapping
	public String listProducts(Model model, @RequestParam(defaultValue = "0") int page) {
	    Page<Product> productPage = productService.getAllProductsPageAndSort(page);
	    
	    model.addAttribute("products", productPage.getContent()); 
	    model.addAttribute("currentPage", productPage.getNumber()); 
	    model.addAttribute("totalPages", productPage.getTotalPages()); 

	    return "admin/products";
	}


	 @GetMapping("/create")
	    public String showCreateForm(Model model) {
	        model.addAttribute("product", new Product());
	        model.addAttribute("categories", categoryServicevice.getAllCategories()); 
	        return "/admin/product-form";
	    }

	
	 @Autowired
	 FileService fileService;

	 @PostMapping("/save")
	 public String saveProduct(@ModelAttribute("product") Product product, 
	                           @RequestParam("imageFile") MultipartFile imageFile) {
	     try {
	         if (!imageFile.isEmpty()) {
	             File savedFile = fileService.save(imageFile, "products");
	             if (savedFile != null) {
	                 product.setMainImage("/photo/" + savedFile.getName());
	             }
	         }
	         productService.saveOrUpdateProduct(product);
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return "redirect:/devshop/admin/product";
	 }
	    @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable Integer id, Model model) {
	        Optional<Product> product = productService.getProductById(id);
	        if (product == null) {
	            return "redirect:/devshop/admin/product";
	        }
	        model.addAttribute("product", product);
	        model.addAttribute("categories", categoryServicevice.getAllCategories()); 
	        return "admin/product-form";
	    }

	    @GetMapping("/delete/{id}")
	    public String deleteProduct(@PathVariable Integer id) {
	        productService.deleteProductById(id);
	        return "redirect:/devshop/admin/product";
	    }
}
