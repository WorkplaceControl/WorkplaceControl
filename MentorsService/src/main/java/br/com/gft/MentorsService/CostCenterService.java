package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsDAO.CostCenterDAO;
import br.com.gft.MentorsDAO.JobDAO;

public class CostCenterService {

	public void addCostCenter(CostCenter costcenter) throws Exception {
		new CostCenterDAO().insertCostCenter(costcenter);
	}

	public List<CostCenter> getCostCenters() {
		return new CostCenterDAO().findCostCenters();
	}

	public List<CostCenter> getCostCentersInactive() {
		return new CostCenterDAO().findCostCentersInactive();
	}
	
	public List<CostCenter> getPagedCostCenters(int inicio, int quantidade){
		return new CostCenterDAO().findPagedCostCenters(inicio, quantidade);
	}
	
	public List<CostCenter> getPagedCostCentersInactive(int inicio, int quantidade){
		return new CostCenterDAO().findPagedCostCentersInactive(inicio, quantidade);
	}

	public CostCenter getCostCenter(String costCenterId) {
		return new CostCenterDAO().findCost(costCenterId);
	}

	public void alterCostCenter(CostCenter costcenter) {
		new CostCenterDAO().updateCostCenter(costcenter);
	}
	public List<CostCenter> getCostCenters(String search){
		return new CostCenterDAO().findCostCenters(search);
	}
	
	public List<CostCenter> getCostCentersInactive(String search){
		return new CostCenterDAO().findCostCentersInactive(search);
	}
	
	public List<CostCenter> getPagedCostCenters(String search, int begin, int quantity){
		return new CostCenterDAO().findCostCenters(search, begin, quantity);
	}
	
	public List<CostCenter> getPagedCostCentersInactive(String search, int begin, int quantity){
		return new CostCenterDAO().findCostCentersInactive(search, begin, quantity);
	}
	
}
