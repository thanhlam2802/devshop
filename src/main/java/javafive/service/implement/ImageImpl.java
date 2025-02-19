package javafive.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javafive.dao.ImageDAO;
import javafive.entity.Image;
import javafive.service.ImageService;

@Service
public class ImageImpl implements ImageService{

	@Autowired
	 ImageDAO daoci;
	@Override
	public List<Image> getImagebyProductIdAngColorId(Integer productid, Integer colorId) {
		
		return daoci.findByProductIdAndColorId(productid,colorId);
	}

}
