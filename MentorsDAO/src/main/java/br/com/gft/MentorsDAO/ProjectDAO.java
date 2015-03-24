package br.com.gft.MentorsDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;









import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.Unit;

public class ProjectDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void setup(){ 
		emf = Persistence.createEntityManagerFactory("test");
		em = emf.createEntityManager();
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
		em.getTransaction().begin();
		Project project = new Project();
		project = em.find(Project.class, projectId);
		em.close();
		return project;
	}
	

	public List<Project> findProjects(){
		TypedQuery<Project> query = (TypedQuery<Project>) em.createNativeQuery("select * from project where active = 0 order by description asc" , Project.class);
		Collection<Project> project  = (Collection<Project>) query.getResultList();
		return (List<Project>) project;
	}
	
	public List<Project> findProjectsInactive(){
		TypedQuery<Project> query = (TypedQuery<Project>) em.createNativeQuery("select * from project order by description asc" , Project.class);
		Collection<Project> project  = (Collection<Project>) query.getResultList();
		return (List<Project>) project;
	}


}
