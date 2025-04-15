package javafive.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javafive.dao.OrderDetailDAO;
import javafive.entity.OrderDetails;
import javafive.entity.OrderStatus;
import javafive.entity.Orders;
import javafive.service.OrderDetailService;
import javafive.service.OrderService;

@Controller
@RequestMapping("/devshop/order/confirmation")
public class CustomerController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @GetMapping
    public String showOrdersByStatus(@RequestParam(value = "status", required = false, defaultValue = "PENDING") OrderStatus status, Model model) {
        List<Orders> orders = orderService.getOrdersByStatus(status);
        

        model.addAttribute("orders", orders);
        model.addAttribute("activeTab", status.name());
        return "/home/confirmation";
    }


    @GetMapping("/details")
    public String showOrderDetails(@RequestParam("id") Long orderId, Model model) {
        Optional<Orders> orderOpt = orderService.getOrderById(orderId);

        if (orderOpt.isPresent()) {
            Orders order = orderOpt.get();
            int orderId2 = Math.toIntExact(order.getId());
     
           
            List<OrderDetails> orderDetail = orderDetailDAO.findByOrderId(orderId);
            System.out.println(": " + orderDetail);
            model.addAttribute("selectedOrder", order);
            model.addAttribute("orderDetails", orderDetail); 
            model.addAttribute("activeTab", "details");
            return "/home/confirmation";
        } else {
            return "redirect:/devshop/order/confirmation?error=notfound"; 
            }
    }

}
