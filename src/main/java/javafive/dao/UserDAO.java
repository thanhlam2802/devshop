package javafive.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javafive.entity.User;
public interface UserDAO extends JpaRepository<User, String>{
	
	    @Query("SELECT DISTINCT u FROM User u JOIN u.authorities a WHERE a.role.id = 'CUSTOMER'")
	    List<User> findAllCustomer();
	    
	    
	    
	    @Query("SELECT DISTINCT u FROM User u JOIN u.authorities a WHERE a.role.id = 'CUSTOMER'")
	    Page<User> findCustomersWithPagination(Pageable pageable);
	    
	    Optional<User> findByUsernameOrEmail(String username, String email);
	    Optional<User> findByEmail(String email);
	    Optional<User> findByUsername(String username);
}
