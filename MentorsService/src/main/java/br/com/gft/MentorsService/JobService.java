package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsDAO.EmployeeDAO;
import br.com.gft.MentorsDAO.JobDAO;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.Job;



public class JobService {
	
	public void processJob(Job job){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO( );
		jobdao.insertJob(job);
	}
	
	public List<Job> getJobs(){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO();
		List<Job> jobs = jobdao.findJobs();
		return jobs;
	}
	
	public List<Job> getJobsInactive(){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO();
		List<Job> jobs = jobdao.findJobsInactive();
		return jobs;
	}
	
	public List<Job> getPagedJobs(int inicio, int quantidade){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO();
		List<Job> job = jobdao.findPagedJobs(inicio, quantidade);
		return job;
	}
	
	public List<Job> getPagedJobsInactive(int inicio, int quantidade){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO();
		List<Job> job = jobdao.findPagedJobsInactive(inicio, quantidade);
		return job;
	}

	public void alterJob(Job job){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO();
		jobdao.updateJob(job);
	}
	
	public Job getJob(String jobId){
		Job job = new Job();
		JobDAO.setup();
		JobDAO jobdao = new JobDAO();
		job = jobdao.finJob(jobId);
		return job;
	}
    
}
