package javafive.service;

import java.util.List;

import javafive.entity.Image;

public interface ImageService {
	
	List<Image> getImagebyProductIdAngColorId(Integer productid, Integer colorId );
}
