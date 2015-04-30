package br.com.gft.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.MentorHistory;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.MentorsService.MentorHistoryService;
import br.com.gft.share.Paths;

/**
 * this Class is to Control request and responses of Mentor History
 *
 */
@Controller
public class MentorHistoryControl {
	private int mentorHistoryControlMessage;
	/**
	 * this method is to update informations of mentor history
	 * @param histId
	 * @param employeeId
	 * @param start_date
	 * @param finish_date
	 * @param jobId
	 * @param costCenterId
	 * @param ratePrfId
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/ProcessMentorsHistoryUpdate", method=RequestMethod.POST)
	public String ProcessProjectUpdate(	@RequestParam(Paths.ATTRIBUTE_MHC_HIST_ID) int histId , 
										@RequestParam(Paths.ATTRIBUTE_MHC_EMPLOYEE_ID) String employeeId , 
										@RequestParam(Paths.ATTRIBUTE_MHC_START_DATE) String start_date ,
										@RequestParam(Paths.ATTRIBUTE_MHC_FINISH_DATE) String finish_date ,
										@RequestParam(Paths.ATTRIBUTE_MHC_JOB) String jobId ,
										@RequestParam(Paths.ATTRIBUTE_MHC_COST) String costCenterId ,
										@RequestParam(Paths.ATTRIBUTE_MHC_RATE) int ratePrfId , 
										Model model) throws Exception {
 
		Date start = new EmployeeService().formatdate(start_date); 
		Date finish = new EmployeeService().formatdate(finish_date);
		
		if(start.after(finish)){
			mentorHistoryControlMessage = 1;
		}else{
			MentorHistoryService mentorhistoryservice = new MentorHistoryService();
			MentorHistory mentorhistory = mentorhistoryservice.getMentorHistory(histId);
			
			mentorhistory.setStartDate(start);
			mentorhistory.setFinishDate(finish);
			mentorhistoryservice.alterMentorHistory(mentorhistory);
			
			mentorHistoryControlMessage = 0;
		}
		
		model.addAttribute("mentorHistoryControlMessage", mentorHistoryControlMessage);
		
		new EmployeeControl().EmployeeUpdate(employeeId, model);
		
		return "EmployeeUpdate";
	
	}
	
	
}
