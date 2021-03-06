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
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 order by id" , Employee.class);

		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> findPagedEmployeesInactive(int inicio, int quantidade){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee order by id" , Employee.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> findEmployees(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 order by id" , Employee.class);
		
		return (List<Employee>) query.getResultList();
	}
		
	public List<Employee> findEmployeesInactive(){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee order by id" , Employee.class);

		return (List<Employee>) query.getResultList();
	}
	
	public List<Mentor> findQtyMentor(){
		TypedQuery<Mentor> query = (TypedQuery<Mentor>) em.createNativeQuery("SELECT id, name, job_id, cost_center_id, rate_prf_id, mentor_id,"
																			 + " (SELECT COUNT(ee.id) FROM employee ee WHERE ee.mentor_id = e.id) as qtymentee FROM employee e WHERE"
																			 + " (SELECT position FROM Job WHERE id = e.job_id) >= (SELECT position FROM Job WHERE id = 'SE02') AND "
																			 + " e.active = 0"
																			 + " order by qtymentee", Mentor.class);

		return (List<Mentor>) query.getResultList();
	}
	
	public List<Mentor> findQtyMentee(){
		TypedQuery<Mentor> query = (TypedQuery<Mentor>) em.createNativeQuery("SELECT id, name, job_id, cost_center_id, rate_prf_id, mentor_id,"
																			 + " (SELECT COUNT(ee.id) FROM employee ee WHERE ee.mentor_id = e.id) as qtymentee FROM employee e WHERE"
																			 + " e.active = 0"
																			 + " order by qtymentee", Mentor.class);

		return (List<Mentor>) query.getResultList();
	}
	
	public List<Employee> findEmployees(String search){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 AND (id || job_id || ws_name || mentor_id) iLIKE ? order by id" , Employee.class);

		query.setParameter(1, "%" + search + "%");
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> findEmployeesInactive(String search){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where (id || job_id || ws_name || mentor_id) iLIKE ? order by id" , Employee.class);
		
		query.setParameter(1, "%" + search + "%");
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> findPagedEmployees(String search, int begin, int quantity){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 AND (id || job_id || ws_name || mentor_id) iLIKE ? order by id" , Employee.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		query.setParameter(1, "%" + search + "%");
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> findPagedEmployeesInactive(String search, int begin, int quantity){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where (id || job_id || ws_name || mentor_id) iLIKE ? order by name" , Employee.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		
		query.setParameter(1, "%" + search + "%");
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> getMentees(String mentor){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select * from Employee where active = 0 AND mentor_id = ? order by id" , Employee.class);

		query.setParameter(1, mentor);
		
		return (List<Employee>) query.getResultList();
	}
	
	public List<Employee> getNotMentees(String mentor, int jobPosition){
		TypedQuery<Employee> query = (TypedQuery<Employee>) em.createNativeQuery("select e.* from employee e INNER JOIN job j ON e.job_id = j.id where e.active = 0 AND e.id != ? AND (mentor_id != ? OR mentor_id is null) AND j.position < ? order by e.id" , Employee.class);

		query.setParameter(1, mentor);
		query.setParameter(2, mentor);
		query.setParameter(3, jobPosition);
		
		return (List<Employee>) query.getResultList();
	}

}