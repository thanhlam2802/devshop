package javafive.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.Size;

public interface SizeDAO extends JpaRepository<Size, Integer>{

	Optional<Size> findBySize(String name);
}
