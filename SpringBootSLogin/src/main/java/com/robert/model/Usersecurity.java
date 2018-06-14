package com.robert.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "usersecurity")
public class Usersecurity implements Serializable {
	
	@Id
	@Column(name = "username")
	@NotEmpty
	private String username;
	
	@Column(name = "password")
	@NotEmpty(message = "Please provide your password") 
	@Size(min=3, max=255, message="la taille doit être > 3 caractères")
	//@Transient
	private String password;
	
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "actived")
	private Boolean actived;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getActived() {
		return actived;
	}
	public void setActived(Boolean actived) {
		this.actived = actived;
	}
	public Usersecurity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Usersecurity(String username, String password, Boolean actived, String email) {
		super();
		this.username = username;
		this.password = password;
		this.actived = actived;
		this.email = email;
	}
	
	public Usersecurity(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.actived = true;
		this.email = "test@free.fr";
	}
	
	

}
