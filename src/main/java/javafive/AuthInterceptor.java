package javafive;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java5.hoc.Const;
import javafive.entity.Authority;
import javafive.entity.Roles;
import javafive.entity.User;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    HttpSession session = request.getSession();
	    User currentUser = (User) session.getAttribute("currentUser"); 

	    System.out.println("Current User in session: " + currentUser);

	    if (currentUser == null&& !request.getRequestURI().contains("/cookie/login/check")) {
	        session.setAttribute(Const.SECAURED, request.getRequestURI());
	        response.sendRedirect("/cookie/login/form");
	        return false;
	    }

	    List<Authority> authorities = currentUser.getAuthorities();
	    String role = "CUSTOMER";
	    if (authorities != null && !authorities.isEmpty()) {
	        Roles userRole = authorities.get(0).getRole();
	        if (userRole != null) {
	            role = userRole.getId();
	        }
	    }
	   

	    System.out.println("User Role: " + role);
	    System.out.println("Request URI: " + request.getRequestURI());

	    if (role.equalsIgnoreCase("ADMIN") && request.getRequestURI().startsWith("/devshop/admin")) {
	        return true;
	    }
	    if (role.equalsIgnoreCase("CUSTOMER") && 
	       (request.getRequestURI().startsWith("/devshop/page") || request.getRequestURI().startsWith("/cart"))) {
	        return true;
	    }


	    response.sendRedirect("/cookie/login/form?error=unauthorized");
	    return false;
	}

}
