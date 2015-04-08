package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.Mentor;

public class EmployeeDAO {
	
	private EntityManager em;
	
	public EmployeeDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
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
		return em.find(Employee.class,  employeeId);
	}
		
	public List<Employee> findPagedEmployees(int inicio, int quantidade){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 order by ws_name asc" , Employee.class);

		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> findPagedEmployeesInactive(int inicio, int quantidade){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee order by ws_name asc" , Employee.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> findEmployees(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 order by ws_name asc" , Employee.class);
		
		return (List<Employee>) query.getResultList();
	}
	
	
	public List<Employee> findEmployeesInactive(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee order by ws_name asc" , Employee.class);

		return (List<Employee>) query.getResultList();
	}

	
	public List<Mentor> findQtyMentee(){
		TypedQuery<Mentor> query = (TypedQuery<Mentor>) em.createNativeQuery("select id, name, job_id, cost_center_id, rate_prf_id, count(mentor_id) as qtymentee from employee group by id order by qtymentee asc", Mentor.class);

		return (List<Mentor>) query.getResultList();
	}
	
}
