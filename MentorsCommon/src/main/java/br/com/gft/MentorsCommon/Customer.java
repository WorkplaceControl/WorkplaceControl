package br.com.gft.MentorsCommon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Id
	private String description;

	@Column
	private int active;

	@ManyToOne
	@JoinColumn(name = "unit_id", referencedColumnName = "id")
	private Unit unit;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
	private List<Project> project = new ArrayList<Project>();

	public Customer() {

	}

	public Customer(int id, String description, int unit_id) {
		super();
		this.id = id;
		this.description = description;

	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

}
