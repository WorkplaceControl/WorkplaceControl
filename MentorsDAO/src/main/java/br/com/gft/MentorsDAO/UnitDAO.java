package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsCommon.Unit;

public class UnitDAO {
	
	private EntityManager em;
	
	public UnitDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}
	
	public void insertUnit(Unit unit){
		em.getTransaction().begin();
		em.persist(unit);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateUnit(Unit unit){
		em.getTransaction().begin();
		em.merge(unit);
		em.getTransaction().commit();
		em.close();
	}
	
	public Unit findUnit(String unitId){
		return em.find(Unit.class,unitId);
	}
	
	public List<Unit> findPagedUnits(int inicio, int quantidade){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where active = 0 order by description asc" , Unit.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Unit>) query.getResultList();
	}
	
	public List<Unit> findPagedUnitsInactive(int inicio, int quantidade){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit order by description asc" , Unit.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<Unit>) query.getResultList();
	}
	
	public List<Unit> findUnits(){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where active = 0 order by description asc" , Unit.class);
		
		return (List<Unit>) query.getResultList();
	}
	
	public List<Unit> findUnitsInactive(){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit order by description asc" , Unit.class);
		
		return (List<Unit>) query.getResultList();
	}
	
	public List<Unit> findUnits(String search){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where active = 0 and (id || description) iLIKE ? order by description" , Unit.class);

		query.setParameter(1, "%" + search + "%");
		
		return (List<Unit>) query.getResultList();
	}
	
	public List<Unit> findUnitsInactive(String search){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where (id || description) iLIKE ? order by description" , Unit.class);
		
		query.setParameter(1, "%" + search + "%");
		
		return (List<Unit>) query.getResultList();
	}
	
	public List<Unit> findUnits(String search, int begin, int quantity){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where active = 0 and (id || description) iLIKE ? order by description" , Unit.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		query.setParameter(1, "%" + search + "%");
		
		return (List<Unit>) query.getResultList();
	}
	
	public List<Unit> findUnitsInactive(String search, int begin, int quantity){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where (id || description) iLIKE ? order by description" , Unit.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		query.setParameter(1, "%" + search + "%");
		
		return (List<Unit>) query.getResultList();
	}

}