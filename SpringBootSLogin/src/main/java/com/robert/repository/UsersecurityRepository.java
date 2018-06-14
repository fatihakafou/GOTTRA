package com.robert.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.robert.model.Usersecurity;

@Repository("usersecurityRepository")
public interface UsersecurityRepository extends JpaRepository<Usersecurity, String>{
	
	// =========================================================
	// recherche par email non utilisée pour le moment
	// =========================================================	
	 Usersecurity findByEmail(String email);

	// =========================================================
	// recherche par username non utilisée pour le moment
	// =========================================================	
	 Usersecurity findByUsername(String username);
		 	 
	 // ============================================================
	 // requête pour la recherche
	 // ============================================================
	 @Query("select e from Usersecurity e where e.username like :x")
	  public Page<Usersecurity> finduser(@Param("x")String mc, Pageable pageable); 
	 
	 
	 //@Query("select e from Usersroles e where e.user like :x")
	 //public Page<Usersroles> findroles(@Param("x")String mc, Pageable pageable); 
	 
	// @Query("select e from User where e.dateNaissance > :x and e.dateNaissance < :y")
	// public List<User> finduser(@Param("x")Date d1, @Param("y")Date d2); 

}




