package br.com.gft.MentorsDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.gft.MentorsCommon.MentorHistory;

public class MentorHistoryDAO {
	
	private EntityManager em;
	
	public MentorHistoryDAO(){ 
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
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

		return (List<MentorHistory>) query.getResultList();
	}
	
	public MentorHistory findMentorHistory(int mentorhistoryId) {
		return em.find(MentorHistory.class, mentorhistoryId);
	}
}
