package br.com.gft.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsService.CostCenterService;
import br.com.gft.MentorsService.JobService;
import br.com.gft.logs.SystemLogs;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;
/**
 * 
 * @author mlav
 *
 *Class to control request and responses of pages Cost Center 
 */
@Controller
public class CostCenterControl {
	private String index;
	CostCenter costcenter = new CostCenter();
	CostCenterService service = new CostCenterService();
	
	/**
	 * this code is to show the page with allow user to read all cost centers active and inactive
	 */
	@RequestMapping(value = "/CostCenter", method = RequestMethod.GET)
	public String showCostCenter(@RequestParam(value = "page", required = false) Integer page, Model model) {
		Pagination pagination = new Pagination(service.getCostCenters().size(), page);
		
		model.addAttribute("url", "CostCenter");
		model.addAttribute("pagination", pagination);
		model.addAttribute("costcenter", service.getPagedCostCenters(pagination.getBegin(), pagination.getQuantity()));
				
		
		return "CostCenter";
	}
	
	/**
	 * this code is to get cost centers including inactive 
	 */
	@RequestMapping(value = "/CostCenterInactive", method = RequestMethod.GET)
	public String showCostCenterInactive(@RequestParam(value = "page", required = false) Integer page, Model model) {
	
		Pagination pagination = new Pagination(service.getCostCenters().size(), page);
		
		model.addAttribute("url", "CostCenter");
		model.addAttribute("pagination", pagination);
		model.addAttribute("costcenter", service.getPagedCostCentersInactive(pagination.getBegin(), pagination.getQuantity()));
				
		return "CostCenter";
	}
	
	/**
	 * this code is to show the page with allow user to open a page to register a cost center
	 */
	@RequestMapping(value = "/CostCenterRegistration", method = RequestMethod.GET)
	public String ShowJobRegistration(
			@ModelAttribute("CostCenter") CostCenter costcenter, Model model) {

		return "CostCenterRegistration";
	}
	
	/**
	 * this code allow user to  register a cost center
	 */
	@RequestMapping(value = "/ProcessCostCenterRegistration", method = RequestMethod.POST)
	public String ProcessJobRegistration(
			@ModelAttribute("CostCenter") CostCenter costcenter, Model model)
			throws Exception {
		model.addAttribute("CostCenter", costcenter);
		CostCenterService costcenterservice = new CostCenterService();
		costcenterservice.addCostCenter(costcenter);
		showCostCenter(null, model);
		
		new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Cost Center (Descrição): " + costcenter.getTitle().toUpperCase());
		return "CostCenter";
	}
	
	/**
	 * this code allow user to  open a page to Update a cost center
	 */
	@RequestMapping(value = "/CostCenterUpdate", method = RequestMethod.GET)
	public String ShowCostCenterRegistration(
			@RequestParam(value = "id") String id, Model model) {
		costcenter.setId(id);
		index = id;
		costcenter.setActive(0);
		CostCenterService costcenterservice = new CostCenterService();
		costcenter = costcenterservice.getCostCenter(costcenter.getId());
		model.addAttribute("CostCenter", costcenter);
		return "CostCenterUpdate";
	}

	/**
	 * this code allow user to  update a cost center
	 * @param costcenter
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessCostCenterUpdate", method = RequestMethod.POST)
	public String ProcessCostCenterInactivate(
			@ModelAttribute("CostCenter") CostCenter costcenter, Model model) {
		model.addAttribute("CostCenter", costcenter);
		CostCenterService costcenterservice = new CostCenterService();
		costcenter.setId(index);
		costcenterservice.alterCostCenter(costcenter);
		showCostCenter(null, model);
		
		new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTEROU o Cost Center (Descrição): " + costcenter.getTitle().toUpperCase());
		return "CostCenter";
	}
	
	/**
	 * this code is to inactive cost center
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CostCenterStatus", method = RequestMethod.GET)
	public String ProcessCostCenterUpdate(@RequestParam(value = "id") String id,
										  @RequestParam(value = "status") int status, Model model) {
		
		costcenter = service.getCostCenter(id);
		int ControlMessages;
		int action = costcenter != null ? 1 : 0;

		if (action == 1) {
			costcenter.setActive(status == 1 ? 0 : 1);
			
			service.alterCostCenter(costcenter);
			
			ControlMessages = 7;
		} else {
			ControlMessages = 8;
		}
		
		showCostCenter(null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 1 ? " ATIVOU" : " DESATIVOU") + " o Cost Center (ID): " + id);
		return "CostCenter";
	}
	
	
}
