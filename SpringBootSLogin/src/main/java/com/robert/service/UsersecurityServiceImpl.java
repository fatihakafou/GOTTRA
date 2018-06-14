package com.robert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.robert.model.Usersecurity;
import com.robert.repository.UsersecurityRepository;




@Service("usersecurityService")
public class UsersecurityServiceImpl implements  UsersecurityService {
	
	@Autowired
	private UsersecurityRepository usersecurityRepository;	

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	// ======================================
	// Méthode de base
	// mis en commenataire pour implémenter le cryptage
	// ======================================
	/*
	public void saveUser(Usersecurity user) {
		usersecurityRepository.save(user);
	}
	*/
	
	// =============================================
	// save user in database
	// =============================================
	//@Override
	public void saveUser(Usersecurity user) {
		
		org.springframework.security.crypto.password.PasswordEncoder encoder
 	   = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
		
		String passwdNonCrypt = user.getPassword();
		System.out.println("=============================================");
    	System.out.println("1-passage saveUser service");
    	System.out.println("2-passage saveUser service mot de passe non crypté/fonction user:"+user.getPassword());
    	System.out.println("3-passage saveUser service mot de passe non crypté/variable:"+ passwdNonCrypt);
    	System.out.println("=============================================");
    	
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		String passwdCrypt = user.getPassword();
		
		System.out.println("=============================================");
    	System.out.println("4-passage saveUser service mot de passe crypté/variable:"+passwdCrypt);
    	System.out.println("=============================================");
    	
    	System.out.println("6-passwd Non crypté / crypté via variable :"+passwdNonCrypt+"//"+passwdCrypt);
    	System.out.println("7-Contrôle password"+encoder.matches(passwdNonCrypt, passwdCrypt));
    	System.out.println("=============================================");
    	
        user.setActived(true);
        usersecurityRepository.save(user);
	}
	
	// =============================================
	// Recherche par email
	// =============================================
	//@Override
	public Usersecurity findUsersecurityByEmail(String email) {
		return usersecurityRepository.findByEmail(email);
	}

	// =============================================
	// Recherche par username
	// =============================================
	public Usersecurity findUsersecurityByUsername(String username) {
		return usersecurityRepository.findByUsername(username);
	}
	
	
	
	
	
	
	
/*
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usersecurity user = UsersecurityDao.findUserByName(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
		
	}
	*/
	
	
	
	
	/*
	 * 
	 * public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.devglan.model.UserDetails user = userDao.findUserById(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public List<com.devglan.model.UserDetails> getUsers() {
		return userDao.getUserDetails();
	}
	 */

}
