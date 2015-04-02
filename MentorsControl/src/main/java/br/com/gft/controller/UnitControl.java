package br.com.gft.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.CostCenterService;
import br.com.gft.MentorsService.JobService;
import br.com.gft.MentorsService.UnitService;
import br.com.gft.share.Pagination;

/**
 * this Class is to control requests and responses from unit pages
 * @author mlav
 *
 */
@Controller
public class UnitControl {
	private int index;
	
	/**
	 * this method is to show units page to read jobs registered
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/Unit" , method = RequestMethod.GET)
	public String showUnit(@RequestParam(value = "page", required = false) Integer page, Model model) {
		UnitService service = new UnitService();
		Pagination pagination = new Pagination(service.getUnits().size(), page);
		
		model.addAttribute("url", "Unit");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Unit", service.getPagedUnits(pagination.getBegin(), pagination.getQuantity()));
				
		return "Unit";
		
	}
	
	/**
	 * this method is to get Units including inactive
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/UnitInactive" , method = RequestMethod.GET)
	public String showUnitInactive(@RequestParam(value = "page", required = false) Integer page, Model model) {
		UnitService service = new UnitService();
		Pagination pagination = new Pagination(service.getUnitsInactive().size(), page);
		
		model.addAttribute("url", "UnitInactive");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Unit", service.getPagedUnitsInactive(pagination.getBegin(), pagination.getQuantity()));
				
		return "Unit";
		
	}
	
	/**
	 * this method show page of Unit registration
	 * @param unit
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/UnitRegistration" , method = RequestMethod.GET)
	public String showUnitForm(@ModelAttribute("Unit") Unit unit, Model model){
		return "UnitRegistration";
		
	}
	
	/**
	 * this method is to register Unit
	 * @param unit
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ProcessUnitRegistration" , method = RequestMethod.POST)
	public String ProcessUnitForm(@ModelAttribute("Unit") Unit unit, Model model){
		UnitService unitservice = new UnitService();
		unitservice.addUnit(unit);
		showUnit(null, model);
		return "Unit";
		
	}
	
	/**
	 * this method is to show page to update Unit informations
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UnitUpdate", method = RequestMethod.GET)
	public String ShowUnitRegistration(@RequestParam(value="id") int id, Model model) {
		Unit unit = new Unit();
		unit.setId(id);
		index = id;
		unit.setActive(0);
		UnitService unitservice = new UnitService();
		unit = unitservice.getUnit(unit.getId());
		model.addAttribute("Unit" , unit);
		return "UnitUpdate";
	}
	
	/**
	 * this method is to register a update of Unit informations
	 * @param unit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UnitUpdate", method=RequestMethod.POST)
	public String ProcessUnitInactivate(@ModelAttribute("Unit") Unit unit, Model model) {
		model.addAttribute("Unit" , unit);
		UnitService unitservice = new UnitService();
		unit.setId(index);
		unitservice.alterUnit(unit);
		showUnit(null, model);
		return "Unit";
	}
	
	/**
	 * this method is to register a inactive Unit
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UnitInactivate", method=RequestMethod.GET)
	public String ProcessCostCenterUpdate(@RequestParam(value="id") int id,@RequestParam(value="title") String title , Model model) {
		Unit unit = new Unit();
		unit.setId(id);
		unit.setDescription(title);
		unit.setActive(1);
		UnitService unitservice = new UnitService();
		unitservice.alterUnit(unit);
		showUnit(null, model);
		return "Unit";
	}
	
}
