package br.com.gft.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

import br.com.gft.MentorsCommon.Users;
import br.com.gft.MentorsService.UsersService;
import br.com.gft.share.UserRoleInfo;


/** Class responsible for authenticating the user via Custom UserDetailsService */
@Controller
public class MongoUserDetailsService implements UserDetailsService {

	/**
	 * Method that authenticates the user receiving the username and returns a
	 * valid Spring Data's User
	 */

	public UserDetails loadUserByUsername(String username){
		Users user = new UsersService().getUser(username);
		User details = null;
		
		if (user != null) {
			/*
			 * Creates a list of GrantedAuthority and add the default
			 * SimpleGrantedAuthority
			 */
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			if (user.getUserRole().getId() == UserRoleInfo.Admin.getIndex()) {
				authorities.add(new SimpleGrantedAuthority(UserRoleInfo.Admin.getValue()));
			} else {
				authorities.add(new SimpleGrantedAuthority(UserRoleInfo.User.getValue()));
			}

			// verify enable/disable account
			if (user.getEnable() == 1) {
				/* Pass the values to the User object and returns it */
				details = new User(user.getUsername(), user.getPassword(), authorities);
			}
		}
		
		return details;
	}
}	