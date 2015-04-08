package br.com.gft.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Mentor;
import br.com.gft.MentorsCommon.MentorHistory;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.RatePrf;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.CostCenterService;
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.MentorsService.JobService;
import br.com.gft.MentorsService.MentorHistoryService;
import br.com.gft.MentorsService.ProjectService;
import br.com.gft.MentorsService.RatePrfService;
import br.com.gft.MentorsService.UnitService;
import br.com.gft.share.Pagination;
import br.com.gft.share.Paths;

/**
 * this Class is to control requests and reponses of employees pages to server
 * 
 * @author mlav
 * 
 */
@Controller
public class EmployeeControl {
	private String index;
	private Date data;

	/**
	 * this method is to show employee page to read employee informations
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Employee", method = RequestMethod.GET)
	public String showEmployee(@RequestParam(value = "page", required = false) Integer page, Model model) {
		EmployeeService service = new EmployeeService();
		Pagination pagination = new Pagination(service.getEmployees().size(), page);
		
		model.addAttribute("url", "Employee");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Employee", service.getPagedEmployees(pagination.getBegin(), pagination.getQuantity()));
		
		
		return "Employee";
	}

	/**
	 * this method is to show get employees including inactive
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/EmployeeInactive", method = RequestMethod.GET)
	public String showEmployeeInactive(@RequestParam(value = "page", required = false) Integer page, Model model) {
		EmployeeService service = new EmployeeService();
		Pagination pagination = new Pagination(service.getEmployeesInactive().size(), page);
		
		
		
		
		model.addAttribute("url", "EmployeeInactive");
		model.addAttribute("pagination", pagination);
		model.addAttribute("Employee", service.getPagedEmployeesInactive(pagination.getBegin(), pagination.getQuantity()));
		return "Employee";

	}

	/**
	 * this method is to show all employee informations
	 * 
	 * @param employeeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/EmployeeView", method = RequestMethod.GET)
	public String EmployeeView(@RequestParam("id") String employeeId,
			Model model) {
		Employee employee = new EmployeeService().getEmployee(employeeId);
		index = employeeId;
		int calcYear = new EmployeeService().calcYears(employee);
		List<Job> jobs = new JobService().getJobs();
		List<RatePrf> rateprfs = new RatePrfService().getRatePrfs();
		List<CostCenter> costs = new CostCenterService().getCostCenters();
		List<Project> project = new ProjectService().getProjects();
		List<Customer> customer = new CustomerService().getCustomers();
		List<Unit> unit = new UnitService().getUnits();
		List<Employee> employeels = new EmployeeService().getEmployees();
		List<MentorHistory> mentorhistory = new MentorHistoryService()
				.getMentorHistorys();
		EmployeeAssignment employeeassignment = new EmployeeAssignment();

		model.addAttribute("yearGFT", calcYear);
		model.addAttribute("Employee", employee);
		model.addAttribute("Employeels", employeels);
		model.addAttribute("jobs", jobs);
		model.addAttribute("costcenters", costs);
		model.addAttribute("rateprfs", rateprfs);
		model.addAttribute("Project", project);
		model.addAttribute("Customer", customer);
		model.addAttribute("Unit", unit);
		model.addAttribute("EmployeeAssignment",
				employee.getEmployeeassignment());
		model.addAttribute("Assignment", employeeassignment);
		model.addAttribute("MentorHistory", mentorhistory);
		return "EmployeeView";

	}

	/**
	 * this method is to show employee registration page
	 * 
	 * @param employee
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/EmployeeRegistration", method = RequestMethod.GET)
	public String showEmployeeRegistration(
			@ModelAttribute("Employee") Employee employee, Model model) {
		EmployeeService employeeservice = new EmployeeService();
		List<Job> job = employeeservice.getJobs();
		List<CostCenter> costcenter = employeeservice.getCostCenters();
		List<RatePrf> rateprf = employeeservice.getRatePrfs();
		model.addAttribute("job", job);
		model.addAttribute("costcenter", costcenter);
		model.addAttribute("rateprf", rateprf);
		return "EmployeeRegistration";

	}

	/**
	 * this method is to register employee on database
	 * 
	 * @param id
	 * @param name
	 * @param sap
	 * @param join_date
	 * @param workplace
	 * @param extension
	 * @param ws_Name
	 * @param jobId
	 * @param ratePrfId
	 * @param costId
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/ProcessEmployeeRegistration", method = RequestMethod.POST)
	public String ProcessEmployeeRegistration(@RequestParam("id") String id,
			@RequestParam("name") String name, @RequestParam("sap") String sap,
			@RequestParam("joinDate") String joinDate,
			@RequestParam("workplace") String workplace,
			@RequestParam("extension") int extension,
			@RequestParam("wsName") String wsName,
			@RequestParam("job") String jobId,
			@RequestParam("ratePrf") int ratePrfId,
			@RequestParam("costCenter") String costId, Model model)
			throws ParseException {
		
		List<Employee> employeeLs = new ArrayList<Employee>();
		employeeLs = new EmployeeService().getEmployees();
		int checker = new EmployeeService().existEmployee(employeeLs , id);
		Employee employee = new Employee();
		
		if(checker == 0){
			Date data = new EmployeeService().formatdate(joinDate);
			
			employee.setId(id);
			employee.setName(name);
			employee.setSap(sap);
			employee.setJoinDate(data);
			employee.setWorkplace(workplace);
			employee.setExtension(extension);
			employee.setWsName(wsName);
			employee.setActive(0);
			Job job = new JobService().getJob(jobId);
			employee.setJob(job);
			RatePrf rateprf = new RatePrfService().getRatePrf(ratePrfId);
			employee.setRate_Prf(rateprf);
			CostCenter cost = new CostCenterService().getCostCenter(costId);
			employee.setCost_Center(cost);
			EmployeeService employeeservice = new EmployeeService();
			employeeservice.addEmployee(employee);	
		}
			showEmployee(null, model);
			model.addAttribute("checker" , checker);
			return "Employee";
	}

	/**
	 * this method is to show page to Update employee Informations
	 * 
	 * @param employeeId
	 * @param jobId
	 * @param costCenterId
	 * @param ratePrfId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/EmployeeUpdate", method = RequestMethod.GET)
	public String EmployeeUpdate(@RequestParam(Paths.ATTRIBUTE_EC_ID) String employeeId,
			@RequestParam(Paths.ATTRIBUTE_EC_JOB) String jobId,
			@RequestParam(Paths.ATTRIBUTE_EC_COST) String costCenterId,
			@RequestParam(Paths.ATTRIBUTE_EC_RATE) int ratePrfId, Model model) {
	
		
		Employee employee = new EmployeeService().getEmployee(employeeId);
		index = employeeId;
		data = employee.getJoinDate();
		Job job = new JobService().getJob(jobId);
		employee.setJob(job);
		List<Job> jobs = new JobService().getJobs();
		RatePrf rateprf = new RatePrfService().getRatePrf(ratePrfId);
		employee.setRate_Prf(rateprf);
		List<RatePrf> rateprfs = new RatePrfService().getRatePrfs();
		CostCenter cost = new CostCenterService().getCostCenter(costCenterId);
		employee.setCost_Center(cost);
		List<Mentor> qtyMentee = new EmployeeService().getQtyMentee();
		List<CostCenter> costs = new CostCenterService().getCostCenters();
		List<Project> project = new ProjectService().getProjects();
		List<Customer> customer = new CustomerService().getCustomers();
		List<Unit> unit = new UnitService().getUnits();
		List<Employee> employeels = new EmployeeService().getEmployees();
		List<MentorHistory> mentorhistory = new MentorHistoryService()
				.getMentorHistorys();
		EmployeeAssignment employeeassignment = new EmployeeAssignment();
		
		model.addAttribute(Paths.ATTRIBUTE_EC_QTY_MENTEE, qtyMentee);
		model.addAttribute(Paths.ATTRIBUTE_EC_EMPLOYEE, employee);
		model.addAttribute(Paths.ATTRIBUTE_EC_EMPLOYEE_LIST, employeels);
		model.addAttribute(Paths.ATTRIBUTE_EC_JOB, job);
		model.addAttribute(Paths.ATTRIBUTE_EC_JOBS, jobs);
		model.addAttribute(Paths.ATTRIBUTE_EC_COST_CENTER, cost);
		model.addAttribute(Paths.ATTRIBUTE_EC_COST_CENTERS, costs);
		model.addAttribute(Paths.ATTRIBUTE_EC_RATE_PRF, rateprf);
		model.addAttribute(Paths.ATTRIBUTE_EC_RATE_PRFS, rateprfs);
		model.addAttribute(Paths.ATTRIBUTE_EC_PROJECT, project);
		model.addAttribute(Paths.ATTRIBUTE_EC_CUSTOMER, customer);
		model.addAttribute(Paths.ATTRIBUTE_EC_UNIT, unit);
		model.addAttribute(Paths.ATTRIBUTE_EC_EMPLOYEE_ASSIGNMENT,
				employee.getEmployeeassignment());
		model.addAttribute(Paths.ATTRIBUTE_EC_ASSIGNMENT, employeeassignment);
		model.addAttribute(Paths.ATTRIBUTE_EC_MENTOR_HISTORY, mentorhistory);

		return "EmployeeUpdate";

	}

	/**
	 * this method is to register the update of employee informations
	 * 
	 * @param id
	 * @param name
	 * @param sap
	 * @param join_date
	 * @param workplace
	 * @param extension
	 * @param ws_Name
	 * @param jobId
	 * @param ratePrfId
	 * @param costId
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/ProcessEmployeeUpdate", method = RequestMethod.POST)
	public String ProcessEmployeeUpdate(@RequestParam("id") String id,
			@RequestParam("name") String name, @RequestParam("sap") String sap,
			@RequestParam("joinDate") String joinDate,
			@RequestParam("workplace") String workplace,
			@RequestParam("extension") int extension,
			@RequestParam("wsName") String wsName,
			@RequestParam("job") String jobId,
			@RequestParam("ratePrf") int ratePrfId,
			@RequestParam("costCenter") String costId, Model model)
			throws ParseException {

		Date data = new EmployeeService().formatdate(joinDate);
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		employee.setSap(sap);
		employee.setJoinDate(data);
		employee.setWorkplace(workplace);
		employee.setExtension(extension);
		employee.setWsName(wsName);
		employee.setActive(0);
		Job job = new JobService().getJob(jobId);
		employee.setJob(job);
		RatePrf rateprf = new RatePrfService().getRatePrf(ratePrfId);
		employee.setRate_Prf(rateprf);
		CostCenter cost = new CostCenterService().getCostCenter(costId);
		employee.setCost_Center(cost);
		EmployeeService employeeservice = new EmployeeService();
		employeeservice.alterEmployee(employee);
		showEmployee(null, model);
		return "Employee";
	}

	/**
	 * this method is to inactive employee
	 * 
	 * @param id
	 * @param jobId
	 * @param ratePrfId
	 * @param costId
	 * @param leaving_date
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/EmployeeInactivate", method = RequestMethod.POST)
	public String EmployeeInactivate(@RequestParam("id") String id,
			@RequestParam("job") String jobId,
			@RequestParam("rate") int ratePrfId,
			@RequestParam("cost") String costId,
			@RequestParam("leaving_date") String leavingDate, Model model)
			throws ParseException {

		Date data = new EmployeeService().formatdate(leavingDate);
		EmployeeService employeeservice = new EmployeeService();
		Employee employee = new Employee();
		employee = employeeservice.getEmployee(id);
		employee.setActive(1);
		employee.setLeavingDate(data);
		Job job = new JobService().getJob(jobId);
		employee.setJob(job);
		RatePrf rateprf = new RatePrfService().getRatePrf(ratePrfId);
		employee.setRate_Prf(rateprf);
		CostCenter cost = new CostCenterService().getCostCenter(costId);
		employee.setCost_Center(cost);
		employeeservice.alterEmployee(employee);
		EmployeeControl employeecontrol = new EmployeeControl();
		employeecontrol.showEmployee(null, model);
		return "Employee";

	}

	/**
	 * this method is to add new mentee to employee
	 * 
	 * @param id
	 * @param employeeId
	 * @param jobId
	 * @param costCenterId
	 * @param ratePrfId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/EmployeeAddMentee", method = RequestMethod.POST)
	public String EmployeeAddMentee(@RequestParam("id") String id,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("job") String jobId,
			@RequestParam("cost") String costCenterId,
			@RequestParam("rate") int ratePrfId, Model model) throws Exception {
		
		// verificando se o mentee exist
		List<Employee> employeeLs = new EmployeeService().getEmployees();

		int verifyMent = new EmployeeService().verifyMent(employeeLs, id , employeeId);
		
		if (verifyMent == 1) {
			
			// Inserindo novo mentee para employee
			Employee employee = new EmployeeService().getEmployee(id);
			String oldMentor = employee.getMentorId();
			employee.setMentorId(employeeId);
			employee.setTutorId(null);
			EmployeeService employeeservice = new EmployeeService();
			employeeservice.alterEmployee(employee);

			// Atualizando finish date employee History
			MentorHistory mentorhistory = new MentorHistory();
			mentorhistory.setEmployee(employee);
			mentorhistory.setMentorId(employeeId);
			Date sysDate = new EmployeeService().getSysdate();
			mentorhistory.setFinishDate(null);
			mentorhistory.setStartDate(sysDate);
			MentorHistoryService mentorhistoryservice = new MentorHistoryService();
			mentorhistoryservice.addFinishDate(id, oldMentor, sysDate);

			// Inserindo novo mentee na History
			mentorhistoryservice.addMentorHistory(mentorhistory);

			// Atualizando Mentor para "is mentor"
			Employee mentor = employeeservice.getEmployee(employeeId);
			mentor.setIsMentor(1);
			employeeservice.alterEmployee(mentor);
		}
		
		// to return to the same employee
		id = employeeId;
		model.addAttribute("verifyMent", verifyMent);
		EmployeeControl empcontrol = new EmployeeControl();
		empcontrol.EmployeeUpdate(id, jobId, costCenterId, ratePrfId, model);
		return "EmployeeUpdate";
	}

	/**
	 * this method is to add Tutor to Employee
	 * 
	 * @param id
	 * @param employeeId
	 * @param jobId
	 * @param costCenterId
	 * @param ratePrfId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/EmployeeAddTutor", method = RequestMethod.POST)
	public String EmployeeAddTutor(@RequestParam("id") String id,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("job") String jobId,
			@RequestParam("cost") String costCenterId,
			@RequestParam("rate") int ratePrfId, Model model) throws Exception {
		
			// verificando se o mentee exist
		List<Employee> employeeLs = new EmployeeService().getEmployees();
		int verifyMent = new EmployeeService().verifyMent(employeeLs, id , employeeId);
		System.out.println("verify : " + verifyMent);
		if (verifyMent == 1) {
			// Inserindo novo mentee para employee
			System.out.println("Index id : " + id);
			Employee employee = new EmployeeService().getEmployee(id);
			String oldTutor = employee.getMentorId();
			employee.setTutorId(employeeId);
			employee.setMentorId(null);
			System.out.println("passou aqui");
			EmployeeService employeeservice = new EmployeeService();
			employeeservice.alterEmployee(employee);

			// Atualizando finish date employee History
			MentorHistory mentorhistory = new MentorHistory();
			mentorhistory.setEmployee(employee);
			mentorhistory.setTutorId(employeeId);
			Date sysDate = new EmployeeService().getSysdate();
			mentorhistory.setFinishDate(null);
			mentorhistory.setStartDate(sysDate);
			MentorHistoryService mentorhistoryservice = new MentorHistoryService();
			mentorhistoryservice.addFinishDate(id, oldTutor, sysDate);

			// Inserindo novo mentee na History
			mentorhistoryservice.addMentorHistory(mentorhistory);

			// Atualizando Mentor para "is tutor"
			Employee tutor = employeeservice.getEmployee(employeeId);
			tutor.setIsTutor(1);
			employeeservice.alterEmployee(tutor);

		}
		// to return to the same employee
		id = employeeId;
		model.addAttribute("verifyMent", verifyMent);
		EmployeeControl empcontrol = new EmployeeControl();
		empcontrol.EmployeeUpdate(id, jobId, costCenterId, ratePrfId, model);

		return "EmployeeUpdate";
	}

}