package br.com.gft.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Mentor;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsService.EmployeeAssignService;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.MentorsService.JobService;
import br.com.gft.MentorsService.ProjectService;

/**
 * this Class is to controll requests and responses of reports
 * 
 * @author mlav
 * 
 */
@Controller
public class ReportControl {

	/**
	 * this method is to show a page to select which projects will be at report
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProjectReport", method = RequestMethod.GET)
	public String showProjectReport(Model model) { 
		List<Project> project = new ProjectService().getProjects();

		model.addAttribute("Project", project);
		return "ProjectReport";

	}

	/**
	 * this method is to create a report of Mentee X Mentor
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/MentorReport", method = RequestMethod.GET)
	public String showMentorReport(Model model) {

		List<Mentor> mentor = new EmployeeService().getQtyMentee();
		List<Job> jobs = new JobService().getJobs();
		model.addAttribute("mentor", mentor);
		model.addAttribute("job", jobs);

		return "MentorReportView";

	}
	/**
	 * this method is to create a project X employee report 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/projectReportView", method = RequestMethod.POST)
	public String projectReportView(@RequestParam("projReport") String id,
			Model model) {
		List<Project> projects = new EmployeeService().getSelectedProjects(id);
		List<Employee> employeeLs = new EmployeeService().getEmployees();
		List<EmployeeAssignment> empassigns = new EmployeeAssignService()
				.getEmployeeAssignsAll();
		List<Job> jobs = new JobService().getJobs();
		
		
		model.addAttribute("job", jobs);
		model.addAttribute("empassigns", empassigns);
		model.addAttribute("project", projects);
		model.addAttribute("employeeLs", employeeLs);
		return "ProjectReportView";
	}

}
