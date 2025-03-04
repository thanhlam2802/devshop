package javafive.controller;
import javafive.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import javafive.entity.CartItem;
import javafive.entity.User;
import javafive.service.ColorService;
import javafive.service.CookieService;
import javafive.service.SessionService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CookieService cookieService;
	@Autowired
	SessionService sessionService;
	@Autowired
	ColorService colorService;
	
	@PostMapping("/update")
    public String updateCart(@RequestParam Integer productId, @RequestParam String action, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) sessionService.get("cart");
        if (cart == null) {
            return "redirect:/cart/show";
        }
       
        Iterator<CartItem> iterator = cart.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProducID().equals(productId)) {
                if ("increase".equals(action)) {
                    item.setQuantity(item.getQuantity() + 1);
                } else if ("decrease".equals(action)) {
                    item.setQuantity(item.getQuantity() - 1);
                    if (item.getQuantity() == 0) {
                        iterator.remove(); 
                    }
                }
                break;
            }
        }
        
        sessionService.set("cart", cart);
        
        return "redirect:/cart/show";
    }

	@PostMapping("/add")
	public String addToCart(@RequestParam Integer productId,
	                        @RequestParam String productName,
	                        @RequestParam Integer color,
	                        @RequestParam String size,
	                        @RequestParam Double price,
	                        @RequestParam Integer quantity,
	                        @RequestParam String image,
	                        HttpSession session,
	                        RedirectAttributes redirectAttributes) {
	    
	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	    if (cart == null) {
	        cart = new ArrayList<>();
	        session.setAttribute("cart", cart);
	    }

	    for (CartItem item : cart) {
	        if (item.getProducID().equals(productId) && item.getColor().equals(color) && item.getSize().equals(size)) {
	            item.setQuantity(item.getQuantity() + quantity);
	            redirectAttributes.addFlashAttribute("addedItem", item);
	            return "redirect:/devshop/product/" + productId;
	        }
	    }

	   
	    String nameColor = colorService.getColorById(color).get().getName();
	
	    User user = (User) session.getAttribute("currentUser");
	    String userId = user.getUsername();

	
	    CartItem newItem = new CartItem(userId, productId, productName, nameColor, size, price, quantity, image);
	    cart.add(newItem);

	    redirectAttributes.addFlashAttribute("addedItem", newItem);
	    return "redirect:/devshop/product/" + productId;
	}

    
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Integer productVariantId, HttpSession session) {
    	List<CartItem> cart = (List<CartItem>) sessionService.get("cart");

        if (cart != null) {
            cart.removeIf(item -> item.getProducID().equals(productVariantId));
            return "Xóa sản phẩm khỏi giỏ hàng!";
        }

        return "Giỏ hàng trống!";
    }

   
    @PostMapping("/clear")
    public String clearCart() {
        sessionService.remove("cart");
        return "Đã xóa toàn bộ giỏ hàng!";
    }
    


}
