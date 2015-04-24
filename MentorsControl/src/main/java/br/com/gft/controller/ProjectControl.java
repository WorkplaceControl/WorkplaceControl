package br.com.gft.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
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
import br.com.gft.share.SystemLogs;

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
	public String showProject(@RequestParam(value = "page", required = false) Integer page,
							  @RequestParam(value = "s", required = false) String search, Model model) {
		
		Pagination pagination = null;

		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getProjects(search).size(), page);
			model.addAttribute("Project", service.getPagedProjects(search, pagination.getBegin(), pagination.getQuantity()));
		}else {
			pagination = new Pagination(service.getProjects().size(), page);
			model.addAttribute("Project", service.getPagedProjects(pagination.getBegin(), pagination.getQuantity()));
		}

		
		model.addAttribute("url", "Project");
		model.addAttribute("pagination", pagination);
		
		return "Project";
	}
	
	/**
	 * this method is to get Projects including inactive
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ProjectInactive" , method = RequestMethod.GET)
	public String showProjectInactive(@RequestParam(value = "page", required = false) Integer page,
									  @RequestParam(value = "s", required = false) String search, Model model) {
		
			Pagination pagination = null;
			
			if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getProjectsInactive(search).size(), page);
			model.addAttribute("Project", service.getPagedProjectsInactive(search, pagination.getBegin(), pagination.getQuantity()));
			}else {
			pagination = new Pagination(service.getProjectsInactive().size(), page);
			model.addAttribute("Project", service.getPagedProjectsInactive(pagination.getBegin(), pagination.getQuantity()));
			}		
			
		model.addAttribute("url", "ProjectInactive");
		model.addAttribute("pagination", pagination);
		
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
		List<Customer> customer = service.getCustomers();
		model.addAttribute("customer" , customer);
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
	public String processProjectForm(@RequestParam("description") String description, @RequestParam("customer") String customerId, Model model) {
		Customer customer = new CustomerService().getCustomer(customerId);
		
		project.setDescription(description);
		project.setActive(0);
		project.setCustomer(customer);
		
		service.addProject(project);
		
		showProject(null, null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ADDED the Project (Description): " + description.toUpperCase());
		return "Project";
	}
	
	/**
	 * this method is to update projects 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProjectUpdate", method = RequestMethod.GET)
	public String ShowProjectRegistration(@RequestParam(value="id") String id, Model model) {
		//index = id;
		//project.setActive(0);
		
		model.addAttribute("customer" , service.getCustomers());
		model.addAttribute("Project" , service.getProject(id));
		
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
	public String ProcessProjectUpdate(@RequestParam("description") String description, 
									   @RequestParam("customer") String customerId, Model model) {
		
		Customer customer = new CustomerService().getCustomer(customerId);
		
		project.setId(index);
		project.setDescription(description);
		project.setActive(0);
		project.setCustomer(customer);
		service.alterProject(project);
		
		showProject(null, null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTER the Project (Description): " + description.toUpperCase());
		
		return "Project";
	}
	
	/**
	 * this method is to inactive a project
	 * 
	 * @param projectId
	 * @param customerId
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ProjectStatus", method=RequestMethod.POST)
	public String ProcessProjectInactivate(@RequestParam("id") String id,
			@RequestParam("status") int status,
			Model model) {
		int ControlMessages;
		project = service.getProject(id);

		if (project != null) {
			project.setActive(status == 1 ? 0 : 1);
			
			service.alterProject(project);
			
			ControlMessages = 7;
		} else {
			ControlMessages = 8;
		}
		
		showProject(null, null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 1 ? " ENABLE" : " DISABLE") + " the Project (ID): " + id);
		return "Project";
	}
	

}

