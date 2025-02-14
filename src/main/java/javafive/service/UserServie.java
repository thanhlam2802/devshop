package javafive.service;

import java.util.List;
import java.util.Optional;

import javafive.entity.User;
import org.springframework.stereotype.Service;


@Service
public interface UserServie {

	List<User> findAll(); 
    Optional<User> findById(Long id); 
    void deleteById(Long id); 
    User create(User user); 
    User update(User user); 
	
}
