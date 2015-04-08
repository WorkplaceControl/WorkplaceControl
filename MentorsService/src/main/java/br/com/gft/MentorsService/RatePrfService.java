package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.RatePrf;
import br.com.gft.MentorsDAO.RatePrfDAO;

public class RatePrfService {

	public RatePrf getRatePrf(int ratePrfId){
		return new RatePrfDAO().findRatePrf(ratePrfId);
	}
	
	public List<RatePrf> getRatePrfs(){
		return new RatePrfDAO().findRatePrfs();
	}
	
}
