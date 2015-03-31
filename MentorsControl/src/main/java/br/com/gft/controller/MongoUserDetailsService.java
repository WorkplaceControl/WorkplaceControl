package br.com.gft.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import br.com.gft.MentorsCommon.Users;
import br.com.gft.MentorsService.UsersService;
import br.com.gft.share.UserRoleInfo;


/** Class responsible for authenticating the user via Custom UserDetailsService */
@Controller
public class MongoUserDetailsService implements UserDetailsService {

	/* New instance of Spring Data's User */
	private org.springframework.security.core.userdetails.User userdetails;

	UsersService userserv = new UsersService();

	/**
	 * Method that authenticates the user receiving the username and returns a
	 * valid Spring Data's User
	 */

	public UserDetails loadUserByUsername(String username){
		// New Login object
		Users user = new Users();
		
		
		/* If MongoTemplate bean is null, instantiates a new one */
		List<Users> users = userserv.getUsers();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				user = users.get(i);
			}
		}
		/*
		 * Creates a list of GrantedAuthority and add the default
		 * SimpleGrantedAuthority
		 */
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		// Set type of permission
		if (user.getUserRole() == UserRoleInfo.Admin.getIndex() ) {
			authorities.add(new SimpleGrantedAuthority(UserRoleInfo.Admin.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRoleInfo.User.getValue()));
		}	

		// verify enable/disable account
		if (user.getEnable() == 1) {
			
			/* Pass the values to the User object and returns it */
			userdetails = new User (user.getUsername(), user.getPassword(), authorities);
			return userdetails;
			
			}else{
				
			/* Pass the values to the User object and returns it */
			userdetails = null;
			return userdetails;	
			}
		}
	}	