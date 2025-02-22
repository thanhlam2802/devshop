package javafive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import javafive.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
	


	
    
	@PostMapping("/update")
    public String updateCart(@RequestParam Long productId, @RequestParam String action, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            return "redirect:/cart/show";
        }

        for (CartItem item : cart) {
            if (item.getProducID().equals(productId)) {
                if ("increase".equals(action)) {
                    item.setQuantity(item.getQuantity() + 1);
                } else if ("decrease".equals(action) && item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                }
                break;
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart/show";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer productId,
                            @RequestParam String productName,
                            @RequestParam String color,
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
            if (item.getProducID().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật số lượng sản phẩm thành công!");
                return "redirect:/devshop/product/" + productId;
            }
        }

        cart.add(new CartItem(productId, productName, color, size, price, quantity, image));
        redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm vào giỏ hàng thành công!");
        return "redirect:/devshop/product/" + productId;
    }

    
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Integer productVariantId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(item -> item.getProducID().equals(productVariantId));
            return "Xóa sản phẩm khỏi giỏ hàng!";
        }

        return "Giỏ hàng trống!";
    }

   
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "Đã xóa toàn bộ giỏ hàng!";
    }
    


}
