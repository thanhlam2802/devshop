package javafive.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;


public interface CookieService {
	Cookie create(String name, String value, int expiry);
	Cookie get(String name);
	
	
	default void  delete(String name) {
		this.create(name, "", 0);
	}
	default String getValue(String name) {
		Cookie cookie = this.get(name);
		return cookie != null ? cookie.getValue():""
			;
	}
	
}
