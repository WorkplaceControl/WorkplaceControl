package br.com.gft.MentorsCommon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class Users implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

	@ManyToOne
	@JoinColumn(name = "user_role", referencedColumnName = "role_id")
	private UserRole userRole;

	@Column(name = "enable")
	private int enable;
	
	public Users() {

	}

	public Users(int id, String username, String password, UserRole userRole, int enable) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
		this.enable = enable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

}