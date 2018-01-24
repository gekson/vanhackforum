package com.vh.forum.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vh.forum.domain.User;

/**
 * 
 * @author Gekson.Silva
 *
 */
public interface UserMongoRepository extends MongoRepository<User, Integer> {

	User findByName(String name);

	User findByUsername(String username);
}
