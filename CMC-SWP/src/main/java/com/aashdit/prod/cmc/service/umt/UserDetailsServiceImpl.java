package com.aashdit.prod.cmc.service.umt;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aashdit.prod.cmc.model.umt.LoggedInUser;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.repository.umt.UserRepository;

@Component(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(username);
		if (user != null) {
			/*
			  if (user.getAllowMultipleSession() == false) { if (user.getIsLoggedIn() ==
			  true) { logger.
			  debug("User already logged into system and ALLOW_MULTIPLE_SESSION is false -> "
			  + username); throw new
			  SessionAuthenticationException("You are already logged into the system. Please log out."
			  ); } }
			 */
			/*
			if (user.getEmailVerified() == false)
			{
				throw new SessionAuthenticationException("Your Email has not been verified. Please verify your email");
			}
			*/
		} else {
			logger.debug("No user found with user name -> " + username);
			throw new SessionAuthenticationException("Please check your credentials. Either Username or Password is wrong");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getPrimaryRole().getRoleCode()));

		LoggedInUser liu = new LoggedInUser(username, user.getPassword(), true, true,
				true, true, grantedAuthorities, user.getPrimaryRole(), user);
		
		return liu;

	}
	
	
}
