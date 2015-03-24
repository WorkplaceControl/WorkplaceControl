package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.RatePrf;




public class EmployeeDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public static void setup(){ 
	emf = Persistence.createEntityManagerFactory("test");
	em = emf.createEntityManager();
	}
	
	public void insertEmployee(Employee employee){
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateEmployee(Employee employee) {
		em.getTransaction().begin();
		em.merge(employee);
		em.getTransaction().commit();
		em.close();
	}
	
	public Employee findEmployee(String employeeId) {
		Employee employee = new Employee();
	    employee = em.find(Employee.class,  employeeId);
		return employee;
	}
	
	public List<Employee> findEmployees(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 order by ws_name asc" , Employee.class);
		Collection<Employee> employee  = (Collection<Employee>) query.getResultList();
		return (List<Employee>) employee;
	}
	
	public List<Employee> findEmployeesInactive(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee order by ws_name asc" , Employee.class);
		Collection<Employee> employee  = (Collection<Employee>) query.getResultList();
		return (List<Employee>) employee;
	}


}
