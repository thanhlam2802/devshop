package javafive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import javafive.entity.OrderStatus;
import javafive.entity.Orders;
import javafive.entity.PaymentMethod;


public interface OrderService {
	List<Orders> getAllOrder();
	Orders saveOrUpdateOrder(Orders order);
	Optional<Orders> getOrderById(Long id);
	  List<Orders> getCompletedOrders();
	  List<Orders> getCompletedOrdersByPayment(PaymentMethod paymentMethod);
	    List<Orders> getOrdersByStatus( OrderStatus status);

}
