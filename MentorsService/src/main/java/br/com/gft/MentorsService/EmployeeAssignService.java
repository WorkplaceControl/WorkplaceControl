package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsDAO.EmployeeAssignmentDAO;
import br.com.gft.MentorsDAO.ProjectDAO;

public class EmployeeAssignService {

	public void addEmployeeAssign(EmployeeAssignment emp){
		EmployeeAssignmentDAO.setup();
		EmployeeAssignmentDAO empdao = new EmployeeAssignmentDAO();
		empdao.insertEmployeeAssignnment(emp);
		System.out.println("Passou aqui");
	}
	
	public void updateEmployeeAssign(EmployeeAssignment emp){
		EmployeeAssignmentDAO.setup();
		EmployeeAssignmentDAO empdao = new EmployeeAssignmentDAO();
		empdao.updateEmployeeAssignment(emp);
		System.out.println("Passou aqui");
	}
	
	public List<EmployeeAssignment> getEmployeeAssigns(String employeeId){
		EmployeeAssignmentDAO.setup();
		EmployeeAssignmentDAO empdao = new EmployeeAssignmentDAO();
		List<EmployeeAssignment> empAssign = empdao.findEmployeeAssignments(employeeId);
		System.out.println("Passou aqui");
		return empAssign;
	}
	
	public List<EmployeeAssignment> getEmployeeAssignsAll(){
		EmployeeAssignmentDAO.setup();
		EmployeeAssignmentDAO empdao = new EmployeeAssignmentDAO();
		List<EmployeeAssignment> empAssign = empdao.getEmployeeAssignments();
		return empAssign;
	}
	
	/**
	 * this method is to verify if exist this register to employee in the project informed 
	 * @param employeeId
	 * @param projId
	 * @return
	 */
	public int existsRegister(String employeeId , int projId){
		List<EmployeeAssignment> employeeassigments = new EmployeeAssignService().getEmployeeAssignsAll();
		int verify = 0;
		for(int i=0 ; i<employeeassigments.size() ; i++){
			if(employeeassigments.get(i).getEmployee().getId().equals(employeeId) && employeeassigments.get(i).getProject().getId() == projId ){
				verify = 1;
			}

		}
		return verify;
		
	}
}
