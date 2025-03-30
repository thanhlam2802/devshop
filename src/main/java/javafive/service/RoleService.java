package javafive.service;

import java.util.Optional;

import javafive.entity.Roles;

public interface RoleService {
	Optional<Roles>  findById(String id);
    void save(Roles role);
}
