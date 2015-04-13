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

import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.ProjectService;
import br.com.gft.MentorsService.UnitService;
import br.com.gft.logs.SystemLogs;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;

/**
 * This class to control request and responses of Customer
 * @author mlav
 *
 */
@Controller
public class CustomerControl {

	private int index;
	Customer customer = new Customer();
	CustomerService service = new CustomerService();
	/**
	 * this method is show page to read Customer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Customer", method = RequestMethod.GET)
	public String showCustomer(@RequestParam(value = "page", required = false) Integer page, Model model) {
		
		
Pagination pagination = new Pagination(service.getCustomers().size(), page);
		
		model.addAttribute("url", "job");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Customer", service.getPagedCustomers(pagination.getBegin(), pagination.getQuantity()));
		return "Customer";
	}
	
	/**
	 * this method is to get customer including inactive
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerInactive", method = RequestMethod.GET)
	public String showCustomerInactive(@RequestParam(value = "page", required = false) Integer page, Model model) {
		Pagination pagination = new Pagination(service.getCustomersInactive().size(), page);
		
		model.addAttribute("url", "Customer");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Customer", service.getPagedCustomersInactive(pagination.getBegin(), pagination.getQuantity()));
		return "Customer";
	}
	
	/**
	 * this method is to show page to register new Customer
	 * @param customer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerRegistration", method = RequestMethod.GET)
	public String showCustomerResgistration(
			@ModelAttribute("Customer") Customer customer, Model model) {
		CustomerService customerservice = new CustomerService();
		List<Unit> units = customerservice.getUnits();
		model.addAttribute("unit", units);
		return "CustomerRegistration";
	}
	
	
	/**
	 * this method is to register new Customer
	 * @param description
	 * @param unitId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ProcessCustomerRegistration", method = RequestMethod.POST)
	public String processCustomerRegistration(
			@RequestParam("description") String description,
			@RequestParam("unit") int unitId, Model model) throws Exception {
		Unit unit = new UnitService().getUnit(unitId);
		customer.setDescription(description);
		customer.setActive(0);
		customer.setUnit(unit);
		CustomerService customerservice = new CustomerService();
		customerservice.addCustomer(customer);
		showCustomer(null, model);
		
		new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Customer (Descri��o): " + description.toUpperCase());
		return "Customer";
	}

	/**
	 * this method is to Update Customer informations 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerUpdate", method = RequestMethod.GET)
	public String ShowCustomerUpdate(@RequestParam(value = "id") int id,
			Model model) {
		index = id;
		customer.setActive(0);
		CustomerService customerservice = new CustomerService();
		customer = customerservice.getCustomer(id);
		List<Unit> unit = customerservice.getUnits();
		model.addAttribute("unit", unit);
		model.addAttribute("Customer", customer);
		return "CustomerUpdate";
	}

	/**
	 * this method is to register a Update of Customer
	 * @param description
	 * @param unitId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ProcessCustomerUpdate", method = RequestMethod.POST)
	public String ProcessCustomerUpdate(
			@RequestParam("description") String description,
			@RequestParam("unit") int unitId, Model model) {
		Unit unit = new UnitService().getUnit(unitId);		
		customer.setId(index);
		customer.setDescription(description);
		customer.setActive(0);
		customer.setUnit(unit);
		CustomerService customerservice = new CustomerService();
		customerservice.alterCustomer(customer);
		showCustomer(null, model);
		
		new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTEROU o Customer (Descri��o): " + description.toUpperCase());
		return "Customer";
	}
	
	/**
	 * this method is to inactive a Customer
	 * @param customerId
	 * @param unitId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerStatus", method = RequestMethod.GET)
	public String ProcessProjectInactivate(@RequestParam("id") int id,
										   @RequestParam("status") int status, Model model) {
		customer = service.getCustomer(id);
		int ControlMessages;
		int action = customer != null ? 1 : 0;

		if (action == 1) {
			customer.setActive(status == 1 ? 0 : 1);
			
			service.alterCustomer(customer);
			
			ControlMessages = 7;
		} else {
			ControlMessages = 8;
		}
		
		showCustomer(null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, ControlMessages);
		
		new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 1 ? " ATIVOU" : " DESATIVOU") + " o Customer (ID): " + id);
		return "Customer";
	}
}
