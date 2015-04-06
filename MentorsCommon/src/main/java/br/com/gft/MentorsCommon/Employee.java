package br.com.gft.MentorsCommon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Employee implements Serializable{

	@Id
	private String id;	
	@Column
	private String name;
	@Column
	private String sap;
	@Column(name="join_date")
	@Temporal(TemporalType.DATE)
	private Date joinDate;
	@Column(name="leaving_date")
	@Temporal(TemporalType.DATE)
	private Date leavingDate;
	@Column
	private String workplace;

	@Column
	private int extension;
	
	@ManyToOne
	@JoinColumn(name="job_id" , referencedColumnName="id")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="rate_Prf_id" , referencedColumnName="id")
	private RatePrf ratePrf;
	
	@ManyToOne
	@JoinColumn(name="cost_Center_id" , referencedColumnName="id")
	private CostCenter costCenter;
	
	
	@OneToMany(mappedBy = "employee", fetch=FetchType.EAGER)
	private List<EmployeeAssignment> employeeassignment = new ArrayList<EmployeeAssignment>();
	
	@Column(name="ws_name")
	private String wsName;
	@Column(name="is_mentor")
	private int isMentor;
	@Column(name="istutor")
	private int isTutor;
	@Column(name="mentor_id")
	private String mentorId;
	@Column(name="tutor_id")
	private String tutorId;
	@Column
	private int active;
	@Transient
	private int qtyMentee;
	
	public Employee() {
	}
	
	public Employee(String id, String name, String sap, Date joinDate,
			Date leavingDate, String workplace, int extension,
			String ws_Name, int isMentor, int isTutor, String mentorId,
			String tutorId) {
		super();
		this.id = id;
		this.name = name;
		this.sap = sap;
		this.joinDate = joinDate;
		this.leavingDate = leavingDate;
		this.workplace = workplace;
		this.extension = extension;
		this.wsName = ws_Name;
		this.isMentor = isMentor;
		this.isTutor = isTutor;
		this.mentorId = mentorId;
		this.tutorId = tutorId;
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

	public String getSap() {
		return sap;
	}

	public void setSap(String sap) {
		this.sap = sap;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public int getExtension() {
		return extension;
	}

	public void setExtension(int extension) {
		this.extension = extension;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public RatePrf getRatePrf() {
		return ratePrf;
	}

	public void setRate_Prf(RatePrf ratePrf) {
		this.ratePrf = ratePrf;
	}


	public List<EmployeeAssignment> getEmployeeassignment() {
		return employeeassignment;
	}

	public void setEmployeeassignment(List<EmployeeAssignment> employeeassignment) {
		this.employeeassignment = employeeassignment;
	}

	public String getWsName() {
		return wsName;
	}

	public void setWsName(String wsName) {
		this.wsName = wsName;
	}

	public int getIsMentor() {
		return isMentor;
	}

	public void setIsMentor(int isMentor) {
		this.isMentor = isMentor;
	}

	public int getIsTutor() {
		return isTutor;
	}

	public void setIsTutor(int isTutor) {
		this.isTutor = isTutor;
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


	public CostCenter getCostCenter() {
		return costCenter;
	}

	public void setCost_Center(CostCenter costCenter) {
		this.costCenter = costCenter;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getQtyMentee() {
		return qtyMentee;
	}

	public void setQtyMentee(int qtyMentee) {
		this.qtyMentee = qtyMentee;
	}
	
	
	

}
