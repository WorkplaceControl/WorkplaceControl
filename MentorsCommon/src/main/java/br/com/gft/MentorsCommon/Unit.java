package br.com.gft.MentorsCommon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "UNIT")
public class Unit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Id
	private String description;

	@Column
	private int active;

	@OneToMany(mappedBy = "unit")
	private List<Customer> customer = new ArrayList<Customer>();

	public Unit() {

	}

	public Unit(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
