package com.vh.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vh.forum.domain.User;
import com.vh.forum.repository.UserMongoRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	private final UserMongoRepository repository;

	@Autowired
	public UserDetailServiceImpl(UserMongoRepository repository) {
		this.repository = repository;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   
    	User curruser = repository.findByUsername(username);
    	
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(), true, 
        		true, true, true, AuthorityUtils.createAuthorityList(curruser.getRole()));
        
        System.out.println("ROLE: " + curruser.getRole());
        return user;
//        return new org.springframework.security.core.userdetails.User(curruser.getName(), curruser.getPassword(),
//				AuthorityUtils.createAuthorityList(curruser.getRole()));
    }

}
