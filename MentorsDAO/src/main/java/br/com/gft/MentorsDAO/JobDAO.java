package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.Job;



public class JobDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public static void setup() {
		emf = Persistence.createEntityManagerFactory("test");
		em = emf.createEntityManager();
	}
	
	public void insertJob(Job job){
		System.out.println(job.getTitle());
		System.out.println("Active : " + job.getActive());
		em.getTransaction().begin();
		em.persist(job);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateJob(Job job){
		em.getTransaction().begin();
		em.merge(job);
		em.getTransaction().commit();
		em.close();
	}
	
	public Job finJob(String jobId){
		Job job = new Job();
		job = em.find(Job.class, jobId);
	    job.setId(job.getId());
	    job.setTitle(job.getTitle());
		return job;
	}
	
	public List<Job> findPagedJobs(int inicio, int quantidade){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job where active = 0 order by position asc" , Job.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<Job> job = (Collection<Job>) query.getResultList();
		return (List<Job>) job;
	}
	
	public List<Job> findPagedJobsInactive(int inicio, int quantidade){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job" , Job.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<Job> job = (Collection<Job>) query.getResultList();
		return (List<Job>) job;
	}
	
	public List<Job> findJobs(){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job where active = 0 order by position asc" , Job.class);
		Collection<Job> job = (Collection<Job>) query.getResultList();
		return (List<Job>) job;
	}
	
public List<Job> findJobsInactive(){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job" , Job.class);
		Collection<Job> job = (Collection<Job>) query.getResultList();
		return (List<Job>) job;
	}
	
}
