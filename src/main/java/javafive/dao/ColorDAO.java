package javafive.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.Color;

public interface ColorDAO extends JpaRepository<Color, Integer>{

}
