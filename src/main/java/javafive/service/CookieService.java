package javafive.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {
	@Autowired
	HttpServletResponse response;
	@Autowired
	HttpServletRequest request;
	
	public Cookie create(String name, String value, int expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(expiry);
		cookie.setPath("/");
		
		response.addCookie(cookie); // gửi về client
		
		return cookie;
	}
	
	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equalsIgnoreCase(name)) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	public void delete(String name) {
		this.create(name, "", 0);
	}
	
	public String getValue(String name) {
		Cookie cookie = this.get(name);
		return cookie != null ? cookie.getValue() : "";
	}
}
