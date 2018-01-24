package com.vh.forum.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vh.forum.domain.Commentary;
import com.vh.forum.domain.Post;
import com.vh.forum.domain.User;
import com.vh.forum.repository.PostMongoRepository;
import com.vh.forum.repository.UserMongoRepository;

/**
 * 
 * @author Gekson.Silva
 *
 */
@Service
public class PostService {

	@Autowired
	private PostMongoRepository postMongoRepository;
	
	@Autowired
	private UserMongoRepository userMongoRepository;
	
	@Autowired
	private CommentaryService commentaryService;
	
	public List<Post> findAllPosts() {
		return postMongoRepository.findAll();
	}
	
	public Post save(Post post) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		User user = userMongoRepository.findByUsername(auth.getName());
		post.setDateRegister(LocalDateTime.now());	
		post.setUser(user);
		return postMongoRepository.save(post);
	}

	public Post findById(String id) {		
		return postMongoRepository.findById(id);
	}

	public Boolean delete(String id) {
		Post post = postMongoRepository.findById(id);
		try {
			postMongoRepository.delete(post);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public Boolean deleteCommentary(String postId, String id) {
		try {
			Post post = postMongoRepository.findById(postId);
			Commentary commentary = commentaryService.findById(id);
			post.getComments().remove(commentary);
			if (commentaryService.delete(commentary)) {
				postMongoRepository.save(post);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
}
