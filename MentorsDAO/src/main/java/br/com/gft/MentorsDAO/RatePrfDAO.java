package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.RatePrf;


public class RatePrfDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public static void setup(){ 
	emf = Persistence.createEntityManagerFactory("test");
	em = emf.createEntityManager();
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
		RatePrf ratePrf = new RatePrf();
		em.getTransaction().begin();
		ratePrf = em.find(RatePrf.class,ratePrfId);
		em.close();
		return ratePrf;
	}
	
	public List<RatePrf> findRatePrfs(){
		TypedQuery<RatePrf> query = (TypedQuery<RatePrf>) em.createNativeQuery("select * from Rate_Prf" , RatePrf.class);
		Collection<RatePrf> costcenter = (Collection<RatePrf>) query.getResultList();
		return (List<RatePrf>) costcenter;
	}

}
