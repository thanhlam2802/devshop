package javafive.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.ProductSize;

public interface ProductSizeDAO extends JpaRepository<ProductSize, Integer>{

}
