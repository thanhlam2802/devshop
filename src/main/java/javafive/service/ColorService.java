package javafive.service;

import java.util.Optional;

import javafive.entity.Color;

public interface ColorService {

	Optional<Color> getColorById(Integer colorid);
}
