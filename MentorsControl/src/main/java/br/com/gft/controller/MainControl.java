package br.com.gft.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.logs.SystemLogs;
import br.com.gft.share.Paths;
import br.com.gft.share.UserRoleInfo;

/**
 * this Class is to control request and responses to main page
 * 
 * @author mlav
 *
 */
@Controller
public class MainControl {
	/**
	 * this method is to show main page and show pending , errors and news about
	 * employees
	 * 
	 * @param model
	 * @return
	 */
	
	Calendar date = Calendar.getInstance();
	int roleUser;
	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String showHome(Model model, HttpServletRequest request) {
		List<Employee> employeeError = new EmployeeService().employeeVerify();
		List<Employee> employeePending = new EmployeeService()
				.EmployeePending();
		List<Employee> newEmployees = new EmployeeService().newEmployees();
		int qtyErrors = employeeError.size();
		int qtyPendings = employeePending.size();
		int qtyNews = newEmployees.size();

				Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		
		for(GrantedAuthority authorities : auth.getAuthorities()) {
			if(authorities.getAuthority().equals(UserRoleInfo.Admin.getValue())) {
				roleUser = UserRoleInfo.Admin.getIndex();
			} else {
				roleUser = UserRoleInfo.User.getIndex();
			}
		}
		
		final String user = auth.getName();
		request.getSession().setAttribute(Paths.ATTRIBUTE_MC_USER, user);
		request.getSession().setAttribute(Paths.ATTRIBUTE_MC_ROLE_USER, roleUser);
		model.addAttribute(Paths.ATTRIBUTE_MC_QTY_NEWS, qtyNews);
		model.addAttribute(Paths.ATTRIBUTE_MC_NEW_EMPLOYEE, newEmployees);
		model.addAttribute(Paths.ATTRIBUTE_MC_QTY_PENDING, qtyPendings);
		model.addAttribute(Paths.ATTRIBUTE_MC_EMPLOYEE_PENDING, employeePending);
		model.addAttribute(Paths.ATTRIBUTE_MC_QTY_ERRORS, qtyErrors);
		model.addAttribute(Paths.ATTRIBUTE_MC_EMPLOYEE_ERROR, employeeError);
		
//		new SystemLogs("Date:");
//		new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " Conectou.");
			

		return "mainPage";

	}

}