package javafive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import javafive.entity.Category;
import javafive.entity.Product;


@Service
public interface ProductService {

	List<Product> getAllProducts();
	
	List<Product> getAllProductsByCategory(Integer categoryId, String sortColumn);


	Optional<Product> getProductById(Integer id);


    Product saveOrUpdateProduct(Product product);

 

    List<Product> searchProductsByName(String name);
}
