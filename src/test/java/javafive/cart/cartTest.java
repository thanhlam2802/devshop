package javafive.cart;

import javafive.controller.CartController;
import javafive.controller.LoginController;
import javafive.entity.CartItem;
import javafive.entity.Color;
import javafive.entity.User;
import javafive.service.ColorService;
import javafive.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class cartTest {
    @InjectMocks
    private CartController cartController;
    
    @InjectMocks
    private LoginController loginController;

    @Mock
    private SessionService sessionService;

    @Mock
    private ColorService colorService;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;

    private List<CartItem> cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new ArrayList<>();
    }

    @Test
    void UT_CART_01_addValidProductToCart() {
        when(session.getAttribute("cart")).thenReturn(cart);
    
        when(colorService.getColorById(anyInt())).thenReturn(Optional.of(new javafive.entity.Color(1, "Red")));
        User mockUser = new User("testUser", "password", true, "test@example.com", "0123456789", "Test Address", null, null);
        when(session.getAttribute("currentUser")).thenReturn(mockUser);
        String result = cartController.addToCart(1, "Product 1", 1, "M", 100.0, 2, "image.jpg", session, redirectAttributes);
        assertEquals("redirect:/devshop/product/1", result);
        assertEquals(1, cart.size());
    }



    @Test
    void UT_CART_02_addProductWithNegativeQuantity() {
        String result = cartController.addToCart(1, "Product 1", 1, "M", 100.0, -1, "image.jpg", session, redirectAttributes);
        assertEquals("redirect:/devshop/product/1", result);
        assertTrue(cart.isEmpty());
    }

    @Test
    void UT_CART_04_addProductExceedingStock() {
        String result = cartController.addToCart(1, "Product 1", 1, "M", 100.0, 1000, "image.jpg", session, redirectAttributes);
        assertEquals("redirect:/devshop/product/1", result);
    }

    @Test
    void UT_CART_05_removeValidProductFromCart() {
        cart.add(new CartItem("testUser", 1, "Product 1", "Red", "M", 100.0, 2, "image.jpg"));
        when(sessionService.get("cart")).thenReturn(cart);

        String result = cartController.removeFromCart(1, session);
        assertEquals("Xóa sản phẩm khỏi giỏ hàng!", result);
        assertTrue(cart.isEmpty());
    }

    @Test
    void UT_CART_07_updateCartValidQuantity() {
        cart.add(new CartItem("testUser", 1, "Product 1", "Red", "M", 100.0, 2, "image.jpg"));
        when(sessionService.get("cart")).thenReturn(cart);

        String result = cartController.updateCart(1, "increase", session);
        assertEquals("redirect:/cart/show", result);
        assertEquals(3, cart.get(0).getQuantity());
    }

    @Test
    void UT_CART_08_updateCartQuantityToZero() {
        cart.add(new CartItem("testUser", 1, "Product 1", "Red", "M", 100.0, 1, "image.jpg"));
        when(sessionService.get("cart")).thenReturn(cart);

        cartController.updateCart(1, "decrease", session);
        assertTrue(cart.isEmpty());
    }

    @Test
    void UT_CART_09_updateCartQuantityToNegative() {
        cart.add(new CartItem("testUser", 1, "Product 1", "Red", "M", 100.0, 1, "image.jpg"));
        when(sessionService.get("cart")).thenReturn(cart);

        cartController.updateCart(1, "decrease", session);
        assertTrue(cart.isEmpty());
    }

    @Test
    void UT_CART_10_addDuplicateProductToCart() {
        when(session.getAttribute("currentUser")).thenReturn(new User("testUser", "password", true, "test@example.com", "0123456789", "Test Address", null, null));
        cart.add(new CartItem("testUser", 1, "Product 1", "Red", "M", 100.0, 1, "image.jpg"));
 
        when(session.getAttribute("cart")).thenReturn(cart);

        when(colorService.getColorById(anyInt())).thenReturn(Optional.of(new javafive.entity.Color(1, "Red")));

        cartController.addToCart(1, "Product 1", 1, "M", 100.0, 1, "image.jpg", session, redirectAttributes);

        assertEquals(2, cart.get(0).getQuantity());
    }


    @Test
    void UT_CART_11_addProductWithoutLogin() {
        when(session.getAttribute("currentUser")).thenReturn(null);
        String result = cartController.addToCart(1, "Product 1", 1, "M", 100.0, 1, "image.jpg", session, redirectAttributes);
        assertEquals("redirect:/devshop/product/1", result);
    }

    @Test
    void UT_CART_12_checkCartTotalWithValidProducts() {
        cart.add(new CartItem("testUser", 1, "Product 1", "Red", "M", 100.0, 2, "image.jpg"));
        when(sessionService.get("cart")).thenReturn(cart);
        double total = cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        assertEquals(200.0, total);
    }

    @Test
    void UT_CART_13_checkCartTotalWithEmptyCart() {
        when(sessionService.get("cart")).thenReturn(cart);
        double total = cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        assertEquals(0.0, total);
    }

    @Test
    void UT_CART_14_checkCartAfterLogout() {
        session.setAttribute("cart", new ArrayList<>()); 
        session.setAttribute("currentUser", new User("testUser", "password", true, "test@example.com", "0123456789", "Test Address", null, null));

        loginController.logoutForm(session);

        assertNull(session.getAttribute("cart"));
    }


    @Test
    void UT_CART_15_addProductWhenCartIsFull() {
        for (int i = 0; i < 50; i++) {
            cart.add(new CartItem("testUser", i, "Product " + i, "Red", "M", 100.0, 1, "image.jpg"));
        }
        when(session.getAttribute("cart")).thenReturn(cart);

        String result = cartController.addToCart(51, "Product 51", 1, "M", 100.0, 1, "image.jpg", session, redirectAttributes);
        assertEquals("redirect:/devshop/product/51", result);
    }

    @Test
    void UT_CART_19_clearCart() {
       
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem("testUser", 1, "Product 1", "Red", "M", 100.0, 1, "image.jpg"));
        session.setAttribute("cart", cart);

        cartController.clearCart(session, redirectAttributes);

        assertNull(session.getAttribute("cart")); 
    }

}