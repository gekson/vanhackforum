package com.vh.forum.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Gekson.Silva
 *
 */
@Document(collection="categories")
@EqualsAndHashCode @ToString
@Getter @Setter
public class Category {

	@Id	
    private String id;
    	
	private String name;
	
	public Category() {
	}
	
	public Category(String name) {
		this.name = name;
	}
	
	public Category(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
