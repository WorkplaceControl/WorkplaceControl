package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.UserRole;

public class UserRoleDAO {
	
	private EntityManager em;

	public UserRoleDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}

	public UserRole findUserById(int id){
		return em.find(UserRole.class, id);
	}

	public List<UserRole> findUserRoles(){
		TypedQuery<UserRole> query = (TypedQuery<UserRole>) em.createNativeQuery("select * from user_role" , UserRole.class);
		
		return (List<UserRole>) query.getResultList();
	}

}
