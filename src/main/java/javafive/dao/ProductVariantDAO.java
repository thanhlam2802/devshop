package javafive.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javafive.entity.ProductVariant;
import javafive.entity.Color;

public interface ProductVariantDAO extends JpaRepository<ProductVariant, Integer>{
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.product.product_id = :productId")
	List<ProductVariant> findByProductId(@Param("productId") Integer productId);
	
	@Query("SELECT pv.color FROM ProductVariant pv " +
	           "WHERE pv.product.product_id = :productId " +
	           "AND pv.quantity > 0 " +
	           "ORDER BY pv.color.id ASC LIMIT 1")
	    Optional<Color> findFirstAvailableColorByProductId(@Param("productId") Integer productId);

}
