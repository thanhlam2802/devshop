package javafive.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javafive.entity.OrderStatus;
import javafive.entity.Orders;
import javafive.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

	 @Autowired
	    private OrderService orderService;
	 @PostMapping("/update-status")
	 public String updateOrderStatus(@RequestParam("orderId") Long orderId,
	                                 @RequestParam("status") String status,
	                                 RedirectAttributes redirectAttributes) {
	     try {
	         Optional<Orders> optionalOrder = orderService.getOrderById(orderId);
	         if (optionalOrder.isPresent()) {
	             Orders order = optionalOrder.get();
	             order.setStatus(OrderStatus.valueOf(status));
	             orderService.saveOrUpdateOrder(order); 
	             redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công!");
	         } else {
	             redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng!");
	         }
	     } catch (Exception e) {
	         redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
	     }
	     return "redirect:/devshop/admin/order";  
	 }

}
