package javafive.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javafive.entity.Category;
import javafive.service.CategoryService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CategoryService categoryService;
    
    @ModelAttribute("categories")
    public List<Category> loadCategories() {
        return categoryService.getParentCategories();
    }

   
}
