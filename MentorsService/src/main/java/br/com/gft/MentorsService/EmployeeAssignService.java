package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsDAO.EmployeeAssignmentDAO;

public class EmployeeAssignService {

	public void addEmployeeAssign(EmployeeAssignment emp){
		new EmployeeAssignmentDAO().insertEmployeeAssignnment(emp);
	}
	
	public void updateEmployeeAssign(EmployeeAssignment emp){
		new EmployeeAssignmentDAO().updateEmployeeAssignment(emp);
	}
	
	public List<EmployeeAssignment> getEmployeeAssigns(String employeeId){
		return new EmployeeAssignmentDAO().findEmployeeAssignments(employeeId);
	}
	
	public List<EmployeeAssignment> getEmployeeAssignsAll(){
		return new EmployeeAssignmentDAO().getEmployeeAssignments();
	}
	
	/**
	 * this method is to verify if exist this register to employee in the project informed
	 *  
	 * @param employeeId
	 * @param projId
	 * 
	 * @return
	 */
	public int existsRegister(String employeeId , String projId){
		int verify = 0;
		
		for (EmployeeAssignment employeeAssignment : new EmployeeAssignService().getEmployeeAssignsAll()) {
			if (employeeAssignment.getEmployee().getId().equals(employeeId) && employeeAssignment.getProject().getDescription() == projId) {
				verify = 1;
			}
		}
		
		return verify;
	}
	
}
