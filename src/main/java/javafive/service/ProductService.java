package javafive.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javafive.entity.Category;
import javafive.entity.Product;


@Service
public interface ProductService {

	List<Product> getAllProducts();
	
	Page<Product> getAllProductsPageAndSort(int number);
	
	List<Product> getAllProductsByCategory(Integer categoryId, String sortColumn);


	Optional<Product> getProductById(Integer id);

	List<Product> getProductByIdList(List<Integer>  id);
    Product saveOrUpdateProduct(Product product);

 

    List<Product> searchProductsByName(String name);
}
