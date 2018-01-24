package com.vh.forum.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vh.forum.domain.Category;

/**
 * 
 * @author Gekson.Silva
 *
 */
public interface CategoryMongoRepository extends MongoRepository<Category, Integer> {

	Category findByName(String name);
}
