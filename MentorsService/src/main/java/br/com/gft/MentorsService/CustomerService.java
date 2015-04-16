package br.com.gft.MentorsService;

import java.util.List;

import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsDAO.CustomerDAO;
import br.com.gft.MentorsDAO.UnitDAO;

public class CustomerService {

	public List<Unit> getUnits(){
		return new UnitDAO().findUnits();
	}
	
	public void addCustomer(Customer customer) throws Exception{
		new CustomerDAO().insertCustomer(customer);
	}
	
	public Customer getCustomer(String customerId){
		return new CustomerDAO().findCustomer(customerId);	
	}
	
	public List<Customer> getCustomers(){
		return new CustomerDAO().findCustomers();
	}
	
	public List<Customer> getCustomersInactive(){
		return new CustomerDAO().findCustomersInactive();
	}
	
	public List<Customer> getPagedCustomers(int inicio, int quantidade){
		return new CustomerDAO().findPagedCustomers(inicio, quantidade);
	}
	
	public List<Customer> getPagedCustomersInactive(int inicio, int quantidade){
		return new CustomerDAO().findPagedCustomersInactive(inicio, quantidade);
	}
	
	public void alterCustomer(Customer customer){
		new CustomerDAO().updateCustomer(customer);
	}
	public List<Customer> getJobs(String search){
		return new CustomerDAO().findCustomers(search);
	}
	
	public List<Customer> getCustomersInactive(String search){
		return new CustomerDAO().findCustomersInactive(search);
	}
	
	public List<Customer> getCustomers(String search, int begin, int quantity){
		return new CustomerDAO().findCustomers(search, begin, quantity);
	}
	
	public List<Customer> getCustomersInactive(String search, int begin, int quantity){
		return new CustomerDAO().findCustomersInactive(search, begin, quantity);
	}
	
}
