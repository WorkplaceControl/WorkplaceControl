package br.com.gft.MentorsService;

import java.util.Date;
import java.util.List;

import br.com.gft.MentorsCommon.MentorHistory;
import br.com.gft.MentorsDAO.MentorHistoryDAO;

public class MentorHistoryService {

	public void addMentorHistory(MentorHistory mentorhistory) throws Exception {
		new MentorHistoryDAO().insertMentorHistory(mentorhistory);

	}

	public List<MentorHistory> getMentorHistorys() {
		return new MentorHistoryDAO().findMentorHistorys();
	}

	public void alterMentorHistory(MentorHistory mentorhistory) {
		new MentorHistoryDAO().updateMentorHistory(mentorhistory);
	}

	public MentorHistory getMentorHistory(int employeeId) {
		return new MentorHistoryDAO().findMentorHistory(employeeId);
	}

	/**
	 * this method is to set the finish date on table employee history
	 * 
	 * @param id
	 * @param oldMente
	 * @param sysDate
	 */
	public void addFinishDate(String id, String oldMente, Date sysDate) {
		MentorHistoryDAO mentorhistorydao = new MentorHistoryDAO();
		List<MentorHistory> ments = mentorhistorydao.findMentorHistorys();
		
		if (ments.size() > 1) {
			for (MentorHistory ment : ments) {
				if (ment.getMentorId() != null && ment.getFinishDate() == null) {
					if (ment.getEmployee().getId().equals(id) && ment.getMentorId().equals(oldMente)) {
						ment.setFinishDate(sysDate);
						mentorhistorydao.updateMentorHistory(ment);
					}
				}

				if (ment.getTutorId() != null && ment.getFinishDate() == null) {
					if (ment.getTutorId().equals(oldMente) && ment.getEmployee().getId().equals(id) && ment.getFinishDate().equals(null)) {
						ment.setFinishDate(sysDate);
						mentorhistorydao.updateMentorHistory(ment);
					}
				}
			}
		}
	}
	
}
