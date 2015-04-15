package br.com.gft.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.EmployeeAssignService;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.MentorsService.ProjectService;

/**
 * this Class control requisitions and responses of projects linked to employee
 * 
 * @author mlav
 * 
 */
@Controller
public class EmployeeAssignControl {
	private int verify = 0;
	/**
	 * this method control requests and responses to register the relation of the employee to a project
	 * @param projId
	 * @param employeeId
	 * @param jobId
	 * @param costCenterId
	 * @param ratePrfId
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/EmployeeAssignment", method = RequestMethod.POST)
	public String processProjectForm(@RequestParam("project") String projId,
			@RequestParam("id") String employeeId,
			@RequestParam("job") String jobId,
			@RequestParam("cost") String costCenterId,
			@RequestParam("rate") int ratePrfId, Model model) throws Exception {
		EmployeeAssignService empassgnservice = new EmployeeAssignService();
		int verify = empassgnservice.existsRegister(employeeId, projId);

		if (verify == 0) {
			Project project = new ProjectService().getProject(projId);

			Employee employee = new EmployeeService().getEmployee(employeeId);

			EmployeeAssignment employeeassigment = new EmployeeAssignment();
			employeeassigment.setProject(project);
			employeeassigment.setEmployee(employee);
			employeeassigment.setActive(0);

			employee.getEmployeeassignment().add(employeeassigment);

			new EmployeeService().alterEmployee(employee);
		}
		model.addAttribute("verify", verify);
		EmployeeControl empcontrol = new EmployeeControl();
		empcontrol.EmployeeUpdate(employeeId, jobId, costCenterId, ratePrfId,
				model);

		return "EmployeeUpdate";
	}
	
	/**
	 * this method is inactive the relation of the employee to project
	 * @param empAssigId
	 * @param projId
	 * @param employeeId
	 * @param jobId
	 * @param costCenterId
	 * @param ratePrfId
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/EmployeeAssignInactivate", method = RequestMethod.GET)
	public String ProcessProjectInactivate(
			@RequestParam("empAssigId") int empAssigId,
			@RequestParam("project") String projId,
			@RequestParam("id") String employeeId,
			@RequestParam("job") String jobId,
			@RequestParam("cost") String costCenterId,
			@RequestParam("rate") int ratePrfId, Model model) throws Exception {
		Project project = new ProjectService().getProject(projId);
		Employee employee = new EmployeeService().getEmployee(employeeId);
		EmployeeAssignment employeeassigment = new EmployeeAssignment();
		employeeassigment.setId(empAssigId);
		employeeassigment.setProject(project);
		employeeassigment.setEmployee(employee);
		employeeassigment.setActive(1);
		EmployeeAssignService empservice = new EmployeeAssignService();
		empservice.updateEmployeeAssign(employeeassigment);
		EmployeeControl empcontrol = new EmployeeControl();
		empcontrol.EmployeeUpdate(employeeId, jobId, costCenterId, ratePrfId,
				model);
		return "EmployeeUpdate";
	}
}
