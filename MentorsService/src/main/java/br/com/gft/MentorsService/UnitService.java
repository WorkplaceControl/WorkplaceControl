package br.com.gft.MentorsService;

import java.util.List;

import javax.xml.ws.ServiceMode;

import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsDAO.JobDAO;
import br.com.gft.MentorsDAO.UnitDAO;

@ServiceMode
public class UnitService {

	public void addUnit(Unit unit){
		new UnitDAO().insertUnit(unit);
	}
	
	public List<Unit> getUnits(){
		return new UnitDAO().findUnits();
	}
	
	public List<Unit> getUnitsInactive(){
		return new UnitDAO().findUnitsInactive();
	}

	public List<Unit> getPagedUnits(int inicio, int quantidade){
		return new UnitDAO().findPagedUnits(inicio, quantidade);
	}
	
	public List<Unit> getPagedUnitsInactive(int inicio, int quantidade){
		return new UnitDAO().findPagedUnitsInactive(inicio, quantidade);
	}
	
	public Unit getUnit(String unitId){
		return new UnitDAO().findUnit(unitId);
	}
	
	public void alterUnit(Unit unit){
		new UnitDAO().updateUnit(unit);
	}
	
	public List<Unit> getUnits(String search){
		return new UnitDAO().findUnits(search);
	}
	
	public List<Unit> getUnitsInactive(String search){
		return new UnitDAO().findUnitsInactive(search);
	}
	
	public List<Unit> getPagedUnits(String search, int begin, int quantity){
		return new UnitDAO().findUnits(search, begin, quantity);
	}
	
	public List<Unit> getPagedUnitsInactive(String search, int begin, int quantity){
		return new UnitDAO().findUnitsInactive(search, begin, quantity);
	}
	
	
}
