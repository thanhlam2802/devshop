package javafive.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import javafive.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer>{
 

	@Query("""
	        SELECT p FROM Product p
	        WHERE p.category.category_id = :categoryId
	        OR p.category IN (
	            SELECT c FROM Category c WHERE c.parentCategory.id = :categoryId
	        )
	    """)
	    List<Product> findByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

}
