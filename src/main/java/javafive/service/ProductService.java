package javafive.service;

import java.util.List;

import org.springframework.stereotype.Service;

import javafive.entity.Product;

@Service
public interface ProductService {

	List<Product> getAllProducts();


    Product getProductById(Integer id);


    Product saveOrUpdateProduct(Product product);

  
    void deleteProductById(Integer id);


    List<Product> searchProductsByName(String name);
}
