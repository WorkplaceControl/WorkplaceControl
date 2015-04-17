package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsDAO.JobDAO;

public class JobService {
	
	public void processJob(Job job){
		new JobDAO().insertJob(job);
	}
	
	public List<Job> getJobs(){
		return new JobDAO().findJobs();
	}
	
	public List<Job> getJobsInactive(){
		return new JobDAO().findJobsInactive();
	}
	
	public List<Job> getPagedJobs(int inicio, int quantidade){
		return new JobDAO().findPagedJobs(inicio, quantidade);
	}
	
	public List<Job> getPagedJobsInactive(int inicio, int quantidade){
		return new JobDAO().findPagedJobsInactive(inicio, quantidade);
	}

	public void alterJob(Job job){
		new JobDAO().updateJob(job);
	}
	
	public Job getJob(String jobId){
		return new JobDAO().findJob(jobId);
	}
	
	public List<Job> getJobs(String search){
		return new JobDAO().findJobs(search);
	}
	
	public List<Job> getJobsInactive(String search){
		return new JobDAO().findJobsInactive(search);
	}
	
	public List<Job> getPagedJobs(String search, int begin, int quantity){
		return new JobDAO().findJobs(search, begin, quantity);
	}
	
	public List<Job> getPagedJobsInactive(String search, int begin, int quantity){
		return new JobDAO().findJobsInactive(search, begin, quantity);
	}
	
    
}
