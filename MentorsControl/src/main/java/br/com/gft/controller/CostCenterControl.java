package br.com.gft.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsService.CostCenterService;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;
import br.com.gft.share.SystemLogs;
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
	public String showCostCenter(@RequestParam(value = "page", required = false) Integer page,
								 @RequestParam(value = "s", required = false) String search, Model model) {
		
		Pagination pagination = null;

		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getCostCenters(search).size(), page);
			model.addAttribute("costcenter", service.getPagedCostCenters(search, pagination.getBegin(), pagination.getQuantity()));
		}else {
			pagination = new Pagination(service.getCostCenters().size(), page);
			model.addAttribute("costcenter", service.getPagedCostCenters(pagination.getBegin(), pagination.getQuantity()));
		}
		model.addAttribute("url", "CostCenter");
		model.addAttribute("pagination", pagination);
		
		return "CostCenter";
	}
	
	/**
	 * this code is to get cost centers including inactive 
	 */
	@RequestMapping(value = "/CostCenterInactive", method = RequestMethod.GET)
	public String showCostCenterInactive(@RequestParam(value = "page", required = false) Integer page,
										@RequestParam(value = "s", required = false) String search, Model model) {
		
		Pagination pagination = null;
		
		if (search != null && !search.equals("")) {
		pagination = new Pagination(service.getCostCentersInactive(search).size(), page);
		model.addAttribute("costcenter", service.getPagedCostCentersInactive(search, pagination.getBegin(), pagination.getQuantity()));
		}else {
		pagination = new Pagination(service.getCostCentersInactive().size(), page);
		model.addAttribute("costcenter", service.getPagedCostCentersInactive(pagination.getBegin(), pagination.getQuantity()));
		}		
		model.addAttribute("url", "CostCenterInactive");
		model.addAttribute("pagination", pagination);
				
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
		service.addCostCenter(costcenter);
		showCostCenter(null, null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ADDED the Cost Center (Description): " + costcenter.getTitle().toUpperCase());
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
		costcenter = service.getCostCenter(costcenter.getId());
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
		costcenter.setId(index);
		service.alterCostCenter(costcenter);
		showCostCenter(null, null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTER the Cost Center (Description): " + costcenter.getTitle().toUpperCase());
		return "CostCenter";
	}
	
	/**
	 * this code is to inactive cost center
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CostCenterStatus", method = RequestMethod.POST)
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
		
		showCostCenter(null, null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 1 ? " ENABLE" : " DISABLE") + " the Cost Center (ID): " + id);
		return "CostCenter";
	}
	
	
}
