package javafive;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javafive.entity.User;

@Component
public class AuthInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        HttpSession session = request.getSession();
//        User currentUser = (User) session.getAttribute("currentUser"); 
//
//        if (currentUser == null) {
//            response.sendRedirect("/cookie/login/form");
//            return false;
//        }
//
//       
//        String role = currentUser.getAuthorities().stream()
//                .map(auth -> auth.getRole().getAuthorities()) 
//                .findFirst()
//                .orElse("CUSTOMER"); 
//
//        // Điều hướng theo vai trò
//        if (role.equalsIgnoreCase("ADMIN") && request.getRequestURI().startsWith("/devshop/admin/index")) {
//            return true;
//        } 
//        if (role.equalsIgnoreCase("CUSTOMER") && request.getRequestURI().startsWith("/devshop/page/index")) {
//            return true;
//        }
//
//        response.sendRedirect("/cookie/login/form?error=unauthorized");
//        return false;
//    }
}
