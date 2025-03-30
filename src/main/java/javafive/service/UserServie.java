package javafive.service;

import java.util.List;
import java.util.Optional;

import javafive.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;


@Service
public interface UserServie {
	List<User> findAllCustomer();
	List<User> findAll(); 
    Optional<User> findById(String id); 
    void deleteById(Long id); 
    User create(User user); 
    User update(User user); 
    Page<User> findUsersWithLimit(int size);
    Optional<User> findByUsernameOrEmail(String identifier);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
	
    
}
