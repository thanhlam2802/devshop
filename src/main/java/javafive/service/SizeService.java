package javafive.service;

import java.util.Optional;

import javafive.entity.Size;

public interface SizeService {

	 Optional<Size> findByName(String name) ;
      
    
}
