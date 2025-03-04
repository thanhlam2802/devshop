package javafive.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.ProductDAO;
import javafive.dao.ProductVariantDAO;
import javafive.entity.Color;
import javafive.entity.ProductVariant;
import javafive.service.ProductVariantService;
@Service
public class ProductVariantImpl implements ProductVariantService{
	 @Autowired
	 ProductVariantDAO daopv;
	@Override
	public List<ProductVariant> getAllVariants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductVariant> getVariantsByProductId(Integer productId) {
		
		return daopv.findByProductId(productId);
	}

	@Override
	public ProductVariant getVariantById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductVariant saveVariant(ProductVariant productVariant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVariant(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getFirstAvailableColor(Integer productId) {
		// TODO Auto-generated method stub
		 return daopv.findFirstAvailableColorByProductId(productId).orElse(null);
	}

	@Override
	public Optional<ProductVariant> findVariant(Integer productId, Integer color, Integer size) {
	    return  daopv.findVariant(productId, color, size);
	}


}
