package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Users;

public class UsersDAO {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public UsersDAO(){ 
		emf = Persistence.createEntityManagerFactory("test");
		em = emf.createEntityManager();
	}

	public void insertUsers(Users user) throws Exception {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}

	public void updateUsers(Users user){
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
		em.close();
	}

	public Users findUser(String username){
		return em.find(Users.class, username);
	}

	public List<Users> findUsers(){
		TypedQuery<Users> query = (TypedQuery<Users>) em.createNativeQuery("select * from Users where enable = 1 order by username" , Users.class);

		return (List<Users>) query.getResultList();
	}

	public List<Users> findUsersInactive(){
		TypedQuery<Users> query = (TypedQuery<Users>) em.createNativeQuery("select * from Users order by username" , Users.class);
		
		return (List<Users>) query.getResultList();
	}

	public List<Users> findUsers(int begin, int quantity){
		TypedQuery<Users> query = (TypedQuery<Users>) em.createNativeQuery("select * from Users where enable = 1 order by username" , Users.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		
		return (List<Users>) query.getResultList();
	}

	public List<Users> findUsersInactive(int begin, int quantity){
		TypedQuery<Users> query = (TypedQuery<Users>) em.createNativeQuery("select * from Users order by username" , Users.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		
		return (List<Users>) query.getResultList();
	}

}
