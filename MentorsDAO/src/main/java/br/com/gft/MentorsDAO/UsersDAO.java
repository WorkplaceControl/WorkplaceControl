package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.UserRole;
import br.com.gft.MentorsCommon.Users;

public class UsersDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public static void setup(){ 
	emf = Persistence.createEntityManagerFactory("test");
	em = emf.createEntityManager();
	}
	
	public void insertUsers(Users user) throws Exception {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public void insertUserRole(UserRole userrole) throws Exception {
		em.getTransaction().begin();
		em.persist(userrole);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateUsers(Users user){
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public Users findUser(String userId){
		Users user = new Users();
		user = em.find(Users.class, userId);
		return user;
	}
	
	public List<Users> findUsers(){
		TypedQuery<Users> query = (TypedQuery<Users>) em.createNativeQuery("select * from Users" , Users.class);
		Collection<Users> users = (Collection<Users>) query.getResultList();
		return (List<Users>) users;
	}
}
