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
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.ProjectService;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;

/**
 * this Class is to control request and responses of Project pages
 * @author mlav
 *
 */
@Controller
public class ProjectControl {
	private int index;
	Project project = new Project();
	ProjectService service = new ProjectService();
	/**
	 * this method is to show job page to read projects registered
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/Project" , method = RequestMethod.GET)
	public String showProject(@RequestParam(value = "page", required = false) Integer page, Model model) {
		ProjectService service = new ProjectService();
		
Pagination pagination = new Pagination(service.getProjectsInactive().size(), page);
		
		model.addAttribute("url", "Projects");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Project", service.getPagedProjects(pagination.getBegin(), pagination.getQuantity()));
		
		return "Project";
	}
	
	/**
	 * this method is to get Projects including inactive
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ProjectInactive" , method = RequestMethod.GET)
	public String showProjectInactive(@RequestParam(value = "page", required = false) Integer page, Model model) {
		ProjectService service = new ProjectService();
		Pagination pagination = new Pagination(service.getProjectsInactive().size(), page);
		
		model.addAttribute("url", "Projects");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Project", service.getPagedProjectsInactive(pagination.getBegin(), pagination.getQuantity()));
		
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
		List<Customer> cus = service.getCustomers();
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
		project.setDescription(description);
		project.setDescription(description);
		project.setActive(0);
		project.setCustomer(customer);
		service.addProject(project);
		showProject(null, model);
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
		index = id;
		project.setActive(0);
		project = service.getProject(id);
		List<Customer> cus = service.getCustomers();
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
		project.setId(index);
		project.setDescription(description);
		project.setActive(0);
		project.setCustomer(customer);
		service.alterProject(project);
		showProject(null, model);
		return "Project";
	}
	
	/**
	 * this method is to inactive a project
	 * @param projectId
	 * @param customerId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProjectStatus", method=RequestMethod.GET)
	public String ProcessProjectInactivate( @RequestParam("id") int id,
											@RequestParam("status") int status , Model model) {
		
		
		project = service.getProject(id);
		int ControlMessages;
		int action = project != null ? 1 : 0;

		if (action == 1) {
			project.setActive(status == 1 ? 0 : 1);
			
			service.alterProject(project);
			
			ControlMessages = 7;
		} else {
			ControlMessages = 8;
		}
		
		showProject(null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		return "Project";
	}
	

}

