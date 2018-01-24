package com.vh.forum.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vh.forum.domain.User;

/**
 * 
 * @author Gekson.Silva
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMongoRepositoryTest {
	@Autowired
    private UserMongoRepository userMongoRepository;

	@Before
    public void setUp() throws Exception {
        User user1= new User("Alice", "alice", "alice", "ROLE_USER");
        User user2= new User("Bob", "bob", "bob", "ROLE_USER");        
        assertNull(user1.getId());
        assertNull(user2.getId());
        this.userMongoRepository.save(user1);
        this.userMongoRepository.save(user2);
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
    } 
 
	 @Test
	    public void testFetchName(){	        
	        User userA = userMongoRepository.findByName("Bob");
	        assertNotNull(userA);
	        assertEquals("Bob", userA.getName());
	        
	        Iterable<User> users = userMongoRepository.findAll();
	        int count = 0;
	        for(User u : users){
	            count++;
	            System.out.println(u);
	        }
	        assertEquals(count, 2);
	    }
	 
    @Test
    public void testNameUpdate(){
        
        User userB = userMongoRepository.findByName("Bob");
        userB.setName("Bob2");
        userMongoRepository.save(userB);
        User userC= userMongoRepository.findByName("Bob2");
        assertNotNull(userC);
        assertEquals("Bob2", userC.getName());
    }
 
    @After
    public void tearDown() throws Exception {
      this.userMongoRepository.deleteAll();
    }

}
