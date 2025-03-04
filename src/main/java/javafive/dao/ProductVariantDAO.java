package javafive.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javafive.entity.ProductVariant;
import javafive.entity.Color;
@Repository
public interface ProductVariantDAO extends JpaRepository<ProductVariant, Integer>{
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.product.product_id = :productId")
	List<ProductVariant> findByProductId(@Param("productId") Integer productId);
	
	@Query("SELECT pv.color FROM ProductVariant pv " +
	           "WHERE pv.product.product_id = :productId " +
	           "AND pv.quantity > 0 " +
	           "ORDER BY pv.color.id ASC LIMIT 1")
	    Optional<Color> findFirstAvailableColorByProductId(@Param("productId") Integer productId);

	
	
	
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.product.id = :productId AND pv.color.id = :colorId AND pv.size.id = :sizeId")
	Optional<ProductVariant> findVariant(@Param("productId") Integer productId, 
	                                     @Param("colorId") Integer colorId, 
	                                     @Param("sizeId") Integer sizeId);

	

}
