package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;







import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Unit;


public class CustomerDAO {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public CustomerDAO() {
		emf = Persistence.createEntityManagerFactory("test");
		em = emf.createEntityManager();
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
		
		public Customer findCustomer(int CustomerId) {
			Customer customer = new Customer();
			customer = em.find(Customer.class, CustomerId);
			return customer;
			
		}
		
		@SuppressWarnings("unchecked")
		public List<Customer> findPagedCustomers(int inicio, int quantidade){
			TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer where active=0 order by description asc" , Customer.class);
			query.setFirstResult(inicio);
			query.setMaxResults(quantidade);
			Collection<Customer> customer = (Collection<Customer>) query.getResultList();
			return (List<Customer>) customer;
		}
		
		@SuppressWarnings("unchecked")
		public List<Customer> findPagedCustomersInactive(int inicio, int quantidade){
			TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer order by description asc" , Customer.class);
			query.setFirstResult(inicio);
			query.setMaxResults(quantidade);
			Collection<Customer> customer = (Collection<Customer>) query.getResultList();
			return (List<Customer>) customer;
		}

		@SuppressWarnings("unchecked")
		public List<Customer> findCustomers() {
			TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer where active=0 order by description asc" , Customer.class);	
			Collection<Customer> cus = (Collection<Customer>) query.getResultList();
	    	return (List<Customer>) cus;

		}
		
		@SuppressWarnings("unchecked")
		public List<Customer> findCustomersInactive() {
			TypedQuery<Customer> query = (TypedQuery<Customer>) em.createNativeQuery("select * from Customer order by description asc" , Customer.class);	
			Collection<Customer> cus = (Collection<Customer>) query.getResultList();
	    	return (List<Customer>) cus;

		}
}
