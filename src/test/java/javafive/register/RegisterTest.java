package javafive.register;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javafive.controller.RegisterController;
import javafive.dto.RegisterDTO;
import javafive.entity.Roles;
import javafive.entity.User;
import javafive.service.UserServie;
import javafive.service.RoleService;

@ExtendWith(MockitoExtension.class)
class RegisterTest {

    @Mock
    private UserServie userService;
    
    @Mock
    private RoleService roleService;
    
    @Mock
    private Model model;
    
    @Mock
    private BindingResult bindingResult;
    
    @InjectMocks
    private RegisterController registerController;
    
    private RegisterDTO registerDTO;
    
    @BeforeEach
    void setUp() {
        registerDTO = RegisterDTO.builder()
                .username("user21")
                .password("pass123")
                .confirmPassword("pass123")
                .fullname("Nguyen Van A")
                .email("user21@example.com")
                .mobile("0369123456")
                .build();
    }
    
    @Test
    void UT_REGISTER_01_Success() {
        when(userService.findByUsername("user21")).thenReturn(Optional.empty());
        when(bindingResult.hasErrors()).thenReturn(false);
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(userService).create(any(User.class));
        assertEquals("redirect:/devshop/home/login", view);
    }
    
    @Test
    void UT_REGISTER_02_UsernameAlreadyExists() {
        when(userService.findByUsername("user21")).thenReturn(Optional.of(new User()));
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_03_PasswordTooShort() {
        registerDTO.setPassword("pass");
        registerDTO.setConfirmPassword("pass");
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_05_InvalidUsernameCharacters() {
        registerDTO.setUsername("user21&");
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_06_EmptyUsername() {
        registerDTO.setUsername(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registerController.register(registerDTO, bindingResult, model);
        });

        assertEquals("Username không được để trống", exception.getMessage());
    }

    
    @Test
    void UT_REGISTER_07_EmptyPassword() {
        registerDTO.setPassword(null);
        

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registerController.register(registerDTO, bindingResult, model);
        });

        assertEquals("Mật khẩu không được để trống.", exception.getMessage());
    }
    
    @Test
    void UT_REGISTER_08_UsernameMaxLength() {
        registerDTO.setUsername("a".repeat(99));
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        assertEquals("redirect:/devshop/home/login", view);
    }
    
    @Test
    void UT_REGISTER_09_PasswordMaxLength() {
        registerDTO.setPassword("a".repeat(99));
        registerDTO.setConfirmPassword("a".repeat(99));
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        assertEquals("redirect:/devshop/home/login", view);
    }
    
    @Test
    void UT_REGISTER_10_UsernameTooShort() {
        registerDTO.setUsername("usr");
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_11_PasswordWithWhitespace() {
        registerDTO.setPassword("pass 123");
        registerDTO.setConfirmPassword("pass 123");
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_12_UsernameTooLong() {
        registerDTO.setUsername("a".repeat(100));
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_13_PasswordTooLong() {
        registerDTO.setPassword("a".repeat(100));
        registerDTO.setConfirmPassword("a".repeat(100));
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_14_PasswordMismatch() {
        registerDTO.setConfirmPassword("passs");
        
        String view = registerController.register(registerDTO, bindingResult, model);
        
        verify(model).addAttribute(eq("errors"), any(List.class));
        assertEquals("home/register", view);
    }
    
    @Test
    void UT_REGISTER_15_EmptyConfirmPassword() {
        registerDTO.setConfirmPassword(null);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registerController.register(registerDTO, bindingResult, model);
        });

        assertEquals("Mật khẩu xác nhận không được để trống", exception.getMessage());
    }
}