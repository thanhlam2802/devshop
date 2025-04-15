package javafive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javafive.entity.User;
import javafive.service.SessionService;
import javafive.service.UserServie;
import javafive.service.implement.UserDetailsServiceImpl;
import javafive.util.JwtUtil;


@Configuration
public class SecurityConfig {
	@Autowired
	SessionService sessionService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
	UserServie userService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/cookie/login/**", "/devshop/home/logout","/cookie/login/form").permitAll()
                .requestMatchers("/devshop/admin/**").hasRole("ADMIN")
                .requestMatchers("/devshop/page/**").hasRole("CUSTOMER")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/cookie/login/form")
                .loginProcessingUrl("/cookie/login/check")
                .usernameParameter("identifier")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler())
                .failureUrl("/cookie/login/form?error")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/devshop/home/logout")
                .logoutSuccessUrl("/cookie/login/form?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "un", "pw")
                .addLogoutHandler((request, response, authentication) -> {
                   
                    sessionService.remove("currentUser");
                }))
            .rememberMe(remember -> remember
                .key("uniqueKey")
                .rememberMeParameter("remember")
                .tokenValiditySeconds(30 * 24 * 60 * 60)); 
        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            // Lấy thông tin người dùng từ authentication
            org.springframework.security.core.userdetails.User principal = 
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            
            

          
            Optional<User> user = userService.findByUsernameOrEmail(principal.getUsername());
            sessionService.set("currentUser", user.get()); 

            // Nếu là Admin, chuyển đến trang admin
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                response.sendRedirect("/devshop/admin/index");
            } else {
                response.sendRedirect("/devshop/page/index");
            }
        };
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
