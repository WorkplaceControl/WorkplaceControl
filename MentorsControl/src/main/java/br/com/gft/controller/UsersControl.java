package br.com.gft.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.RatePrf;
import br.com.gft.MentorsCommon.UserRole;
import br.com.gft.MentorsCommon.Users;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.MentorsService.UserRoleService;
import br.com.gft.MentorsService.UsersService;
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
	 * this method is to show Users page to read Users informations
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Users", method = RequestMethod.GET)
	public String showUsers(@RequestParam(value = "page", required = false) Integer page, Model model) {
		Pagination pagination = new Pagination(service.getUsers().size(), page);

		model.addAttribute("url", "Users");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Users", service.getUsers(pagination.getBegin(), pagination.getQuantity()));

		return "Users";
	}

	/**
	 * this method is to register user
	 * 
	 * @param username
	 * @param pass
	 * @param repass
	 * @param userRole
	 * @param redirAttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ProcessUser", method = RequestMethod.POST)
	public String ProcessUser(
			@RequestParam(Paths.ATTRIBUTE_UC_USERNAME) String username,
			@RequestParam(Paths.ATTRIBUTE_UC_PASS) String pass,
			@RequestParam(Paths.ATTRIBUTE_UC_REPASS) String repass,
			@RequestParam(Paths.ATTRIBUTE_UC_USER_ROLE) int userRole, 
			RedirectAttributes redirAttr) throws Exception {
		
		List<Users> users = service.getUsers();
		
		int test = 0;
		
		for (int i = 0; i < users.size(); i++) {
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
				user.setUserRole(new UserRoleService().getUserRole(userRole));
				user.setEnable(1);
				
				service.addUser(user);

				usersControlMessage = 0;
			} else {
				usersControlMessage = 1;
			}
		} else {
			usersControlMessage = 2;
		}
		
		redirAttr.addFlashAttribute(Paths.ATTRIBUTE_UC_USERS_CONTROL_MESSAGE, usersControlMessage);
		
		return "redirect:Users";
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
	@RequestMapping(value = "/UpdateUser", method = RequestMethod.POST)
	public String UpdateUser(
			@RequestParam(Paths.ATTRIBUTE_UC_USERNAME) String username,
			@RequestParam(Paths.ATTRIBUTE_UC_NEW_PASS) String pass,
			@RequestParam(Paths.ATTRIBUTE_UC_REP_PASS) String repass,
			@RequestParam(Paths.ATTRIBUTE_UC_OLD_PASS) String oldpass,
			@RequestParam("redirect") String URL,
			RedirectAttributes redirAttr) throws Exception {

		String[] path = URL.split("/");
		String page = path[path.length - 1];
		List<Users> users = service.getUsers();
		user = service.getUser(username);
		int usersControlMessage;
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
					usersControlMessage = 3;
				} else {
					usersControlMessage = 4;
				}

			} else {
				usersControlMessage = 5;
			}
		} else {
			usersControlMessage = 6;
		}

		redirAttr.addFlashAttribute(Paths.ATTRIBUTE_UC_USERS_CONTROL_MESSAGE, usersControlMessage);
		
		return "redirect:" + (page.equals("") || page == null ? "mainPage" : page);
	}

	/**
	 * this method is enable/disable user
	 * 
	 * @param username
	 * @param redirAttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UserStatus", method = RequestMethod.POST)
	public String UserStatus(
			@RequestParam(Paths.ATTRIBUTE_UC_USERNAME) String username,
			@RequestParam("status") int status,
			@RequestParam("redirect") String URL,
			RedirectAttributes redirAttr) throws Exception {
		
		String[] path = URL.split("/");
		String page = path[path.length - 1];
		List<Users> users = service.getUsers();
		user = service.getUser(username);
		int usersControlMessage;
		int action = 0;

		for (int i = 0; i < users.size(); i++) {
			if (username.equals(users.get(i).getUsername())) {
				user = users.get(i);
				action = 1;
			}
		}
		
		if (action == 1) {
			user.setEnable(status);
			service.alterUser(user);
			usersControlMessage = 7;
		} else {
			usersControlMessage = 8;
		}	
		redirAttr.addFlashAttribute(Paths.ATTRIBUTE_UC_USERS_CONTROL_MESSAGE, usersControlMessage);
		return "redirect:" + (page.equals("") || page == null ? "mainPage" : page);
	}

	/**
	 * this method is to show user registration page
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UserRegistration", method = RequestMethod.GET)
	public String showEmployeeRegistration(@ModelAttribute("Users") Users user, Model model) {
		model.addAttribute("userRoles", new UserRoleService().getUserRoles());
		
		System.out.println(new UserRoleService().getUserRoles());
		
		return "UserRegistration";
	}

}

