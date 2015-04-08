package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.RatePrf;

public class RatePrfDAO {
	
	private EntityManager em;
	
	public RatePrfDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
	}
	
	public void insertRatePrf(RatePrf rate) {
		em.getTransaction().begin();
		em.persist(rate);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateRatePrf(RatePrf rate) {
		em.getTransaction().begin();
		em.merge(rate);
		em.getTransaction().commit();
		em.close();
	}
	
	public RatePrf findRatePrf(int ratePrfId) {
		return em.find(RatePrf.class, ratePrfId);
	}
	
	public List<RatePrf> findRatePrfs(){
		TypedQuery<RatePrf> query = (TypedQuery<RatePrf>) em.createNativeQuery("select * from Rate_Prf" , RatePrf.class);
		
		return (List<RatePrf>) query.getResultList();
	}

}
