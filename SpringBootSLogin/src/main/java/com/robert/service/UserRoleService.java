package com.robert.service;

import java.util.List;

import com.robert.model.Usersroles;

public interface UserRoleService {
	
	List<Usersroles> findAll();
	List<Usersroles> findByUser(String username);
	

}
