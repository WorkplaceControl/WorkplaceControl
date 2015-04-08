package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.EmployeeAssignment;

public class EmployeeAssignmentDAO {
	
	private EntityManager em;
	
	public EmployeeAssignmentDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}

	public void insertEmployeeAssignnment(EmployeeAssignment empAssign) {
		em.getTransaction().begin();
		em.persist(empAssign);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateEmployeeAssignment(EmployeeAssignment empAssign) {
		em.getTransaction().begin();
		em.merge(empAssign);
		em.getTransaction().commit();
		em.close();
	}
	
	public EmployeeAssignment findEmployeeAssignment(String employeeId){
		return em.find(EmployeeAssignment.class,  employeeId);
	}
	
	public List<EmployeeAssignment> findEmployeeAssignments(String employeeId){
		TypedQuery<EmployeeAssignment> query = (TypedQuery<EmployeeAssignment>) em.createNativeQuery("select * from Employee_Assignment where active = 0 and Employee_id = '"+ employeeId +"' order by project_id asc" , EmployeeAssignment.class);

		return (List<EmployeeAssignment>) query.getResultList();
	}
	
	public List<EmployeeAssignment> getEmployeeAssignments(){
		TypedQuery<EmployeeAssignment> query = (TypedQuery<EmployeeAssignment>) em.createNativeQuery("select * from Employee_Assignment where active = 0 order by project_id asc" , EmployeeAssignment.class);

		return (List<EmployeeAssignment>) query.getResultList();
	}
}
