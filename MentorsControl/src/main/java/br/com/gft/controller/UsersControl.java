package br.com.gft.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;

import org.omg.CORBA.Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.CookieGenerator;

import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.UserRole;
import br.com.gft.MentorsCommon.Users;
import br.com.gft.MentorsService.UsersService;
import br.com.gft.share.UserRoleInfo;
import br.com.gft.share.Paths;

/**
 * this Class is to control requests and responses of User(CRUD)
 * 
 * @author mlav
 * 
 */
@Controller
public class UsersControl {
	UsersService userserv = new UsersService();
	Users user = new Users();



	/**
	 * this method is to verify authentication to add user
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/", "", "/Login"})
	public String processLogin(Model model) {
		
		return "Login";
	}
	

	/**
	 * this method is to deny access when user don`t have privileges
	 * 
	 * @return
	 */
	@RequestMapping(value = "/accessdenied")
	public String noAccess() {
		return "accessdenied";
	}

	/**
	 * this method is to register user
	 * 
	 * @param username
	 * @param pass
	 * @param repass
	 * @param userRole
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ProcessUser", method = RequestMethod.POST)
	public String ProcessUser(
			@RequestParam("username") String username,
			@RequestParam("pass") String pass,
			@RequestParam("repass") String repass,
			@RequestParam("userRole") int userRole,
			Model model)
			throws Exception {
		List<Users> users = userserv.getUsers();
		int test = 0;
		for (int i = 0; i < users.size(); i++) {
			user = userserv.getUser(username);
			if (username.equals(users.get(i).getUsername())) {
				test = 1;
			}
		}
		
		Users user = new Users();
		int usersControlMessage;
		if (test != 1) {
			if (pass.equals(repass)) {
				user.setUsername(username);
				user.setPassword(pass);
				user.setUserRole(userRole);
				user.setEnable(1);
				userserv.addUser(user);
	
				usersControlMessage = 0;
			} else {
				usersControlMessage = 1;
			}
		}else{
			usersControlMessage = 6;
		}
		model.addAttribute("usersControlMessage", usersControlMessage);
		return "mainPage";
	}

	/**
	 * this method is update password
	 * 
	 * @param username
	 * @param pass
	 * @param repass
	 * @param oldpass
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UpdateUser", method = RequestMethod.POST)
	public String UpdateUser(
			@RequestParam("username") String username,
			@RequestParam("newPass") String pass,
			@RequestParam("repPass") String repass,
			@RequestParam("oldPass") String oldpass, Model model)
			throws Exception {
		List<Users> users = userserv.getUsers();
		user = userserv.getUser(username);
		int usersControlMessage;
		int test = 0;
		System.out.println("pass : " + pass);
		System.out.println("repass : " + repass);
		System.out.println("oldpass : " + oldpass);
		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				user = users.get(i);
				test = 1;
			}
		}
		System.out.println("bd oldpass : " + user.getPassword());
		if (test == 1) {
			if (oldpass.equals(user.getPassword())) {
				if (pass.equals(repass)) {
					user.setUsername(username);
					user.setPassword(pass);
					user.setEnable(1);
					userserv.alterUser(user);
					usersControlMessage = 2;
				} else {
					usersControlMessage = 3;
				}

			} else {
				usersControlMessage = 4;
			}
		} else {
			usersControlMessage = 5;
		}
		model.addAttribute("usersControlMessage", usersControlMessage);
		return "mainPage";
	}

}
