package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsDAO.CustomerDAO;
import br.com.gft.MentorsDAO.ProjectDAO;
import br.com.gft.MentorsDAO.UnitDAO;

public class CustomerService {

	public List<Unit> getUnits(){
		UnitDAO.setup();
		UnitDAO unitdao = new UnitDAO();
		return unitdao.findUnits();
		
	}
	
	public void addCustomer(Customer customer) throws Exception{
		CustomerDAO.setup();
		CustomerDAO customerdao = new CustomerDAO();
		customerdao.insertCustomer(customer);
	}
	
	public Customer getCustomer(int customerId){
		CustomerDAO.setup();
		CustomerDAO customerdao = new CustomerDAO();
		Customer customer = new Customer();
		customer = customerdao.findCustomer(customerId);
		return customer;	
	}
	
	public List<Customer> getCustomers(){
		CustomerDAO.setup();
		CustomerDAO customerdao = new CustomerDAO();
		List<Customer> customer = customerdao.findCustomers();
		return customer;
	}
	
	public List<Customer> getCustomersInactive(){
		CustomerDAO.setup();
		CustomerDAO customerdao = new CustomerDAO();
		List<Customer> customer = customerdao.findCustomersInactive();
		return customer;
	}
	
	public void alterCustomer(Customer customer){
		CustomerDAO.setup();
		CustomerDAO customerdao = new CustomerDAO();
		customerdao.updateCustomer(customer);
	}
	
	
}
