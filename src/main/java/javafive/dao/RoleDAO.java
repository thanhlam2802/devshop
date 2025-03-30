package javafive.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.Roles;

public interface RoleDAO extends JpaRepository<Roles,String>{

}
