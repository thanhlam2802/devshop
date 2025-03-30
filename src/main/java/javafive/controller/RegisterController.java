package javafive.controller;

import jakarta.validation.Valid;
import javafive.dto.RegisterDTO;
import javafive.entity.Authority;
import javafive.entity.Roles;
import javafive.entity.User;
import javafive.service.AuthorityService;
import javafive.service.RoleService;
import javafive.service.UserServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserServie userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "home/register";
    }

    @PostMapping
    public String register(@Valid RegisterDTO registerDTO, BindingResult result, Model model) {
        List<String> errors = validateRegisterDTO(registerDTO, result);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "home/register";
        }

        User user = createUser(registerDTO);
        userService.create(user);
        assignCustomerRole(user, model);

        return "redirect:/devshop/home/login";
    }

    private List<String> validateRegisterDTO(RegisterDTO registerDTO, BindingResult result) {
        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            errors.add("Vui lòng điền đầy đủ thông tin.");
        }

        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        String confirmPassword = registerDTO.getConfirmPassword();

        Optional<User> userOptional = userService.findByUsername(username);

        if (username == null || username.isBlank()) {
        	errors.add("Username không được để trống");
            throw new IllegalArgumentException("Username không được để trống");
   

        } else if (username.length() < 5 || username.length() > 99) {
            errors.add("Username phải có từ 5 đến 99 ký tự.");
        } else if (!username.matches("^[a-zA-Z0-9_]+$")) {
            errors.add("Username không được chứa ký tự đặc biệt.");
        } else if (userOptional.isPresent()) {
            errors.add("Username đã tồn tại trong hệ thống.");
        }

        if (password == null || password.isBlank()) {
        	
            errors.add("Mật khẩu không được để trống.");
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        } else if (password.length() < 5 || password.length() > 99) {
            errors.add("Mật khẩu phải có từ 5 đến 99 ký tự.");
        } else if (password.contains(" ")) {
            errors.add("Mật khẩu không được chứa khoảng trắng.");
        }

        if (confirmPassword == null||confirmPassword.isBlank()) {
            errors.add("Mật khẩu xác nhận không được để trống.");
            throw new IllegalArgumentException("Mật khẩu xác nhận không được để trống");
            
        } else if (!password.equals(confirmPassword)) {
            errors.add("Mật khẩu xác nhận không khớp.");
        }

        return errors;
    }

    private User createUser(RegisterDTO registerDTO) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(registerDTO.getPassword())
                .enabled(true)
                .fullname(registerDTO.getFullname() != null ? registerDTO.getFullname() : "Chưa cập nhật")
                .mobile(registerDTO.getMobile() != null ? registerDTO.getMobile() : "0000000000")
                .photo(registerDTO.getPhoto() != null ? registerDTO.getPhoto() : "default.jpg")
                .email(registerDTO.getEmail() != null ? registerDTO.getEmail() : "no-email@example.com")
                .build();
    }


    private void assignCustomerRole(User user, Model model) {
        Optional<Roles> optionalRole = roleService.findById("CUSTOMER");
        if (optionalRole.isEmpty()) {
            model.addAttribute("errors", List.of("Role CUSTOMER chưa tồn tại, hãy liên hệ quản trị viên!"));
            return;
        }

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setRole(optionalRole.get());
        authorityService.create(authority);
    }
}
