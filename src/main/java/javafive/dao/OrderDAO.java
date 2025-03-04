package javafive.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javafive.entity.OrderStatus;
import javafive.entity.Orders;
import javafive.entity.PaymentMethod;

public interface OrderDAO extends JpaRepository<Orders, Long>{
	

	  @Query("SELECT o FROM Orders o")
	    List<Orders> getAllOrder();
	  

	    @Query("SELECT o FROM Orders o WHERE o.status = :status AND o.paymentMethod = :paymentMethod")
	    List<Orders> findByStatusAndPaymentMethod(@Param("status") OrderStatus status, 
	                                              @Param("paymentMethod") PaymentMethod paymentMethod);

	    @Query("SELECT o FROM Orders o WHERE o.status = 'COMPLETED'")
	    List<Orders> getCompletedOrders();
	    @Query("SELECT o FROM Orders o WHERE o.status = :status")
	    List<Orders> getOrdersByStatus(@Param("status") OrderStatus status);
	    
	    @Query("SELECT o FROM Orders o WHERE o.status = 'COMPLETED' AND o.paymentMethod = :paymentMethod")
	    List<Orders> getCompletedOrdersByPayment(@Param("paymentMethod") PaymentMethod paymentMethod);
}
