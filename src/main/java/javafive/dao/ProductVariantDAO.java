package javafive.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.ProductVariant;

public interface ProductVariantDAO extends JpaRepository<ProductVariant, Integer>{

}
