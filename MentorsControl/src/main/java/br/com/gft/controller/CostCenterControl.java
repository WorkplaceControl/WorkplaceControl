package br.com.gft.controller;

import java.util.List;

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
import br.com.gft.share.Pagination;
/**
 * 
 * @author mlav
 *
 *Class to control request and responses of pages Cost Center 
 */
@Controller
public class CostCenterControl {
	private String index;
	
	/**
	 * this code is to show the page with allow user to read all cost centers active and inactive
	 */
	@RequestMapping(value = "/CostCenter", method = RequestMethod.GET)
	public String showCostCenter(@RequestParam(value = "page", required = false) Integer page, Model model) {
		CostCenterService service = new CostCenterService();
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
		CostCenterService service = new CostCenterService();
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
		return "CostCenter";
	}
	
	/**
	 * this code allow user to  open a page to Update a cost center
	 */
	@RequestMapping(value = "/CostCenterUpdate", method = RequestMethod.GET)
	public String ShowCostCenterRegistration(
			@RequestParam(value = "id") String id, Model model) {
		CostCenter costcenter = new CostCenter();
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
		return "CostCenter";
	}
	
	/**
	 * this code is to inactive cost center
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessCostCenterInactivate", method = RequestMethod.GET)
	public String ProcessCostCenterUpdate(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "title") String title, Model model) {
		CostCenter costcenter = new CostCenter();
		costcenter.setId(id);
		costcenter.setTitle(title);
		costcenter.setActive(1);
		CostCenterService costcenterservice = new CostCenterService();
		costcenterservice.alterCostCenter(costcenter);
		showCostCenter(null, model);
		return "CostCenter";
	}
	
	
}
