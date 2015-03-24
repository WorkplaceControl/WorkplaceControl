package br.com.gft.MentorsDAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.MentorHistory;
import br.com.gft.MentorsCommon.Project;

public class MentorHistoryDAO {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public static void setup(){ 
	emf = Persistence.createEntityManagerFactory("test");
	em = emf.createEntityManager();
	}
	
	public void insertMentorHistory(MentorHistory empHist){
		em.getTransaction().begin();
		em.persist(empHist);
		em.getTransaction().commit();
		em.close();
	}
	
	public void updateMentorHistory(MentorHistory empHist){
		em.getTransaction().begin();
		em.merge(empHist);
		em.getTransaction().commit();
		em.close();
	}
	
	
	
	public List<MentorHistory> findMentorHistorys(){
		TypedQuery<MentorHistory> query = (TypedQuery<MentorHistory>) em.createNativeQuery("select * from Mentor_History order by employee_id" , MentorHistory.class);
		Collection<MentorHistory> mentorhistory = (Collection<MentorHistory>) query.getResultList();
		return (List<MentorHistory>) mentorhistory;
	}
	
	public MentorHistory findMentorHistory(int mentorhistoryId) {
		em.getTransaction().begin();
		MentorHistory mentorhistory = new MentorHistory();
		mentorhistory = em.find(MentorHistory.class, mentorhistoryId);
		em.close();
		return mentorhistory;
	}
}
