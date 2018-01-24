package com.vh.forum.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Gekson.Silva
 *
 */
@Document(collection="users")
@EqualsAndHashCode 
@ToString(exclude = "password")
@Getter @Setter
public class User {
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	@Id	
    private String id;
    	
	private String name;	 
	
//	@Indexed(unique=true) 
	private String username;
	
	@JsonIgnore
	private String password;
	
	private String role;

	public User(String name, String username, String password, String role) {
		this.name = name;
		this.username = username;
//		setPassword(password);
		this.password = password;
		this.role = role;
	}
	
	public void setPassword(String password) {
		this.password = PASSWORD_ENCODER.encode(password);
	}

}
