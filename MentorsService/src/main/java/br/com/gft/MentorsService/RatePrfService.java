package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.RatePrf;
import br.com.gft.MentorsDAO.JobDAO;
import br.com.gft.MentorsDAO.ProjectDAO;
import br.com.gft.MentorsDAO.RatePrfDAO;

public class RatePrfService {

	public RatePrf getRatePrf(int ratePrfId){
		RatePrf ratePrf = new RatePrf();
		RatePrfDAO.setup();
		RatePrfDAO ratePrfdao = new RatePrfDAO();
		ratePrf = ratePrfdao.findRatePrf(ratePrfId);
		return ratePrf;
	}
	
	
	public List<RatePrf> getRatePrfs(){
		RatePrfDAO.setup();
		RatePrfDAO rateprfdao = new RatePrfDAO();
		List<RatePrf> rateprf = rateprfdao.findRatePrfs();
		return rateprf;
	}
	
}
