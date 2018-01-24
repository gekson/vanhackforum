package com.vh.forum.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vh.forum.domain.Category;
import com.vh.forum.domain.Commentary;
import com.vh.forum.domain.Post;
import com.vh.forum.domain.User;
import com.vh.forum.repository.CategoryMongoRepository;
import com.vh.forum.repository.CommentaryMongoRepository;
import com.vh.forum.repository.PostMongoRepository;
import com.vh.forum.repository.UserMongoRepository;

@Component
public class DatabaseLoader {

	@Autowired
    private UserMongoRepository userMongoRepository;
	
	@Autowired
	private CategoryMongoRepository categoryMongoRepository;
	
	@Autowired
	private PostMongoRepository postMongoRepository;
	
	@Autowired
	private CommentaryMongoRepository commentaryMongoRepository;
	
	@EventListener
    public void appReady(ApplicationReadyEvent event) {

//		userMongoRepository.save(new User("Bobby", "user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "ROLE_USER"));
//		userMongoRepository.save(new User("Mike", "mike", "mike", "ROLE_USER"));
		User user1= new User("Alice", "user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "ROLE_USER");
        User user2= new User("Bob", "admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ROLE_ADMIN");     
        userMongoRepository.save(user1);
        userMongoRepository.save(user2);
        System.out.println(user1);
        System.out.println(user2);
		
		categoryMongoRepository.save(new Category("Tech"));
		categoryMongoRepository.save(new Category("Social"));
		
		Category category1 = categoryMongoRepository.findByName("Tech");
		Post post = new Post("Test Post", "This is a test!", category1, LocalDateTime.now(), null);
		Commentary commentary = new Commentary("Test Commentary", null);
		Commentary commentary2 = new Commentary("Test Commentary2", null);	
		commentary.setUser(user2);		
		commentaryMongoRepository.save(commentary);
		commentaryMongoRepository.save(commentary2);
		post.getComments().add(commentary);
		post.getComments().add(commentary2);
		post.setUser(user1);		
		postMongoRepository.save(post);
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("mike", "doesn't matter",
					AuthorityUtils.createAuthorityList("ROLE_USER")));
    }
}
