package javafive.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.OrderDetailDAO;
import javafive.entity.OrderDetails;
import javafive.service.OrderDetailService;


@Service
public class  OrderDetailImpl implements OrderDetailService {
	@Autowired
	OrderDetailDAO orderDetaildao;
	
	@Override
	public OrderDetails create(OrderDetails orderDetail) {
		// TODO Auto-generated method stub
		return orderDetaildao.save(orderDetail);
	}

	@Override
	public Optional<OrderDetails> findbyOrderId(Integer id) {
		// TODO Auto-generated method stub
		return orderDetaildao.findById(id);
	}

}
