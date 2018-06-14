package com.robert.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.robert.model.Usersecurity;
import com.robert.model.Usersroles;

@Repository("usersrolesRepository")
public interface UsersrolesRepository extends JpaRepository<Usersroles, Integer>{
	
	
	Usersroles findByRole(String role);
	
	
	
	@Query("select e from Usersroles e where e.user like :x")
	public Page<Usersroles> findroles(@Param("x")String mc, Pageable pageable);
	
	
	@Query("select e from Usersroles e where e.user like :x")
	public List<Usersroles> findByUser(@Param("x")String mc);


}
