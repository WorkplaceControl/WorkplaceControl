package br.com.gft.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.MentorsCommon.Users;
import br.com.gft.MentorsService.UserRoleService;
import br.com.gft.MentorsService.UsersService;
import br.com.gft.logs.SystemLogs;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;

/**
 * this Class is to control requests and responses of User(CRUD)
 * 
 * @author mlav
 * 
 */
@Controller
public class UsersControl {

	UsersService service = new UsersService();
	Users user = new Users();

	/**
	 * this method is to verify authentication to add user
	 * 
	 * @return
	 */
	@RequestMapping(value = {"/Login", "/", ""}, method = RequestMethod.GET)
	public String processLogin( ) {
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
	 * this method is to return General error page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/GeneralError")
	public String GeneralError() {
		return "GeneralError";
	}

	/**
	 * this method is to return 400 error page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/400Error")
	public String Error400() {
		return "400Error";
	}


	/**
	 * this method is to show Users page to read inactive Users informations
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Users", method = RequestMethod.GET)
	public String showUsers(@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "s", required = false) String search, 
			Model model) {
		Pagination pagination = new Pagination(service.getUsers().size(), page);
		
		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getUsers(search).size(), page);
		}

		model.addAttribute("url", "Users");
		model.addAttribute("pagination", pagination);
		
		if (search != null && !search.equals("")) {
			model.addAttribute("Users", service.getUsers(search, pagination.getBegin(), pagination.getQuantity()));
		} else {
			model.addAttribute("Users", service.getUsers(pagination.getBegin(), pagination.getQuantity()));
		}

		return "Users";
	}
	
	/**
	 * this method is to show Users page to read inactive Users informations
	 * 
	 * @return
	 */
	@RequestMapping(value = "/UsersInactive", method = RequestMethod.GET)
	public String showUsersInactive(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "s", required = false) String search,  
			Model model) {
		Pagination pagination = new Pagination(service.getUsersInactive().size(), page);
		
		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getUsersInactive(search).size(), page);
		}

		model.addAttribute("url", "UsersInactive");
		model.addAttribute("pagination", pagination);
		
		if (search != null && !search.equals("")) {
			model.addAttribute("Users", service.getUsersInactive(search, pagination.getBegin(), pagination.getQuantity()));
		} else {
			model.addAttribute("Users", service.getUsersInactive(pagination.getBegin(), pagination.getQuantity()));
		}

		return "Users";
	}

	/**
	 * this method is to register user
	 * 
	 * @param username
	 * @param pass
	 * @param repass
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ProcessUser", method = RequestMethod.POST)
	public String ProcessUser(
			@RequestParam(Paths.ATTRIBUTE_UC_USERNAME) String username,
			@RequestParam(Paths.ATTRIBUTE_UC_PASS) String pass,
			@RequestParam(Paths.ATTRIBUTE_UC_REPASS) String repass,
			@RequestParam(Paths.ATTRIBUTE_UC_USER_ROLE) int userRole, 
			Model model) throws Exception {
		
		List<Users> users = service.getUsers();
		
		int test = 0;
		
		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				test = 1;
			}
		}

		Users user = new Users();
		int ControlMessages;
		
		if (test != 1) {
			if (pass.equals(repass)) {
				user.setUsername(username);
				user.setPassword(pass);
				user.setUserRole(new UserRoleService().getUserRole(userRole));
				user.setEnable(1);
				
				service.addUser(user);

				ControlMessages = 0;
			} else {
				ControlMessages = 1;
			}
		} else {
			ControlMessages = 2;
		}
		
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		showUsers(null, null, model);
		
		new SystemLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o User (Username): " + username.toUpperCase());
		return "Users";
	}

	/**
	 * this method is update password
	 * 
	 * @param username
	 * @param pass
	 * @param repass
	 * @param oldpass
	 * @param redirAttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ProcessUserUpdate", method = RequestMethod.POST)
	public String UpdateUser(
			@RequestParam("username") String username,
			@RequestParam("pass") String pass,
			@RequestParam("repass") String repass,
			@RequestParam("userRole") int userRole, 
			Model model) throws Exception {

		List<Users> users = service.getUsers();
		int ControlMessages;
		int action = 0;

		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				user = users.get(i);
				action = 1;
			}
		}

		if (action == 1) {
			if (pass.equals(repass)) {
				user.setUsername(username);
				
				if (pass.equals("")) {
					user.setPassword(new UsersService().getUser(username).getPassword());
				} else {
					user.setPassword(pass);
				}
				
				user.setUserRole(new UserRoleService().getUserRole(userRole));
				user.setEnable(1);
				
				service.alterUser(user);
				
				ControlMessages = 3;
			} else {
				ControlMessages = 4;
			}
		} else {
			ControlMessages = 6;
		}

		showUsers(null, null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		new SystemLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTEROU o User (Username): " + username.toUpperCase());
		return "Users";
	}

	/**
	 * this method is enable/disable user
	 * 
	 * @param id
	 * @param status
	 * @param redirAttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UserStatus", method = RequestMethod.GET)
	public String UserStatus(
			@RequestParam("username") String username,
			@RequestParam("status") int status,
			Model model) throws Exception {
		
		user = service.getUser(username);
		int ControlMessages;
		int action = user != null ? 1 : 0;

		if (action == 1) {
			user.setEnable(status == 1 ? 0 : 1);
			
			service.alterUser(user);
			
			ControlMessages = 7;
		} else {
			ControlMessages = 8;
		}
		
		showUsers(null, null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		new SystemLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 0 ? " ATIVOU" : " DESATIVOU") + " o User (Username): " + username.toUpperCase());
		return "Users";
	}

	/**
	 * this method is to show user registration page
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UserRegistration", method = RequestMethod.GET)
	public String showUserRegistration(@ModelAttribute("Users") Users user, Model model) {
		model.addAttribute("userRoles", new UserRoleService().getUserRoles());
		
		return "UserRegistration";
	}
	
	/**
	 * this method is to show user update page
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UserUpdate", method = RequestMethod.GET)
	public String showUserUpdate(@RequestParam("username") String username, Model model) {
		model.addAttribute("user", new UsersService().getUser(username));
		model.addAttribute("userRoles", new UserRoleService().getUserRoles());
		
		return "UserUpdate";
	}

	/**
	 * this method is update password
	 * 
	 * @param username
	 * @param pass
	 * @param repass
	 * @param oldpass
	 * @param redirAttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UpdateUserPassword", method = RequestMethod.POST)
	public String UpdateUserPassword(
			@RequestParam(Paths.ATTRIBUTE_UC_USERNAME) String username,
			@RequestParam(Paths.ATTRIBUTE_UC_NEW_PASS) String pass,
			@RequestParam(Paths.ATTRIBUTE_UC_REP_PASS) String repass,
			@RequestParam(Paths.ATTRIBUTE_UC_OLD_PASS) String oldpass,
			@RequestParam("redirect") String URL,
			@RequestParam(value = "query", required = false) String query,
			RedirectAttributes redirAttr) throws Exception {

		String[] path = URL.split("/");
		String page = path[path.length - 1] + (!query.equals("null") && query != null ? "?" + query : "");
		
		List<Users> users = service.getUsers();
		user = service.getUser(username);
		
		int ControlMessages;
		int action = 0;

		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				user = users.get(i);
				action = 1;
			}
		}

		if (action == 1) {
			if (oldpass.equals(user.getPassword())) {
				if (pass.equals(repass)) {
					user.setUsername(username);
					user.setPassword(pass);
					user.setEnable(1);
		
					service.alterUser(user);
					
					ControlMessages = 3;
				} else {
					ControlMessages = 4;
				}
			} else {
				ControlMessages = 5;
			}
		} else {
			ControlMessages = 6;
		}

		redirAttr.addFlashAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		return "redirect:" + (page.equals("") || page == null ? "mainPage" : page);
	}
}

