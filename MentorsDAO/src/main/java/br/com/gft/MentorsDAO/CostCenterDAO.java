package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.CostCenter;

public class CostCenterDAO {
	
	private EntityManager em;
	
	public CostCenterDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}

	public void insertCostCenter(CostCenter cost) throws Exception {
		em.getTransaction().begin();
		em.persist(cost);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateCostCenter(CostCenter cost){
		em.getTransaction().begin();
		em.merge(cost);
		em.getTransaction().commit();
		em.close();
	}
	
	public CostCenter findCost(String costCenterId){
		return em.find(CostCenter.class, costCenterId);
	}
	
	public List<CostCenter> findPagedCostCenters(int inicio, int quantidade){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center where active = 0 order by id desc" , CostCenter.class);

		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<CostCenter>) query.getResultList();
	}
	
	public List<CostCenter> findPagedCostCentersInactive(int inicio, int quantidade){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center order by id desc" , CostCenter.class);
		
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		
		return (List<CostCenter>) query.getResultList();
	}
	
	public List<CostCenter> findCostCenters(){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center where active = 0 order by id desc" , CostCenter.class);

		return (List<CostCenter>) query.getResultList();
	}
	
	public List<CostCenter> findCostCentersInactive(){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from Cost_Center order by id desc" , CostCenter.class);
		
		return (List<CostCenter>) query.getResultList();
	}
	
	public List<CostCenter> findCostCenters(String search){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from cost_center where active = 0 and (id || title) iLIKE ? order by title" , CostCenter.class);

		query.setParameter(1, "%" + search + "%");
		
		return (List<CostCenter>) query.getResultList();
	}
	
	public List<CostCenter> findCostCentersInactive(String search){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from cost_center where (id || title) iLIKE ? order by title" , CostCenter.class);
		
		query.setParameter(1, "%" + search + "%");
		
		return (List<CostCenter>) query.getResultList();
	}
	
	public List<CostCenter> findCostCenters(String search, int begin, int quantity){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from cost_center where active = 0 and (id || title) iLIKE ? order by title" , CostCenter.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		query.setParameter(1, "%" + search + "%");
		
		return (List<CostCenter>) query.getResultList();
	}
	
	public List<CostCenter> findCostCentersInactive(String search, int begin, int quantity){
		TypedQuery<CostCenter> query = (TypedQuery<CostCenter>) em.createNativeQuery("select * from cost_center where (id || title) iLIKE ? order by title" , CostCenter.class);
		
		query.setFirstResult(begin);
		query.setMaxResults(quantity);
		query.setParameter(1, "%" + search + "%");
		
		return (List<CostCenter>) query.getResultList();
	}
	
}
