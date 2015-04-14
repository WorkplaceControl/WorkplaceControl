package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Customer;


public class CustomerDAO {

	private EntityManager em;

	public CustomerDAO() {
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}

	public void insertCustomer(Customer customer) throws Exception{
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();
		em.close();
	}

	public void updateCustomer(Customer customer) {
		em.getTransaction().begin();
		em.merge(customer);
		em.getTransaction().commit();
		em.close();
	}

	public Customer findCustomer(String customerId) {
		return em.find(Customer.class, customerId);
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findPagedCustomers(int inicio, int quantidade){
		TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer where active=0 order by description asc" , Customer.class);

		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Customer>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findPagedCustomersInactive(int inicio, int quantidade){
		TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer order by description asc" , Customer.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Customer>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findCustomers() {
		TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer where active=0 order by description asc" , Customer.class);	
		
		return (List<Customer>) query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Customer> findCustomersInactive() {
		TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer order by description asc" , Customer.class);	
		
		return (List<Customer>) query.getResultList();
	}

}
