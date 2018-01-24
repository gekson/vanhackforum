package com.vh.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vh.forum.repository.UserMongoRepository;

/**
 * 
 * @author Gekson.Silva
 *
 */
@SpringBootApplication
//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
public class VanHackForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(VanHackForumApplication.class, args);
	}
		
}

@RestController
class GreetingController {
	@Autowired
    private UserMongoRepository userMongoRepository;
	
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!" + userMongoRepository.findAll();
    }
}