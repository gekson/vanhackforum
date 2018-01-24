package com.vh.forum.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vh.forum.domain.Commentary;

/**
 * 
 * @author Gekson.Silva
 *
 */
public interface CommentaryMongoRepository extends MongoRepository<Commentary, Integer> {
	Commentary findById(String id);
}
