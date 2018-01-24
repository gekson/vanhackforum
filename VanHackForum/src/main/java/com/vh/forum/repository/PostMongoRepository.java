package com.vh.forum.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vh.forum.domain.Post;

/**
 * 
 * @author Gekson.Silva
 *
 */
public interface PostMongoRepository extends MongoRepository<Post, Integer> {
	Post findByTitle(String name);

	Post findById(String id);
}
