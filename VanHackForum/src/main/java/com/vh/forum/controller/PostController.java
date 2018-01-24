package com.vh.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vh.forum.domain.Post;
import com.vh.forum.service.PostService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@Secured("ROLE_USER")
	@RequestMapping("/post/add")
	public String addPost(Model model) {
		model.addAttribute("post", new Post());
		return "addPost";
	}

	@RequestMapping("/post/list")
	public String index(Model model) {
		List<Post> posts = (List<Post>) postService.findAllPosts();
		model.addAttribute("posts", posts);
    	return "posts";
    }

	@RequestMapping("/")
	public String start() {
	 	return"redirect:/post/list";
	}
	
	@GetMapping("/posts")
	public List<Post> findAllPosts() {
		return postService.findAllPosts();
	}		

	@Secured("ROLE_USER")
	@PostMapping("/post/save")	
	public String savePost(Post post) {
		try {					
			postService.save(post);
		} catch (Exception e) {
			return"redirect:/post/add";
		} 		
		return"redirect:/post/list";
	}

	@Secured("ROLE_USER")
	@DeleteMapping("/post/deleteCommentary/{id}")
	public String deleteCommentary(@PathVariable String postId) {
		return "redirect:/listComments/"+postId;
	}
	
	@Secured("ROLE_USER")
	@DeleteMapping("/post/delete/{id}")
	public String delete(@PathVariable String id) {
		return  "redirect:/post/list";
	}
}
