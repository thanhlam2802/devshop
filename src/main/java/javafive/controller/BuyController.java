package javafive.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import javafive.entity.CartItem;
import javafive.entity.Color;
import javafive.entity.OrderDetails;
import javafive.entity.OrderStatus;
import javafive.entity.Orders;
import javafive.entity.PaymentMethod;
import javafive.entity.ProductVariant;
import javafive.entity.Size;
import javafive.entity.User;
import javafive.service.ColorService;
import javafive.service.OrderDetailService;
import javafive.service.OrderService;
import javafive.service.ProductVariantService;
import javafive.service.SessionService;
import javafive.service.SizeService;
import javafive.service.UserServie;

@Controller
public class BuyController {
	@Autowired
	SessionService sessionService;
	@Autowired
	UserServie userService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDitailService;
	@Autowired
	ColorService colorService;
	@Autowired
	SizeService sizeService;
	
	@Autowired
	ProductVariantService productVariantService;
	
	@RequestMapping("/devshop/checkout")
    public String product(Model model, HttpServletRequest request) {
        User user = sessionService.get("currentUser");
        List<CartItem> cartItem = sessionService.get("cart");
		
        if(user !=null) {
        	model.addAttribute("userName",user.getFullname());
        	model.addAttribute("userPhone", user.getMobile());
        	
        }
        System.out.print(cartItem);
        model.addAttribute("cartItems",cartItem);
        return "/home/buy";
    }
	
	@PostMapping("devshop/order/submit")
	public String submitOrder(
	        @RequestParam("customerAddress") String customerAddress,
	        @RequestParam("paymentMethod") String paymentMethod,
	        HttpServletRequest request,
	        RedirectAttributes redirectAttributes) {
		 {
			    if (customerAddress == null || customerAddress.trim().isEmpty()) {
			        redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng nhập địa chỉ giao hàng!");
			        return "redirect:/devshop/checkout"; 
			    }
	   
	    List<CartItem> cartItems = (List<CartItem>) sessionService.get("cart");

	    if (cartItems == null || cartItems.isEmpty()) {
	        redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn đang trống!");
	        return "redirect:/cart";
	    }

	
	    User user = sessionService.get("currentUser");
	    if (user == null) {
	        redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập trước khi đặt hàng!");
	        return "redirect:/login";
	    }

	  
	    BigDecimal totalPrice = cartItems.stream()
	            .map(item -> BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
	            .reduce(BigDecimal.ZERO, BigDecimal::add);

	  
	    Orders order = Orders.builder()
	            .user(user)
	            .totalPrice(totalPrice.doubleValue())
	            .status(OrderStatus.PENDING)
	            .paymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()))
	            .shippingAddress(customerAddress)
	            .createdAt(LocalDateTime.now())
	            .build();
	    orderService.saveOrUpdateOrder(order);

	   
	    for (CartItem item : cartItems) {
	    	
	    	Color colorEntity = colorService.findByName(item.getColor()).orElse(null);
	    	if (colorEntity == null) {
	    	    System.out.println("Không tìm thấy màu: " + item.getColor());
	    	    continue;
	    	}

	    	
	    	Size sizeEntity = sizeService.findByName(item.getSize()).orElse(null);
	    	if (sizeEntity == null) {
	    	    System.out.println("Không tìm thấy size: " + item.getSize());
	    	    continue;
	    	}

	    	
	    	ProductVariant productVariant = productVariantService.findVariant(
	    	    item.getProducID(), colorEntity.getColor_id(), sizeEntity.getSizeId()
	    	).orElse(null);

	    	if (productVariant == null) {
	    	    System.out.println("Không tìm thấy sản phẩm với biến thể phù hợp!");
	    	    continue;
	    	}


	        OrderDetails orderDetail = new OrderDetails();
	        orderDetail.setOrder(order);
	        orderDetail.setProductVariant(productVariant);
	        orderDetail.setUnitPrice(BigDecimal.valueOf(item.getPrice()));
	        orderDetail.setQuantity(item.getQuantity());

	        orderDitailService.create(orderDetail);
	    }

	 
	    sessionService.remove("cart");
	    redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công!");

	    return "redirect:/devshop/order/confirmation";
	}

	
	}
	@RequestMapping("/devshop/order/confirmation")
	public String getConfirmation() {
		
		return "/home/confirmation";
	}
	
}


