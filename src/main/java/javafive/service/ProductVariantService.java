package javafive.service;

import java.util.List;
import java.util.Optional;

import javafive.entity.Color;
import javafive.entity.ProductVariant;

public interface ProductVariantService {
	 
		    List<ProductVariant> getAllVariants();
		    List<ProductVariant> getVariantsByProductId(Integer productId);
		    ProductVariant getVariantById(Integer id);
		    ProductVariant saveVariant(ProductVariant productVariant);
		    void deleteVariant(Integer id);
		    Color getFirstAvailableColor(Integer productId);

		    Optional<ProductVariant> findVariant(Integer productId, Integer color, Integer size);
}
