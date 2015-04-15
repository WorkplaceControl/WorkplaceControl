package br.com.gft.MentorsCommon;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "MENTOR_HISTORY")
public class MentorHistory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "id")
	private Employee employee;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "finish_date")
	@Temporal(TemporalType.DATE)
	private Date finishDate;
	
	@Column(name = "job_id")
	private int jobId;
	
	@Column(name = "mentor_id")
	private String mentorId;
	
	@Column(name = "tutor_id")
	private String tutorId;

	public MentorHistory() {
	}

	public MentorHistory(int id, Employee employee, Date startDate,
			Date finishDate, int job_id, String mentor_id, String tutor_id) {
		super();
		this.id = id;
		this.employee = employee;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.jobId = jobId;
		this.mentorId = mentor_id;
		this.tutorId = tutor_id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJob_id(int jobId) {
		this.jobId = jobId;
	}

	public String getMentorId() {
		return mentorId;
	}

	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	public String getTutorId() {
		return tutorId;
	}

	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
	}

}
