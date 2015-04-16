package br.com.gft.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.UnitService;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;
import br.com.gft.share.SystemLogs;

/**
 * this Class is to control requests and responses from unit pages
 * @author mlav
 *
 */
@Controller
public class UnitControl {
	private int index;
	Unit unit = new Unit();
	UnitService service = new UnitService();
	
	/**
	 * this method is to show units page to read jobs registered
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/Unit" , method = RequestMethod.GET)
	public String showUnit(@RequestParam(value = "page", required = false) Integer page, Model model) {
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
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Unit (Descrição): " + unit.getDescription().toUpperCase());
		return "Unit";
		
	}
	
	/**
	 * this method is to show page to update Unit informations
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UnitUpdate", method = RequestMethod.GET)
	public String ShowUnitRegistration(@RequestParam(value="id") String id, Model model) {
		model.addAttribute("Unit" , new UnitService().getUnit(id));
		
		return "UnitUpdate";
	}
	
	/**
	 * this method is to register a update of Unit informations
	 * @param unit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessUnitUpdate", method=RequestMethod.POST)
	public String ProcessUnitInactivate(@ModelAttribute("Unit") Unit unit, Model model) {
		model.addAttribute("Unit" , unit);
		UnitService unitservice = new UnitService();
		unit.setId(index);
		unitservice.alterUnit(unit);
		showUnit(null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTEROU o Unit (Descrição): " + unit.getDescription().toUpperCase());
		return "Unit";
	}
	
	/**
	 * this method is to register a inactive Unit
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/UnitStatus", method=RequestMethod.POST)
	public String ProcessCostCenterUpdate(@RequestParam(value="id") String id,
										  @RequestParam(value="status") int status, Model model) {
		unit = service.getUnit(id);
		
		int ControlMessages;
		int action = unit!= null ? 1 : 0;

		if (action == 1) {
			unit.setActive(status == 1 ? 0 : 1);
			
			service.alterUnit(unit);
			
			ControlMessages = 7;
		} else {
			ControlMessages = 8;
		}
		
		showUnit(null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 1 ? " ATIVOU" : " DESATIVOU") + " o Unit (ID): " + id);
		
		return "Unit";
	}
	
}
