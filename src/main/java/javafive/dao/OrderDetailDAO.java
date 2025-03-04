package javafive.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javafive.entity.OrderDetails;

public interface OrderDetailDAO extends JpaRepository<OrderDetails, Integer> {
	@Query("SELECT o FROM OrderDetails o WHERE o.order.id = :orderId")
	List<OrderDetails> findByOrderId(@Param("orderId") Long orderId);

}
