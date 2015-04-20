package br.com.gft.MentorsCommon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Employee")
public class Mentor implements Serializable{
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "job_id", referencedColumnName = "id")
	private Job job;
	
	@Column(name="rate_prf_id")
	private int rate;
	
	@Column(name="cost_center_id")
	private String cost;
	
	@Column(name="mentor_id")
	public String mentorId;
	
	@Column(name="qtymentee")
	private int qtyMentee;
	
	public Mentor() {
	}
	
	public Mentor(String name, int qtyMentee, Job job) {
		super();
		this.name = name;
		this.qtyMentee = qtyMentee;
		this.job = job;
	}
	
	public Mentor(String id, Job job, String cost, int rate) {
		super();
		this.id = id;
		this.job = job;
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
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getMentorId() {
		return mentorId;
	}

	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}
	
	public int getQtyMentee() {
		return qtyMentee;
	}

	public void setQtyMentee(int qtyMentee) {
		this.qtyMentee = qtyMentee;
	}
	
}