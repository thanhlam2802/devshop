package javafive.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import javafive.controller.LoginController;
import javafive.entity.User;
import javafive.service.CookieService;
import javafive.service.SessionService;
import javafive.service.UserServie;

@ExtendWith(MockitoExtension.class)
class LoginTest {

    @Mock
    private UserServie userService;
    
    @Mock
    private SessionService sessionService;
    
    @Mock
    private CookieService cookieService;
    
    @Mock
    private HttpSession session;
    
    @Mock
    private Model model;
    
    @InjectMocks
    private LoginController loginController;
    
    private User mockUser;
    
    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setUsername("user01");
        mockUser.setPassword("pass123");
    }
    
    @Test
    void UT_LOGIN_01_Success_With_RememberMe() {
        when(userService.findByUsernameOrEmail("user01")).thenReturn(Optional.of(mockUser));
        
        String view = loginController.loginCheck(session, model, "user01", "pass123", true);
        
        verify(sessionService).set("currentUser", mockUser);
        verify(cookieService).create("un", "user01", 30 * 24 * 60 * 60);
        verify(cookieService).create("pw", "pass123", 30 * 24 * 60 * 60);
        
        assertEquals("redirect:/devshop/page/index", view);
    }
    
    @Test
    void UT_LOGIN_02_Success_Without_RememberMe() {
        when(userService.findByUsernameOrEmail("user01")).thenReturn(Optional.of(mockUser));
        
        String view = loginController.loginCheck(session, model, "user01", "pass123", false);
     
        verify(sessionService).set("currentUser", mockUser);
        verify(cookieService).delete("un");
        verify(cookieService).delete("pw");
        
        assertEquals("redirect:/devshop/page/index", view);
    }
    
    @Test
    void UT_LOGIN_03_Fail_Invalid_Username() {
        when(userService.findByUsernameOrEmail("wronguser")).thenReturn(Optional.empty());
        
        String view = loginController.loginCheck(session, model, "wronguser", "pass123", false);
        
        verify(model).addAttribute("msg", "Invalid username or email!");
        assertEquals("/home/login", view);
    }
    
    @Test
    void UT_LOGIN_04_Fail_Invalid_Password() {
        when(userService.findByUsernameOrEmail("user01")).thenReturn(Optional.of(mockUser));
        
        String view = loginController.loginCheck(session, model, "user01", "wrongpassword", false);
        
        verify(model).addAttribute("msg", "Invalid password!");
        assertEquals("/home/login", view);
    }
    @Test
    void UT_LOGIN_05_Fail_Empty_Username() {
        String view = loginController.loginCheck(session, model, "", "password123", false);
        
        verify(model).addAttribute("msg", "Invalid username or email!");
        assertEquals("/home/login", view);
    }
    @Test
    void UT_LOGIN_06_Fail_Empty_Password() {
        when(userService.findByUsernameOrEmail("user01")).thenReturn(Optional.of(mockUser));

        String view = loginController.loginCheck(session, model, "user01", "", false);
        
        verify(model).addAttribute("msg", "Invalid password!");
        assertEquals("/home/login", view);
    }
    @Test
    void UT_LOGIN_07_Fail_Special_Character_Username() {
        String view = loginController.loginCheck(session, model, "user@!#", "password123", false);
        
        verify(model).addAttribute("msg", "Invalid username or email!");
        assertEquals("/home/login", view);
    }
    @Test
    void UT_LOGIN_08_RememberMe_With_Invalid_Credentials() {
        when(userService.findByUsernameOrEmail("user01")).thenReturn(Optional.empty());

        String view = loginController.loginCheck(session, model, "user01", "wrongpassword", true);

        verify(model).addAttribute("msg", "Invalid username or email!");
        verify(cookieService, never()).create(anyString(), anyString(), anyInt());
        assertEquals("/home/login", view);
    }
    @Test
    void UT_LOGIN_09_Success_Uppercase_Username() {
        when(userService.findByUsernameOrEmail("USER01")).thenReturn(Optional.of(mockUser));

        String view = loginController.loginCheck(session, model, "USER01", "pass123", true);

        verify(sessionService).set("currentUser", mockUser);
        assertEquals("redirect:/devshop/page/index", view);
    }




}
