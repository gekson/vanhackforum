package com.vh.forum.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Document(collection="posts")
@EqualsAndHashCode @ToString
@Getter @Setter
public class Post {
	@Transient
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
	
	@Id	
    private String id;
    	
	private String title;
	
	private String description;
	
	private Category category;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dateRegister;
			
	private List<Commentary> comments;
	
	private User user;
	
	@Transient
	private Boolean userEqCurrentUser;
	
	public Post() {		
	}
	
	public Post(String title, String description, Category category, LocalDateTime dateRegister, User user) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.dateRegister = dateRegister;
	}
	
	public List<Commentary> getComments() {
		if(this.comments == null) {
			this.comments = new ArrayList<Commentary>();
		}
		return this.comments;
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
