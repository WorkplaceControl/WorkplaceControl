package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsDAO.CostCenterDAO;
import br.com.gft.MentorsDAO.JobDAO;

public class CostCenterService {

	public void addCostCenter(CostCenter costcenter) throws Exception {
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		costcenterdao.insertCostCenter(costcenter);

	}

	public List<CostCenter> getCostCenters() {
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		List<CostCenter> costcenter = costcenterdao.findCostCenters();
		return costcenter;
	}

	public List<CostCenter> getCostCentersInactive() {
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		List<CostCenter> costcenter = costcenterdao.findCostCentersInactive();
		return costcenter;
	}
	
	public List<CostCenter> getPagedCostCenters(int inicio, int quantidade){
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		List<CostCenter> costcenter = costcenterdao.findPagedCostCenters(inicio, quantidade);
		return costcenter;
	}
	
	public List<CostCenter> getPagedCostCentersInactive(int inicio, int quantidade){
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		List<CostCenter> costcenter = costcenterdao.findPagedCostCentersInactive(inicio, quantidade);
		return costcenter;
	}

	public CostCenter getCostCenter(String costCenterId) {
		CostCenter costcenter = new CostCenter();
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		costcenter = costcenterdao.findCost(costCenterId);
		return costcenter;
	}

	public void alterCostCenter(CostCenter costcenter) {
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		costcenterdao.updateCostCenter(costcenter);
	}

	public void showCost() {

	}
}
