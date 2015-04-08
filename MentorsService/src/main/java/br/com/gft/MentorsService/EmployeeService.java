package br.com.gft.MentorsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
		EmployeeDAO.setup();
		EmployeeDAO employeedao = new EmployeeDAO();
		employeedao.insertEmployee(employee);
	}
	
	public List<Job> getJobs(){
		JobDAO.setup();
		JobDAO jobdao = new JobDAO();
		List<Job> jobs = jobdao.findJobs();
		return jobs;
	}
	
	public List<CostCenter> getCostCenters(){
		CostCenterDAO.setup();
		CostCenterDAO costcenterdao = new CostCenterDAO();
		List<CostCenter> costcenters = costcenterdao.findCostCenters();
		return costcenters;
	}
	
	public List<RatePrf> getRatePrfs(){
		RatePrfDAO.setup();
		RatePrfDAO rateprfdao = new RatePrfDAO();
		List<RatePrf> rateprf = rateprfdao.findRatePrfs();
		return rateprf;
	}
	
	public List<Employee> getPagedEmployees(int inicio, int quantidade){
		EmployeeDAO.setup();
		EmployeeDAO employeedao = new EmployeeDAO();
		List<Employee> employee = employeedao.findPagedEmployees(inicio, quantidade);
		return employee;
	}
	
	public List<Employee> getPagedEmployeesInactive(int inicio, int quantidade){
		EmployeeDAO.setup();
		EmployeeDAO employeedao = new EmployeeDAO();
		List<Employee> employee = employeedao.findPagedEmployeesInactive(inicio, quantidade);
		return employee;
	}
	
	public List<Employee> getEmployees(){
		EmployeeDAO.setup();
		EmployeeDAO employeedao = new EmployeeDAO();
		List<Employee> employee = employeedao.findEmployees();
		return employee;
	}
	
	public List<Mentor> getQtyMentee(){
		EmployeeDAO.setup();
		EmployeeDAO employeedao = new EmployeeDAO();
		List<Mentor> mentor = employeedao.findQtyMentee();
		return mentor;
	}
	
	public List<Employee> getEmployeesInactive(){
		EmployeeDAO.setup();
		EmployeeDAO employeedao = new EmployeeDAO();
		List<Employee> employee = employeedao.findEmployeesInactive();
		return employee;
	}
	
	public Employee getEmployee(String employeeId){
		EmployeeDAO.setup();
		return new EmployeeDAO().findEmployee(employeeId);
	}
	
	public void alterEmployee(Employee employee){
		EmployeeDAO.setup();
		EmployeeDAO employeedao = new EmployeeDAO();
		employeedao.updateEmployee(employee);
	}
	
	/**
	 * this method is to calculate how many years employee is working at GFT 
	 * @param employee
	 * @return
	 */
	public int calcYears(Employee employee){
		Date yearWork = employee.getJoinDate();
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		String data = year.format(yearWork);
		String dataatual = year.format(Calendar.getInstance().getTime());
		int calcYear = Integer.parseInt(data);
		int calatual = Integer.parseInt(dataatual);
		calcYear = calatual - calcYear;
		return calcYear;
	}
	
	/**
	 * this method is to format date
	 * @param joinDate
	 * @return
	 * @throws ParseException
	 */
	public Date formatdate(String joinDate) throws ParseException{
		String formato = "yyyy-MM-dd";  
		Date data = new SimpleDateFormat(formato).parse(joinDate); 
		return data;
	}
	
	/**
	 * this method is to verify if mentee exist
	 * @param employeeLs
	 * @param id
	 * @param employeeId
	 * @return
	 */
	public int verifyMent(List<Employee> employeeLs , String id , String employeeId){
		 int verify = 0;
		 int count = 0;
		 for(int i=0 ; i<employeeLs.size() ; i++ ){
		        if(employeeLs.get(i).getId().equals(id)){
		        	count = 2;
		        }	
		 }
		 if( count == 2 && !id.equals(employeeId)) {
			 verify = 1;
		 }
		 
		return verify;
		
	}
	
	/**
	 * this method is to get system date 
	 * @return
	 */
	public Date getSysdate(){
		Calendar calendar = new GregorianCalendar();  
        Date date = new Date();  
        calendar.setTime(date);  
        Date sysDate = calendar.getTime();
		return sysDate;
		
	}
	
	/**
	 * this method is to verify if exist mentor with the same job of mentee 
	 * @return
	 */
	public List<Employee> employeeVerify(){
		List<Employee> employeeLsa = new EmployeeService().getEmployees();
		List<Employee> employeeLsb = new EmployeeService().getEmployees();
		List<Employee> employeeError = new ArrayList<Employee>();
		
		for(int i=0 ; i< employeeLsa.size() ; i++ ){
			if(employeeLsa.get(i).getIsMentor() == 1 || employeeLsa.get(i).getIsTutor() == 1){
				for(int s=0 ; s<employeeLsb.size(); s++ ){
					if(employeeLsa.get(i).getJob().getPosition() <= employeeLsb.get(s).getJob().getPosition() && employeeLsa.get(i).getId() == employeeLsb.get(s).getId()){
						employeeError.add(employeeLsb.get(s));
						System.out.println(employeeLsa.get(i).getId() + " -- " + employeeLsb.get(s).getId());
					}
				}
			}
		}
		
		return employeeError;
	}
	
	/**
	 * this method is to verify if exist informations pending on the user registration
	 * @return
	 */
	public List<Employee> EmployeePending(){
		List<Employee> employeeLs = new EmployeeService().getEmployees();;
		List<Employee> employeePending = new ArrayList<Employee>();
			for(int i=0 ; i< employeeLs.size() ; i++ ){
				if( employeeLs.get(i).getName() == null ||
					employeeLs.get(i).getWorkplace() == null ||
					employeeLs.get(i).getExtension() == 0 ||
					employeeLs.get(i).getMentorId() == null &&  employeeLs.get(i).getTutorId() == null){
						employeePending.add(employeeLs.get(i));
				}
			}
		
		return employeePending;
		
	}
	
	/**
	 * this method is to get new employees and show at homepage
	 * @return
	 */
	public List<Employee> newEmployees(){
		List<Employee> employeeLs = new EmployeeService().getEmployees();;
		List<Employee> newEmployees = new ArrayList<Employee>();
		
		
		for(int i=0 ; i< employeeLs.size() ; i++ ){
			Date monthWork = employeeLs.get(i).getJoinDate();
			SimpleDateFormat year = new SimpleDateFormat("yyyy");
			SimpleDateFormat month = new SimpleDateFormat("MM");
			String monthatual = month.format(Calendar.getInstance().getTime());
			String monthjoin = month.format(monthWork);
			String yearatual = month.format(Calendar.getInstance().getTime());
			String yearjoin = month.format(monthWork);
			int monthActual = Integer.parseInt(monthatual);
			int monthJoin = Integer.parseInt(monthjoin);
			int yearActual = Integer.parseInt(yearatual);
			int yearJoin = Integer.parseInt(yearjoin);
			if(monthJoin >= monthActual - 1 && yearActual == yearJoin){
				newEmployees.add(employeeLs.get(i));
			}
		}	
		return newEmployees;
		
	}	
	
	/**
	 * this method is to get the selected Project to report
	 * @param id
	 * @return
	 */
	public List<Project> getSelectedProjects(String id){
		List<Project> projects = new ArrayList<Project>();
		ProjectService projectservice = new ProjectService();
		int aux = 0;
		String str = "";
		StringTokenizer st = new StringTokenizer(id , ",");
		while(st.hasMoreElements()){
			Project project = new Project();
			str = st.nextToken(); 
			aux = Integer.parseInt(str);
			project = projectservice.getProject(aux);
			projects.add(project);			
		}
		return projects;
		
	}
	
	/**
	 * this method is used to check when registering a new employee if the employee isn`t registered before
	 * @param employeeLs
	 * @param id
	 * @return
	 */
	public int existEmployee(List<Employee> employeeLs , String id ){
		int checker = 0;		
		for(int i=0 ; i<employeeLs.size() ; i++ ) {
			if(employeeLs.get(i).getId().equals(id)){
				checker = 1;
			}
		}
		return checker;
	}
}
