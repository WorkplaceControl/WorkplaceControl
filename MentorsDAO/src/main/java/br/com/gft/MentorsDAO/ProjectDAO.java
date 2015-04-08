package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Project;

public class ProjectDAO {

	private EntityManager em;

	public ProjectDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}

	public void insertProject(Project project) {
		em.getTransaction().begin();
		em.persist(project);
		em.getTransaction().commit();
		em.close();
	}

	public void updateProject(Project project) {
		em.getTransaction().begin();
		em.merge(project);
		em.getTransaction().commit();
		em.close();
	}

	public Project findProject(int projectId) {
		return em.find(Project.class, projectId);
	}
	
	public List<Project> findPagedProjects(int inicio, int quantidade){
		TypedQuery<Project> query = (TypedQuery<Project>) em.createNativeQuery("select * from project where active = 0 order by description asc" , Project.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);

		return (List<Project>) query.getResultList();
	}
	
	public List<Project> findPagedProjectsInactive(int inicio, int quantidade){
		TypedQuery<Project> query = (TypedQuery<Project>) em.createNativeQuery("select * from project order by description asc" , Project.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Project>) query.getResultList();
	}

	public List<Project> findProjects(){
		TypedQuery<Project> query = (TypedQuery<Project>) em.createNativeQuery("select * from project where active = 0 order by description asc" , Project.class);
		
		return (List<Project>) query.getResultList();
	}
	
	public List<Project> findProjectsInactive(){
		TypedQuery<Project> query = (TypedQuery<Project>) em.createNativeQuery("select * from project order by description asc" , Project.class);
		
		return (List<Project>) query.getResultList();
	}

}
