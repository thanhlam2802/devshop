package javafive.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Roles {
	@Id
	private String id;
	private String name;
	@OneToMany(mappedBy = "role")
	private List<Authority> authorities;
}
