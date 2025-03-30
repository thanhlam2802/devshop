package javafive.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.AuthorityDAO;
import javafive.dao.RoleDAO;
import javafive.entity.Roles;
import javafive.service.RoleService;
@Service
public class RoleImpl implements RoleService{
	@Autowired
	 RoleDAO dao;
	@Override
	public Optional<Roles> findById(String id) {
		
		return dao.findById(id);
	}

	@Override
	public void save(Roles role) {
		dao.save(role);
		
	}

}
