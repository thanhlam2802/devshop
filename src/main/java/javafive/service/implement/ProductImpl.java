package javafive.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.CategoryDAO;
import javafive.dao.ProductDAO;
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
		// TODO Auto-generated method stub
		return null;
	}

}
