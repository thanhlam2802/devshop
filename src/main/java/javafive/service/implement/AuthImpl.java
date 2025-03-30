package javafive.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.AuthorityDAO;
import javafive.entity.Authority;
import javafive.service.AuthorityService;
@Service
public class AuthImpl implements AuthorityService{
	 @Autowired
	 AuthorityDAO dao;
	@Override
	public void create(Authority authority) {
		dao.save(authority);
		
	}

}
