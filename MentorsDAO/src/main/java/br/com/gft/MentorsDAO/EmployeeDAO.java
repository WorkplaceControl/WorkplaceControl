package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Employee;




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
		
	public List<Employee> findPagedEmployees(int inicio, int quantidade){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 order by ws_name asc" , Employee.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<Employee> employee = (Collection<Employee>) query.getResultList();
		return (List<Employee>) employee;
	}
	
	public List<Employee> findPagedEmployeesInactive(int inicio, int quantidade){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee order by ws_name asc" , Employee.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<Employee> employee = (Collection<Employee>) query.getResultList();
		return (List<Employee>) employee;
	}
	
	public List<Employee> findEmployees(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 order by ws_name asc" , Employee.class);
		Collection<Employee> employee  = (Collection<Employee>) query.getResultList();
		return (List<Employee>) employee;
	}
	
	
//	public List<Employee> findQtyMentee(String employeeId){
//		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select m.id, count(*) as mentee from employee m, employee e where e.mentor_id = m.id group by m.id", Employee.class);
//		Collection<Employee> employee  = (Collection<Employee>) query.getResultList();
//		return (List<Employee>) employee;
//	}
//	
//	public List<Employee> findQtyMentee(String employeeId){
//		Query query = em.createQuery("select m.id, count(*) as mentee from employee m, employee e where e.mentor_id = m.id group by m.id");
//		Collection<Employee> employee  = (Collection<Employee>) query.getResultList () ;
//				return (List<Employee>) employee;
//	}
	
	public List<Employee> findEmployeesInactive(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee order by ws_name asc" , Employee.class);
		Collection<Employee> employee  = (Collection<Employee>) query.getResultList();
		return (List<Employee>) employee;
	}


}
