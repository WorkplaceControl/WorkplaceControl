package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsDAO.CustomerDAO;
import br.com.gft.MentorsDAO.ProjectDAO;

public class ProjectService {
	
	public void addProject(Project project){
		new ProjectDAO().insertProject(project);
	}

	public List<Customer> getCustomers() {
		return new CustomerDAO().findCustomers(); 
	}
	
	public Project getProject(String projectId){
		return new ProjectDAO().findProject(projectId);
	}
	
	public List<Project> getProjects(){
		return new ProjectDAO().findProjects();
	}
	
	public List<Project> getProjectsInactive(){
		return new ProjectDAO().findProjectsInactive();
	}
	
	public List<Project> getPagedProjects(int inicio, int quantidade){
		return new ProjectDAO().findPagedProjects(inicio, quantidade);
	}
	
	public List<Project> getPagedProjectsInactive(int inicio, int quantidade){
		return new ProjectDAO().findPagedProjectsInactive(inicio, quantidade);
	}
	
	public void alterProject(Project project){
		new ProjectDAO().updateProject(project);
	}
	
	public List<Project> getProjects(String search){
		return new ProjectDAO().findProjects(search);
	}
	
	public List<Project> getProjectsInactive(String search){
		return new ProjectDAO().findProjectsInactive(search);
	}
	
	public List<Project> getPagedProjects(String search, int begin, int quantity){
		return new ProjectDAO().findProjects(search, begin, quantity);
	}
	
	public List<Project> getPagedProjectsInactive(String search, int begin, int quantity){
		return new ProjectDAO().findProjectsInactive(search, begin, quantity);
	}
	
	
}
