package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsDAO.CustomerDAO;
import br.com.gft.MentorsDAO.ProjectDAO;
import br.com.gft.MentorsDAO.UnitDAO;

public class ProjectService {
	ProjectDAO projectdao = new ProjectDAO();
	
	
	public void addProject(Project project){
		ProjectDAO.setup();	
		projectdao.insertProject(project);
		System.out.println("Passou aqui");
	}

	public List<Customer> getCustomers() {
		CustomerDAO.setup();
		CustomerDAO customerdao = new CustomerDAO();
		return customerdao.findCustomers(); 
	}
	
	
	public Project getProject(int projectId){
		Project project = new Project();
		ProjectDAO.setup();
		project = projectdao.findProject(projectId);
		return project;
	}
	
	public List<Project> getProjects(){
		ProjectDAO.setup();
		ProjectDAO projectdao = new ProjectDAO();
		List<Project> project = projectdao.findProjects();
		return project;
	}
	
	public List<Project> getProjectsInactive(){
		ProjectDAO.setup();
		ProjectDAO projectdao = new ProjectDAO();
		List<Project> project = projectdao.findProjectsInactive();
		return project;
	}
	
	
	
	public void alterProject(Project project){
		ProjectDAO.setup();
		ProjectDAO projectdao = new ProjectDAO();
		projectdao.updateProject(project);
	}
	
}
