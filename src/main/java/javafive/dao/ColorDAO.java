package javafive.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.Color;

public interface ColorDAO extends JpaRepository<Color, Integer>{
	Optional<Color> findByName(String name);
}
