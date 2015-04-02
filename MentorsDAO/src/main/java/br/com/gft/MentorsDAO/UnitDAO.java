package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Unit;

public class UnitDAO {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	
	public static void setup(){ 
		emf = Persistence.createEntityManagerFactory("test");
		em = emf.createEntityManager();
		}
	
	public void insertUnit(Unit unit){
		em.getTransaction().begin();
		em.persist(unit);
		em.getTransaction().commit();
	}
	
	public void updateUnit(Unit unit){
		em.getTransaction().begin();
		em.merge(unit);
		em.getTransaction().commit();
	}
	
	public Unit findUnit(long unitId){
		Unit unit = new Unit();
		unit = em.find(Unit.class,unitId);
		return unit;
	}
	
	public List<Unit> findPagedUnits(int inicio, int quantidade){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where active = 0 order by description asc" , Unit.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<Unit> unit = (Collection<Unit>) query.getResultList();
		return (List<Unit>) unit;
	}
	
	public List<Unit> findPagedUnitsInactive(int inicio, int quantidade){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit order by description asc" , Unit.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<Unit> unit = (Collection<Unit>) query.getResultList();
		return (List<Unit>) unit;
	}
	
	public List<Unit> findUnits(){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit where active = 0 order by description asc" , Unit.class);
		Collection<Unit> unit  = (Collection<Unit>) query.getResultList();
		return (List<Unit>) unit;
	}
	
	public List<Unit> findUnitsInactive(){
		TypedQuery<Unit> query = (TypedQuery<Unit>) em.createNativeQuery("select * from unit order by description asc" , Unit.class);
		Collection<Unit> unit  = (Collection<Unit>) query.getResultList();
		return (List<Unit>) unit;
	}
}


