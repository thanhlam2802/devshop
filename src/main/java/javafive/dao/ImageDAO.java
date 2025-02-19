package javafive.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javafive.entity.Image;

public interface ImageDAO extends JpaRepository<Image, Integer>{
	
	@Query("SELECT i FROM Image i WHERE i.product.id = :productId AND i.color.id = :colorId")
	List<Image> findByProductIdAndColorId(@Param("productId") Integer productId, @Param("colorId") Integer colorId);

}
