package javafive.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javafive.dao.CategoryDAO;
import javafive.dao.ProductDAO;
import javafive.entity.Category;
import javafive.entity.Product;
import javafive.service.ProductService;

@Service
public class ProductImpl implements ProductService{

	 @Autowired
	 ProductDAO daop;
	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return daop.findAll();
	}

	@Override
	public Product getProductById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product saveOrUpdateProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> searchProductsByName(String name) {
		
		return null;
	}

	@Override
	public List<Product> getAllProductsByCategory(Integer categoryId, String sortColumn){	
		Sort sort = Sort.unsorted();
        if ("newest".equals(sortColumn)) {
            sort = Sort.by("createdAt").descending();
        } else if ("low-price".equals(sortColumn)) {
            sort = Sort.by("price").ascending();
        } else if ("high-price".equals(sortColumn)) {
            sort = Sort.by("price").descending();
        }

        Pageable pageable = PageRequest.of(0, 100, sort);
        return daop.findByCategoryId(categoryId, pageable);
	}



}
