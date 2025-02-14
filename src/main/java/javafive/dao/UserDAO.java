package javafive.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.User;
public interface UserDAO extends JpaRepository<User, String>{

}
