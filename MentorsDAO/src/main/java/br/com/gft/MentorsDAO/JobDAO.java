package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Job;

public class JobDAO {

	private EntityManager em;
	
	public JobDAO() {
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}
	
	public void insertJob(Job job){
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
	
	public Job findJob(String jobId){
		return em.find(Job.class, jobId);
	}
	
	public List<Job> findPagedJobs(int inicio, int quantidade){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job where active = 0 order by position asc" , Job.class);

		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);

		return (List<Job>) query.getResultList();
	}
	
	public List<Job> findPagedJobsInactive(int inicio, int quantidade){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job" , Job.class);

		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Job>) query.getResultList();
	}
	
	public List<Job> findJobs(){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job where active = 0 order by position asc" , Job.class);
		
		return (List<Job>) query.getResultList();
	}
	
	public List<Job> findJobsInactive(){
		TypedQuery<Job> query = (TypedQuery<Job>) em.createNativeQuery("select * from Job" , Job.class);

		return (List<Job>) query.getResultList();
	}
	
}
