package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsDAO.JobDAO;
import br.com.gft.MentorsCommon.Job;



public class JobService {
	
	public void processJob(Job job){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO( );
		System.out.println("service : " + job.getTitle());
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
