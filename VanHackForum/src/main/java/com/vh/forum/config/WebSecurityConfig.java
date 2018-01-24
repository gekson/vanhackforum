package com.vh.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.vh.forum.domain.User;
import com.vh.forum.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan("com.vh.forum")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        
      http
        .authorizeRequests().antMatchers("/css/**", "/posts").permitAll() // Enable css when logged out

          .and()
        .authorizeRequests()
          .antMatchers("/post/delete/{id}", "/post/list", "/post/save", "/post/add")
            .permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/post/list")
            .permitAll()
            .and()
        .logout()
            .permitAll()
            .and()
        .httpBasic()
            .and()
        .csrf().disable(); //Disable CSRF

    }
     
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {  
    	    	auth.userDetailsService(userDetailsService).passwordEncoder(User.PASSWORD_ENCODER);
    }
       
}
