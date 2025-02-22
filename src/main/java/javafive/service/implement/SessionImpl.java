package javafive.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import javafive.service.SessionService;

@Service
public class SessionImpl implements SessionService{

	@Autowired
	HttpSession session;
	
	@Override
	public <T> T get(String name) {
		return (T) session.getAttribute(name);
	}

	@Override
	public void set(String name, Object value) {
		session.setAttribute(name, value);		
	}

	@Override
	public void remove(String name) {
		session.removeAttribute(name);
		
	}

}
