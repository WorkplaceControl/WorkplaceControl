package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Project;


public class EmployeeAssignmentDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public static void setup(){ 
	emf = Persistence.createEntityManagerFactory("test");
	em = emf.createEntityManager();
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
		EmployeeAssignment empAssign = new EmployeeAssignment();
		return empAssign = em.find(EmployeeAssignment.class,  employeeId);
	}
	
	public List<EmployeeAssignment> findEmployeeAssignments(String employeeId){
		TypedQuery<EmployeeAssignment> query = (TypedQuery<EmployeeAssignment>) em.createNativeQuery("select * from Employee_Assignment where active = 0 and Employee_id = '"+ employeeId +"' order by project_id asc" , EmployeeAssignment.class);
		Collection<EmployeeAssignment> empAssign  = (Collection<EmployeeAssignment>) query.getResultList();
		return (List<EmployeeAssignment>) empAssign;
	}
	
	public List<EmployeeAssignment> getEmployeeAssignments(){
		TypedQuery<EmployeeAssignment> query = (TypedQuery<EmployeeAssignment>) em.createNativeQuery("select * from Employee_Assignment where active = 0 order by project_id asc" , EmployeeAssignment.class);
		Collection<EmployeeAssignment> empAssign  = (Collection<EmployeeAssignment>) query.getResultList();
		return (List<EmployeeAssignment>) empAssign;
	}
}
