package br.com.gft.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.ProjectService;
import br.com.gft.MentorsService.UnitService;

/**
 * this Class is to control request and responses of Project pages
 * @author mlav
 *
 */
@Controller
public class ProjectControl {
	private int index;

	/**
	 * this method is to show job page to read projects registered
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/Project" , method = RequestMethod.GET)
	public String showProject(Model model){
		ProjectService projectservice = new ProjectService();
		List<Project> project = projectservice.getProjects();
		model.addAttribute("Project" , project);
		return "Project";
	}
	
	/**
	 * this method is to get Projects including inactive
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ProjectInactive" , method = RequestMethod.GET)
	public String showProjectInactive(Model model){
		ProjectService projectservice = new ProjectService();
		List<Project> project = projectservice.getProjectsInactive();
		model.addAttribute("Project" , project);
		return "Project";
	}

	/**
	 * this method show page of Project registration
	 * @param project
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ProjectRegistration" , method = RequestMethod.GET)
	public String showProjectRegistration(@ModelAttribute("Project") Project project , Model model){
		ProjectService projectservice = new ProjectService();
		List<Customer> cus = projectservice.getCustomers();
		model.addAttribute("customer" , cus);
		return "ProjectRegistration";
	}

	/**
	 * this method is to register Project
	 * @param description
	 * @param customerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ProcessProjectRegistration", method=RequestMethod.POST)
	public String processProjectForm(@RequestParam("description") String description, @RequestParam("customer") int customerId, Model model) {
		Customer customer = new CustomerService().getCustomer(customerId);
		Project project = new Project();
		project.setDescription(description);
		project.setDescription(description);
		project.setActive(0);
		project.setCustomer(customer);
		ProjectService projectservice = new ProjectService();
		projectservice.addProject(project);
		showProject(model);
		return "Project";
	}
	
	/**
	 * this method is to update projects 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProjectUpdate", method = RequestMethod.GET)
	public String ShowProjectRegistration(@RequestParam(value="id") int id, Model model) {
		Project project = new Project();
		index = id;
		project.setActive(0);
		ProjectService projectservice = new ProjectService();
		project = projectservice.getProject(id);
		List<Customer> cus = projectservice.getCustomers();
		model.addAttribute("customer" , cus);
		model.addAttribute("Project" , project);
		return "ProjectUpdate";
	}
	
	/**
	 * this method is to register a update of informations about a project
	 * @param description
	 * @param customerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessProjectUpdate", method=RequestMethod.POST)
	public String ProcessProjectUpdate(@RequestParam("description") String description, @RequestParam("customer") int customerId , Model model) {
		Customer customer = new CustomerService().getCustomer(customerId);
		Project project = new Project();
		project.setId(index);
		project.setDescription(description);
		project.setActive(0);
		project.setCustomer(customer);
		ProjectService projectservice = new ProjectService();
		projectservice.alterProject(project);
		showProject(model);
		return "Project";
	}
	
	/**
	 * this method is to inactive a project
	 * @param projectId
	 * @param customerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessProjectInactivate", method=RequestMethod.GET)
	public String ProcessProjectInactivate( @RequestParam("id") int projectId , @RequestParam("customer") int customerId , Model model) {
		Customer customer = new CustomerService().getCustomer(customerId);
		Project project = new Project();
		ProjectService projectservice = new ProjectService();
		project = projectservice.getProject(projectId);
		project.setCustomer(customer);
		project.setActive(1);
		projectservice.alterProject(project);
		showProject(model);
		return "Project";
	}
	

}

