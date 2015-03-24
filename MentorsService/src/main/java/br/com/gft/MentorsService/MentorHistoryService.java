package br.com.gft.MentorsService;

import java.util.Date;
import java.util.List;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.MentorHistory;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsDAO.CostCenterDAO;
import br.com.gft.MentorsDAO.MentorHistoryDAO;


public class MentorHistoryService {
	
	public void addMentorHistory(MentorHistory mentorhistory) throws Exception {
		MentorHistoryDAO.setup();
		MentorHistoryDAO mentorhistorydao = new MentorHistoryDAO();
		mentorhistorydao.insertMentorHistory(mentorhistory);
		
	}
	
	public List<MentorHistory> getMentorHistorys(){
		MentorHistoryDAO.setup();
		MentorHistoryDAO mentorhistorydao = new MentorHistoryDAO();
		List<MentorHistory> mentorhistory = mentorhistorydao.findMentorHistorys();
		return mentorhistory;
	}
	
	public void alterMentorHistory(MentorHistory mentorhistory){
		MentorHistoryDAO.setup();
		MentorHistoryDAO mentorhistorydao = new MentorHistoryDAO();
		mentorhistorydao.updateMentorHistory(mentorhistory);
	}
	
	public MentorHistory getMentorHistory(int employeeId){
		MentorHistory mentorhistory = new MentorHistory();
		MentorHistoryDAO.setup();
		MentorHistoryDAO mentorhistorydao = new MentorHistoryDAO();
		mentorhistory =  mentorhistorydao.findMentorHistory(employeeId);
		return mentorhistory;
	}
	
	/**
	 * this method is to set the finish date on table employee history
	 * @param id
	 * @param oldMente
	 * @param sysDate
	 */
	public void addFinishDate(String id , String oldMente ,Date sysDate){
		MentorHistoryDAO.setup();
		MentorHistoryDAO mentorhistorydao = new MentorHistoryDAO();
		List<MentorHistory> ments = mentorhistorydao.findMentorHistorys();
			if(ments.size() > 1){
			    for(int i=0 ; i< ments.size() ; i++ ){
			    	String aux = ments.get(i).getEmployee().getId();
						    	if(ments.get(i).getMentorId() != null  && ments.get(i).getFinishDate() == null){
							    	if (ments.get(i).getEmployee().getId().equals(id) && ments.get(i).getMentorId().equals(oldMente)) {
								    		ments.get(i).setFinishDate(sysDate);
								    		mentorhistorydao.updateMentorHistory(ments.get(i));
							    		}
						    	}
						    	
				    			if(ments.get(i).getTutorId() != null && ments.get(i).getFinishDate() == null){
							    	if (ments.get(i).getTutorId().equals(oldMente) && ments.get(i).getEmployee().getId().equals(id) && ments.get(i).getFinishDate().equals(null)) {
							    		ments.get(i).setFinishDate(sysDate);
							    		mentorhistorydao.updateMentorHistory(ments.get(i));
						    		}
				    			}
					    	}
			    	}

			    }
}


