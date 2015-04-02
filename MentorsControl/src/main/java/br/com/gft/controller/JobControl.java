package br.com.gft.controller;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsService.CostCenterService;
import br.com.gft.MentorsService.JobService;
import br.com.gft.share.Pagination;
/**
 * this Class is to control request and responses of Job pages
 * @author mlav
 *
 */
@Controller
public class JobControl {
	private String index;
	
	/**
	 * this method is to show job page to read jobs registered
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Job", method = RequestMethod.GET)
	public String showJob(@RequestParam(value = "page", required = false) Integer page, Model model) {
		JobService service = new JobService();
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
		JobService service = new JobService();
		List<Job> job = service.getJobs();
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
		JobService jobservice = new JobService();
		jobservice.processJob(job);
		model.addAttribute("Job" , job);
		showJob(null, model);
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
		Job job = new Job();
		job.setId(id);
		index = id;
		job.setActive(0);
		JobService jobservice = new JobService();
        job = jobservice.getJob(job.getId());
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
		JobService jobservice = new JobService();
		job.setId(index);
		jobservice.alterJob(job);
		showJob(null, model);
		return "Job";
	}
	
	/**
	 * this method is to register a inactive Job
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/JobInactivate", method=RequestMethod.GET)
	public  String ProcessJobInactive(@RequestParam(value="id") String id,@RequestParam(value="title") String title , Model model) {
		Job job = new Job();
		job.setId(id);
		job.setTitle(title);
		job.setActive(1);
		JobService jobservice = new JobService();
		jobservice.alterJob(job);
		showJob(null, model);
		return "Job";
	}
	
	
	

}

