package com.vh.forum.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vh.forum.domain.Commentary;
import com.vh.forum.domain.Post;
import com.vh.forum.domain.User;
import com.vh.forum.repository.CommentaryMongoRepository;
import com.vh.forum.repository.UserMongoRepository;

/**
 * 
 * @author Gekson.Silva
 *
 */
@Service
public class CommentaryService {

	@Autowired
	private CommentaryMongoRepository commentaryMongoRepository;
	@Autowired
	private PostService postService;
	@Autowired
	private UserMongoRepository userMongoRepository;
	
	public Boolean delete(Commentary commentary) {
		try {
			commentaryMongoRepository.delete(commentary);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Commentary findById(String id) {		
		return commentaryMongoRepository.findById(id);
	}

	@Transactional
	public Boolean save(Commentary commentary) {
		try {
			Post post = postService.findById(commentary.getPostId());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			User user = userMongoRepository.findByUsername(auth.getName());
			post.setDateRegister(LocalDateTime.now());	
			post.setUser(user);
			
			commentaryMongoRepository.save(commentary);
			post.getComments().add(commentary);
			postService.save(post);
			return true;
		} catch (Exception e) {
			return false;
		} 			
	}
}
