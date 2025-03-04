package javafive.service;

import java.util.Optional;

import javafive.entity.Color;
import javafive.entity.Size;

public interface ColorService {

	Optional<Color> getColorById(Integer colorid);
	
	Optional<Color> findByName(String name);
}
