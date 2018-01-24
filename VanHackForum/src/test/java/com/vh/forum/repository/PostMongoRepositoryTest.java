package com.vh.forum.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vh.forum.domain.Category;
import com.vh.forum.domain.Commentary;
import com.vh.forum.domain.Post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostMongoRepositoryTest {

	@Autowired
	private CategoryMongoRepository categoryMongoRepository;

	@Autowired
	private PostMongoRepository postMongoRepository;

	@Before
	public void setUp() throws Exception {
		Category category1 = new Category("Tech");
		Category category2 = new Category("Social");
		assertNull(category1.getId());
		assertNull(category2.getId());
		this.categoryMongoRepository.save(category1);
		this.categoryMongoRepository.save(category2);
		assertNotNull(category1.getId());
		assertNotNull(category2.getId());
				
		Post post = new Post("Test Post", "This is a test!", category1, LocalDateTime.now(), null);
		postMongoRepository.save(post);
				
		Post post2 = new Post("Test Post2", "This is a test 2!", category2, LocalDateTime.now(), null);
		postMongoRepository.save(post2);
	}

	@Test
	public void testSavePost() {
		Category category1 = categoryMongoRepository.findByName("Tech");
		Post post = new Post("Test Post3", "This is a test3!", category1, LocalDateTime.now(), null);
		postMongoRepository.save(post);
		assertNotNull(post.getId());
	}
	
	@Test
	public void testPostByTitle() {
		Post post = postMongoRepository.findByTitle("Test Post");
        assertNotNull(post);
        assertEquals("Test Post", post.getTitle());        
	}
	
	@Test
	public void testFetchAllPosts() {				
		Iterable<Post> posts = postMongoRepository.findAll();
		int count = 0;
		for (Post p : posts) {
			count++;
			System.out.println("Post -> "+p);
		}
		assertEquals(count, 2);
	}
	
	@Test
	public void testAddCommentary() {
		Post post = postMongoRepository.findByTitle("Test Post");
		Commentary commentary = new Commentary("Test Commentary", null);
		
		post.getComments().add(commentary);
		postMongoRepository.save(post);
		System.out.println("Post with Commentary ->" + post);
		
		Post post2 = postMongoRepository.findByTitle("Test Post");
		assertEquals(1, post2.getComments().size());
	}
	
	@Test
	public void testDeleteCommentary() {
		Post post = postMongoRepository.findByTitle("Test Post");
		post.getComments().remove(0);
		assertEquals(1, post.getComments().size());
	}
	
	@After
	public void tearDown() throws Exception {
		this.postMongoRepository.deleteAll();
	}

}
