package br.com.gft.MentorsCommon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class UserRole implements Serializable {

//	@Id
	private int id;

//	@Column(name = "role_user")
//	private String userRole;

	public UserRole() {

	}

	public UserRole(int id, String userRole) {
		super();
		this.id = id;
//		this.userRole = userRole;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public String getUserRole() {
//		return userRole;
//	}
//
//	public void setUserRole(String userRole) {
//		this.userRole = userRole;
//	}

}