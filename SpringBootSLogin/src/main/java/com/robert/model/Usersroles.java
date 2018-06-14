package com.robert.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "users_roles")

public class Usersroles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_security_username")
	@NotEmpty
	private String user;
	
	@Column(name = "roles_role")
	@NotEmpty
	private String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Usersroles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usersroles(Integer id, String user, String role) {
		super();
		this.id = id;
		this.user = user;
		this.role = role;
	}
	

}
