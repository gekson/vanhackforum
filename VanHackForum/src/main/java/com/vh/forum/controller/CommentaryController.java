package com.vh.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vh.forum.domain.Commentary;
import com.vh.forum.domain.Post;
import com.vh.forum.service.CommentaryService;
import com.vh.forum.service.PostService;

@Controller
public class CommentaryController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private CommentaryService commentaryService;
	
	@Secured("ROLE_USER")
	@GetMapping("/listComments/{id}")
	public String getCommentsByPostId(@PathVariable String id, Model model) {

		Post post = postService.findById(id);
		model.addAttribute("comments",post.getComments());
		model.addAttribute("post", post);
		return "comments";
	}
	
	@Secured("ROLE_USER")
	@RequestMapping("/commentary/add/{id}")
	public String addCommentary(@PathVariable String id,Model model) {	
		Commentary commentary = new Commentary();
		commentary.setPostId(id);
		model.addAttribute("commentary", commentary);
		model.addAttribute("postId", id);
		return "addCommentary";
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/commentary/save")		
	public String saveCommentary(Commentary commentary) {
		
		if(commentaryService.save(commentary)) {						
			return"redirect:/listComments/"+commentary.getPostId();
		} 		
		return"redirect:/commentary/add/{id}"+commentary.getPostId();
	}
}
