package javafive.service;

import java.util.List;
import java.util.Optional;

import javafive.entity.OrderDetails;

public interface OrderDetailService {
	
	OrderDetails create(OrderDetails orderDetail);
	Optional<OrderDetails> findbyOrderId(Integer id);
}
