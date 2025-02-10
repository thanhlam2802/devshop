package javafive.service;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
public interface CartService {

	void add(String id);

	void remove(String id);

	void updateQuantity(String id, int newQuantity);

	void clear();

	default int getTotalQuantity() {
		return this.getItems().stream().mapToInt(item -> item.getQuantity()).sum();
	}

	default double getTotalAmount() {
		return this.getItems().stream().mapToDouble(item -> item.getAmount()).sum();
	}

	Collection<CartItem> getItems();

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Data
	public static class CartItem {
		String id;
		String name;
		double price;
		int quantity;

		public double getAmount() {
			return this.price * this.quantity;
		}
	}
}
