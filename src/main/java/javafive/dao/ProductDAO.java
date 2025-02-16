package javafive.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer>{

}
