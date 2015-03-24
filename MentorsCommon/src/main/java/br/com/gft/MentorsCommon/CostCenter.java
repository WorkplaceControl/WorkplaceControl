package br.com.gft.MentorsCommon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COST_CENTER")
public class CostCenter implements Serializable {

	@Id
	private String id;

	@Column
	private String title;

	@Column
	private int active;

	public CostCenter() {
	}

	public CostCenter(String id, String title, int active) {
		super();
		this.id = id;
		this.title = title;
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
