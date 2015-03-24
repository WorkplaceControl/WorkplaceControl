package br.com.gft.MentorsCommon;

import java.io.Serializable;



public class Mentor implements Serializable{
	private String id;
	private String name;
	private int qtyMentee;
	private String jobId;
	private String cost;
	private int rate;

	public Mentor() {
	}

	public Mentor(String name, int qtyMentee, String jobId) {
		super();
		this.name = name;
		this.qtyMentee = qtyMentee;
		this.jobId = jobId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQtyMentee() {
		return qtyMentee;
	}

	public void setQtyMentee(int qtyMentee) {
		this.qtyMentee = qtyMentee;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
