package br.com.gft.MentorsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Mentor;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.RatePrf;
import br.com.gft.MentorsDAO.CostCenterDAO;
import br.com.gft.MentorsDAO.EmployeeDAO;
import br.com.gft.MentorsDAO.JobDAO;
import br.com.gft.MentorsDAO.RatePrfDAO;

public class EmployeeService {
	
	public void addEmployee(Employee employee){
		new EmployeeDAO().insertEmployee(employee);
	}
	
	public List<Job> getJobs(){
		return new JobDAO().findJobs();
	}
	
	public List<CostCenter> getCostCenters(){
		return new CostCenterDAO().findCostCenters();
	}
	
	public List<RatePrf> getRatePrfs(){
		return new RatePrfDAO().findRatePrfs();
	}
	
	public List<Employee> getPagedEmployees(int inicio, int quantidade){
		return new EmployeeDAO().findPagedEmployees(inicio, quantidade);
	}
	
	public List<Employee> getPagedEmployees(String search, int inicio, int quantidade){
		return new EmployeeDAO().findPagedEmployees(search, inicio, quantidade);
	}
	
	public List<Employee> getPagedEmployeesInactive(int inicio, int quantidade){
		return new EmployeeDAO().findPagedEmployeesInactive(inicio, quantidade);
	}
	
	public List<Employee> getPagedEmployeesInactive(String search, int inicio, int quantidade){
		return new EmployeeDAO().findPagedEmployeesInactive(search, inicio, quantidade);
	}

	public List<Employee> getEmployees(){
		return new EmployeeDAO().findEmployees();
	}
	
	public List<Employee> getEmployees(String search){
		return new EmployeeDAO().findEmployees(search);
	}
	
	public List<Employee> getEmployeesInactive(){
		return new EmployeeDAO().findEmployeesInactive();
	}
	
	public List<Employee> getEmployeesInactive(String search){
		return new EmployeeDAO().findEmployeesInactive(search);
	}
	
	public Employee getEmployee(String employeeId){
		return new EmployeeDAO().findEmployee(employeeId);
	}
	
	public void alterEmployee(Employee employee){
		new EmployeeDAO().updateEmployee(employee);
	}
	
	public List<Mentor> getQtyMentee(){
		return new EmployeeDAO().findQtyMentee();
	}
	
	public List<Mentor> getQtyMentor(){
		return new EmployeeDAO().findQtyMentor();
	}
	
	public List<Employee> getMentees(String mentor){
		return new EmployeeDAO().getMentees(mentor);
	}
	
	public List<Employee> getNotMentees(String mentor, int jobPosition){
		return new EmployeeDAO().getNotMentees(mentor, jobPosition);
	}
	
	/**
	 * this method is to calculate how many years employee is working at GFT 
	 * @param employee
	 * @return
	 */
	public int calcYears(Employee employee){
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		
		String data = year.format(employee.getJoinDate());
		String dataAtual = year.format(Calendar.getInstance().getTime());
		
		int calcYear = Integer.parseInt(data);
		int calcAtual = Integer.parseInt(dataAtual);
		
		calcYear = calcAtual - calcYear;
		
		return calcYear;
	}
	
	/**
	 * this method is to format date
	 * @param joinDate
	 * @return
	 * @throws ParseException
	 */
	public Date formatdate(String joinDate) {
		Date formated = null;
		
		try {
			formated = new SimpleDateFormat("yyyy-MM-dd").parse(joinDate);
		} catch (Exception e) {
			try {
				formated = new SimpleDateFormat("dd/MM/yyyy").parse(joinDate);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		return formated;
	}
	
	/**
	 * this method is to verify if mentee exist
	 * 
	 * @param employees
	 * @param id
	 * @param employeeId
	 * 
	 * @return
	 */
	public int verifyMent(String id , String employeeId){
		int verify = 0;	
		EmployeeService service = new EmployeeService();
		service.getEmployee(id);
		
		
		if (!id.equals(employeeId)) {
			verify = 1;
		}

		return verify;
	}
	
	/**
	 * this method is to verify if exist mentor with the same job of mentee 
	 * 
	 * @return
	 */
	public List<Employee> employeeVerify(){
		List<Employee> employeeError = new ArrayList<Employee>();
		
		for (Employee mentor : new EmployeeService().getEmployees()) {
			if (mentor.getIsMentor() == 1 || mentor.getIsTutor() == 1) {
				for (Employee mentee : new EmployeeService().getEmployees()) {
					if (mentee.getMentorId() != null &&
							mentee.getMentorId().equals(mentor.getId()) && 
							mentee.getJob().getPosition() >= mentor.getJob().getPosition()) {
						employeeError.add(mentee);
					}
				}
			}
			
			System.out.println();
		}
		
		return employeeError;
	}
	
	/**
	 * this method is to verify if exist informations pending on the user registration
	 * 
	 * @return
	 */
	public List<Employee> EmployeePending(){
		List<Employee> employeePending = new ArrayList<Employee>();
		
		for (Employee employee : new EmployeeService().getEmployees()) {
			if(employee.getName() == null ||
					employee.getWorkplace() == null ||
					employee.getExtension() == 0 ||
					employee.getMentorId() == null){ 
//					employee.getTutorId() == null){
				employeePending.add(employee);
			}
		}

		return employeePending;
	}
	
	/**
	 * this method is to get new employees and show at homepage
	 * 
	 * @return
	 */
	public List<Employee> newEmployees(){
		List<Employee> newEmployees = new ArrayList<Employee>();
		
		for(Employee employee : new EmployeeService().getEmployees()){
			Date monthWork = employee.getJoinDate();
			
			SimpleDateFormat year = new SimpleDateFormat("yyyy");
			SimpleDateFormat month = new SimpleDateFormat("MM");
			
			String monthatual = month.format(Calendar.getInstance().getTime());
			String yearatual = year.format(Calendar.getInstance().getTime());

			String monthjoin = month.format(monthWork);
			String yearjoin = year.format(monthWork);
			
			int monthActual = Integer.parseInt(monthatual);
			int monthJoin = Integer.parseInt(monthjoin);
			int yearActual = Integer.parseInt(yearatual);
			int yearJoin = Integer.parseInt(yearjoin);
			
			if(monthJoin >= monthActual - 1 && yearActual == yearJoin){
				newEmployees.add(employee);
			}
		}	
		
		return newEmployees;
	}	
	
	/**
	 * this method is to get the selected Project to report
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public List<Project> getSelectedProjects(String id){
		List<Project> projects = new ArrayList<Project>();
		StringTokenizer st = new StringTokenizer(id, ",");
		
		while (st.hasMoreElements()) {
			projects.add(new ProjectService().getProject(st.nextToken()));			
		}
		
		return projects;
	}
}
