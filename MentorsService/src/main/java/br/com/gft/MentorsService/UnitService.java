package br.com.gft.MentorsService;

import java.util.List;

import javax.xml.ws.ServiceMode;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsDAO.CostCenterDAO;
import br.com.gft.MentorsDAO.UnitDAO;

@ServiceMode
public class UnitService {

	public void addUnit(Unit unit){
		UnitDAO unitdao = new UnitDAO();
		UnitDAO.setup();
		unitdao.insertUnit(unit);
	}
	
	public List<Unit> getUnitsInactive(){
		UnitDAO.setup();
		UnitDAO unitdao = new UnitDAO();
		List<Unit> unit = unitdao.findUnitsInactive();
		return unit;
	}
	
	public List<Unit> getUnits(){
		UnitDAO.setup();
		UnitDAO unitdao = new UnitDAO();
		List<Unit> unit = unitdao.findUnits();
		return unit;
	}
	
	public Unit getUnit(long unitId){
		Unit unit = new Unit();
		UnitDAO.setup();
		UnitDAO unitdao = new UnitDAO();
		unit = unitdao.findUnit(unitId);;
		return unit;
	}
	
	public void alterUnit(Unit unit){
		UnitDAO.setup();
		UnitDAO unitdao = new UnitDAO();
		unitdao.updateUnit(unit);
	}
	
}
