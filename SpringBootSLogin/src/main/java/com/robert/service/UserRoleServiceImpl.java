package com.robert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robert.model.Usersroles;
import com.robert.repository.UsersrolesRepository;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	UsersrolesRepository userrolesRepository;
	
	public List<Usersroles> findAll(){
		return userrolesRepository.findAll();
	}
	
	public List<Usersroles> findByUser(String username){
		return userrolesRepository.findByUser(username);
	}

}
