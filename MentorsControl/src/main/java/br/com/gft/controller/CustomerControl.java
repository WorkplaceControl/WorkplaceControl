package br.com.gft.controller;

import java.util.List;

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
import br.com.gft.share.Pagination;

/**
 * This class to control request and responses of Customer
 * @author mlav
 *
 */
@Controller
public class CustomerControl {

	private int index;
	/**
	 * this method is show page to read Customer
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Customer", method = RequestMethod.GET)
	public String showCustomer(@RequestParam(value = "page", required = false) Integer page, Model model) {
		CustomerService service = new CustomerService();
		List<Customer> customer = service.getCustomers();
		
		
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
		CustomerService service = new CustomerService();
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
		Customer customer = new Customer();
		customer.setDescription(description);
		customer.setActive(0);
		customer.setUnit(unit);
		CustomerService customerservice = new CustomerService();
		customerservice.addCustomer(customer);
		showCustomer(null, model);
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
		Customer customer = new Customer();
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
	@RequestMapping(value = "/CustomerUpdate", method = RequestMethod.POST)
	public String ProcessProjectUpdate(
			@RequestParam("description") String description,
			@RequestParam("unit") int unitId, Model model) {
		Unit unit = new UnitService().getUnit(unitId);
		Customer customer = new Customer();
		customer.setId(index);
		customer.setDescription(description);
		customer.setActive(0);
		customer.setUnit(unit);
		CustomerService customerservice = new CustomerService();
		customerservice.alterCustomer(customer);
		showCustomer(null, model);
		return "Customer";
	}
	
	/**
	 * this method is to inactive a Customer
	 * @param customerId
	 * @param unitId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CustomerInactivate", method = RequestMethod.GET)
	public String ProcessProjectInactivate(@RequestParam("id") int customerId,
			@RequestParam("unit") int unitId, Model model) {
		Unit unit = new UnitService().getUnit(unitId);
		Customer customer = new Customer();
		CustomerService customerservice = new CustomerService();
		customer = customerservice.getCustomer(customerId);
		customer.setUnit(unit);
		customer.setActive(1);
		customerservice.alterCustomer(customer);
		showCustomer(null, model);
		return "Customer";
	}
}
