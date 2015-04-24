package br.com.gft.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.UnitService;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;
import br.com.gft.share.SystemLogs;

/**
 * This class to control request and responses of Customer
 * @author mlav
 *
 */
@Controller
public class CustomerControl {

	private int index;
	CustomerService service = new CustomerService();
	
	/**
	 * this method is show page to read Customer
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Customer", method = RequestMethod.GET)
	public String showCustomer(@RequestParam(value = "page", required = false) Integer page,
							   @RequestParam(value = "s", required = false) String search, Model model) {
		
		Pagination pagination = null;

		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getCustomers(search).size(), page);
			model.addAttribute("Customer", service.getPagedCustomers(search, pagination.getBegin(), pagination.getQuantity()));
		}else {
			pagination = new Pagination(service.getCustomers().size(), page);
			model.addAttribute("Customer", service.getPagedCustomers(pagination.getBegin(), pagination.getQuantity()));
		}

		

		model.addAttribute("url", "Customer");
		model.addAttribute("pagination", pagination);
		
		return "Customer";
	}
	
	/**
	 * this method is to get customer including inactive
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerInactive", method = RequestMethod.GET)
	public String showCustomerInactive(@RequestParam(value = "page", required = false) Integer page,
									   @RequestParam(value = "s", required = false) String search, Model model) {
		
		Pagination pagination = null;

		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getCustomersInactive(search).size(), page);
			model.addAttribute("Customer", service.getPagedCustomersInactive(search, pagination.getBegin(), pagination.getQuantity()));
		}else {
			pagination = new Pagination(service.getCustomersInactive().size(), page);
			model.addAttribute("Customer", service.getPagedCustomersInactive(pagination.getBegin(), pagination.getQuantity()));
		}
		
		model.addAttribute("url", "CustomerInactive");
		model.addAttribute("pagination", pagination);

		return "Customer";
	}
	
	/**
	 * this method is to show page to register new Customer
	 * @param customer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerRegistration", method = RequestMethod.GET)
	public String showCustomerResgistration(@ModelAttribute("Customer") Customer customer, Model model) {
		model.addAttribute("unit", new CustomerService().getUnits());
		
		return "CustomerRegistration";
	}
	
	
	/**
	 * this method is to register new Customer
	 * 
	 * @param description
	 * @param unitId
	 * @param model
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/ProcessCustomerRegistration", method = RequestMethod.POST)
	public String processCustomerRegistration(@RequestParam("description") String description,
			@RequestParam("unit") String unitId, 
			Model model) throws Exception {
		Customer customer = new Customer();
		
		customer.setDescription(description);
		customer.setActive(0);
		customer.setUnit(new UnitService().getUnit(unitId));
		
		new CustomerService().addCustomer(customer);
		
		showCustomer(null, null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ADDED o Customer (Description): " + description.toUpperCase());
		
		return "Customer";
	}

	/**
	 * this method is to Update Customer informations 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerUpdate", method = RequestMethod.GET)
	public String ShowCustomerUpdate(@RequestParam(value = "id") String id,
			Model model) {
		Customer customer = new Customer();
		
		
		List<Unit> unit = service.getUnits();
		
		customer = service.getCustomer(id);
		customer.setActive(0);
		
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
	public String ProcessCustomerUpdate(@RequestParam("description") String description,
			@RequestParam("unit") String unitId, 
			Model model) {
		Customer customer = new Customer();
		
		customer.setId(index);
		customer.setDescription(description);
		customer.setActive(0);
		customer.setUnit(new UnitService().getUnit(unitId));
		
		new CustomerService().alterCustomer(customer);
		
		showCustomer(null, null, model);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " ALTER the Customer (Description): " + description.toUpperCase());
		return "Customer";
	}
	
	/**
	 * this method is to inactive a Customer
	 * @param customerId
	 * @param unitId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerStatus", method = RequestMethod.POST)
	public String ProcessProjectInactivate(@RequestParam("id") String id, 
			@RequestParam("status") int status, 
			Model model) {
		
		
		Customer customer = service.getCustomer(id);
		
		int controlMessages;
		int action = customer != null ? 1 : 0;

		if (action == 1) {
			customer.setActive(status == 1 ? 0 : 1);
			
			service.alterCustomer(customer);
			
			controlMessages = 7;
		} else {
			controlMessages = 8;
		}
		
		showCustomer(null, null, model);
		model.addAttribute(Paths.ATTRIBUTE_CONTROL_MESSAGES, controlMessages);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + (status == 1 ? " ENABLE" : " DISABLE") + " the Customer (ID): " + id);
		return "Customer";
	}
}
