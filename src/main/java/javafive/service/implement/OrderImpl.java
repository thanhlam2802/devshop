package javafive.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.OrderDAO;
import javafive.entity.OrderStatus;
import javafive.entity.Orders;
import javafive.entity.PaymentMethod;
import javafive.service.OrderService;
@Service
public class OrderImpl implements OrderService{

	@Autowired
	OrderDAO orderdao;
	@Override
	public List<Orders> getAllOrder() {
		
		return orderdao.findAll();
	}
	@Override
	public Orders saveOrUpdateOrder(Orders order) {
		
		return orderdao.save(order);
	}
	@Override
	public Optional<Orders> getOrderById(Long id) {
		
		return orderdao.findById(id);
	}
	@Override
	public List<Orders> getCompletedOrders() {
		// TODO Auto-generated method stub
		return orderdao.getCompletedOrders();
	}
	@Override
	public List<Orders> getCompletedOrdersByPayment(PaymentMethod paymentMethod) {
		// TODO Auto-generated method stub
		return orderdao.getCompletedOrdersByPayment(paymentMethod);
	}
	@Override
	public List<Orders> getOrdersByStatus(OrderStatus status) {
		
		return orderdao.getOrdersByStatus(status);
	}
	

}
