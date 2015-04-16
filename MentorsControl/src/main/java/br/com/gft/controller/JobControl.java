package br.com.gft.controller;

import java.util.Calendar;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsService.JobService;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;
import br.com.gft.share.SystemLogs;
/**
 * this Class is to control request and responses of Job pages
 * @author mlav
 *
 */
@Controller
public class JobControl {
	private String index;
	JobService service = new JobService();
	Job job = new Job();
	
	/**
	 * this method is to show job page to read jobs registered
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Job", method = RequestMethod.GET)
	public String showJob(@RequestParam(value = "page", required = false) Integer page, Model model) {

		Pagination pagination = new Pagination(service.getJobs().size(), page);
		model.addAttribute("url", "Job");
		model.addAttribute("pagination", pagination);
		model.addAttribute("job", service.getPagedJobs(pagination.getBegin(), pagination.getQuantity()));
				
		return "Job";
	}
	
	/**
	 * this method is to get Job including inactive
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/JobInactive", method = RequestMethod.GET)
	public String showJobInactive(@RequestParam(value = "page", required = false) Integer page, Model model) {
		Pagination pagination = new Pagination(service.getJobsInactive().size(), page);
		
		model.addAttribute("url", "JobInactive");
		model.addAttribute("pagination", pagination);
		model.addAttribute("job", service.getPagedJobsInactive(pagination.getBegin(), pagination.getQuantity()));
		
		return "Job";
	}
	
	/**
	 * this method show page of Job registration
	 * @param job
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/JobRegistration", method = RequestMethod.GET)
	public String ShowJobRegistration(@ModelAttribute("Job") Job job, Model model) {		
		return "JobRegistration";
	}

	/**
	 * this method is to register Job
	 * @param job
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessJobRegistration", method=RequestMethod.POST)
	public String ProcessJobRegistration(@ModelAttribute("Job") Job job, Model model) {
		job.setActive(0);
		service.processJob(job);
		model.addAttribute("Job" , job);
		showJob(null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Job (ID): " + job.getId().toUpperCase());
		return "Job";
	}
	
	/**
	 * this method is to show page to update job informations
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/JobUpdate", method = RequestMethod.GET)
	public String ShowJobUpdate(@RequestParam(value="id") String id, Model model) {
		job.setId(id);
		index = id;
		job.setActive(0);
        job = service.getJob(job.getId());
		model.addAttribute("Job" , job);		
		return "JobUpdate";
	}
	
	
	/**
	 * this method is to register a update of job informations
	 * @param job
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessJobUpdate", method=RequestMethod.POST)
	public String ProcessJobUpdate(@ModelAttribute("Job") Job job, Model model) {
		model.addAttribute("Job" , job);
		job.setId(index);
		service.alterJob(job);
		showJob(null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTEROU o Job (ID): " + index.toUpperCase());
		return "Job";
	}
	
	/**
	 * this method is to register a inactive Job
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/JobStatus", method=RequestMethod.POST)
	public  String ProcessJobInactive(@RequestParam(value="id") String id,
									  @RequestParam(value="status") int status , Model model) {
		
		
		job = service.getJob(id);
		int ControlMessages;
		int action = job != null ? 1 : 0;

		if (action == 1) {
			job.setActive(status == 1 ? 0 : 1);
			
			service.alterJob(job);
			
			ControlMessages = 7;
		} else {
			ControlMessages = 8;
		}
		
		showJob(null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 1 ? " ATIVOU" : " DESATIVOU") + " o Job (ID): " + id.toUpperCase());
		return "Job";
	}
	
	
	

}

