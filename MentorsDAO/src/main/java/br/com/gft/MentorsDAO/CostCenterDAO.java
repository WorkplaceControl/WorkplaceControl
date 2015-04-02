package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Job;


public class CostCenterDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public static void setup(){ 
	emf = Persistence.createEntityManagerFactory("test");
	em = emf.createEntityManager();
	}

	public void insertCostCenter(CostCenter cost) throws Exception {
		em.getTransaction().begin();
		em.persist(cost);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateCostCenter(CostCenter cost){
		System.out.println("teste : " + cost.getId());
		em.getTransaction().begin();
		em.merge(cost);
		em.getTransaction().commit();
		em.close();
	}
	
		
	
	public CostCenter findCost(String costCenterId){
		CostCenter cost = new CostCenter();
		cost = em.find(CostCenter.class, costCenterId);
		return cost;
	}
	
	public List<CostCenter> findPagedCostCenters(int inicio, int quantidade){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center where active = 0 order by id desc" , CostCenter.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<CostCenter> costcenter = (Collection<CostCenter>) query.getResultList();
		return (List<CostCenter>) costcenter;
	}
	
	public List<CostCenter> findPagedCostCentersInactive(int inicio, int quantidade){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center order by id desc" , CostCenter.class);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		Collection<CostCenter> costcenter = (Collection<CostCenter>) query.getResultList();
		return (List<CostCenter>) costcenter;
	}
	
	public List<CostCenter> findCostCenters(){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center where active = 0 order by id desc" , CostCenter.class);
		Collection<CostCenter> costcenter = (Collection<CostCenter>) query.getResultList();
		return (List<CostCenter>) costcenter;
	}
	
	public List<CostCenter> findCostCentersInactive(){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center order by id desc" , CostCenter.class);
		Collection<CostCenter> costcenter = (Collection<CostCenter>) query.getResultList();
		return (List<CostCenter>) costcenter;
	}
	
}
