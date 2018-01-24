package com.vh.forum.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vh.forum.config.LocalDateTimeDeserializer;
import com.vh.forum.config.LocalDateTimeSerializer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Gekson.Silva
 *
 */
@Document(collection="comments")
@EqualsAndHashCode @ToString
@Getter @Setter
public class Commentary {

	@Transient
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
	
	@Id	
    private String id;    		
	
	private String description;	
	
	@Transient
	private String postId;
	
	@Transient
	private Boolean userEqCurrentUser;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dateRegister;
	
	private User user;
	
	public Commentary() {
		
	}
	
	public Commentary(String description, User user) {
		this.description = description;
		this.dateRegister = LocalDateTime.now();
		this.user = user;
	}
	
	public Boolean getUserEqCurrentUser() {		
		try {
			if(auth.getName().equals(getUser().getUsername())) {
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}		
		return false;
	}
}
