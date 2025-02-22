package javafive.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItem {
	private String userId;
	private Integer producID;
	private String productName;
	private String color;
	private String size;
	private Double price;
	private Integer quantity;
	private String image;
	

}
