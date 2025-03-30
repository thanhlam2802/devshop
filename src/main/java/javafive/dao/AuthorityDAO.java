package javafive.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javafive.entity.Authority;

public interface AuthorityDAO extends JpaRepository<Authority,Integer> {

}
