package br.com.gft.MentorsCommon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "Employee")
public class Mentor implements Serializable{
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="job_id")
	private String jobId;
	
	@Column(name="rate_prf_id")
	private int rate;
	
	@Column(name="cost_center_id")
	private String cost;
	
	@Column(name="qtymentee")
	private int qtyMentee;
	
	public Mentor() {
	}
	
	public Mentor(String name, int qtyMentee, String jobId) {
		super();
		this.name = name;
		this.qtyMentee = qtyMentee;
		this.jobId = jobId;
	}
	
	public Mentor(String id, String jobId, String cost, int rate) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.cost = cost;
		this.rate= rate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public int getQtyMentee() {
		return qtyMentee;
	}

	public void setQtyMentee(int qtyMentee) {
		this.qtyMentee = qtyMentee;
	}

}