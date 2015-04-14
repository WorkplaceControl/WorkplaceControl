package br.com.gft.MentorsCommon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project implements Serializable{

	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Id
	private String description;

	@Column
	private int active;

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer = new Customer();

	@OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
	private List<EmployeeAssignment> employeeAssign = new ArrayList<EmployeeAssignment>();

	public Project() {
	}

	public Project(int id, String description, Customer customer) {
		super();
		this.id = id;
		this.description = description;
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<EmployeeAssignment> getEmployeeAssign() {
		return employeeAssign;
	}

	public void setEmployeeAssign(List<EmployeeAssignment> employeeAssign) {
		this.employeeAssign = employeeAssign;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
