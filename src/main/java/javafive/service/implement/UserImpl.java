package javafive.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.UserDAO;
import javafive.entity.User;
import javafive.service.UserServie;
@Service
public class UserImpl implements UserServie{

	@Autowired
	UserDAO dao;
	@Override
	public List<User> findAll() {
		
		return dao.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		
		return Optional.empty();
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
