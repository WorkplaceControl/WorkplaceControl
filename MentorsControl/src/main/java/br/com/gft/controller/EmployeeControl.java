package br.com.gft.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.MentorHistory;
import br.com.gft.MentorsCommon.RatePrf;
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
import br.com.gft.share.SystemLogs;

/**
 * this Class is to control requests and reponses of employees pages to server
 * 
 * @author mlav
 * 
 */
@Controller
public class EmployeeControl {

	EmployeeService service = new EmployeeService();

	/**
	 * this method is to show employee page to read employee informations
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Employee", method = RequestMethod.GET)
	public String showEmployee(@RequestParam(value = "page", required = false) Integer page, 
							   @RequestParam(value = "s", required = false) String search, Model model) {
		
		Pagination pagination = null;

		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getEmployees(search).size(), page);
			model.addAttribute("Employee", service.getPagedEmployees(search, pagination.getBegin(), pagination.getQuantity()));
		}else {
			pagination = new Pagination(service.getEmployees().size(), page);
			model.addAttribute("Employee", service.getPagedEmployees(pagination.getBegin(), pagination.getQuantity()));
		}
		
		model.addAttribute("url", "Employee");
		model.addAttribute("pagination", pagination);

		return "Employee";
	}

	/**
	 * this method is to show get employees including inactive
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping(value = "/EmployeeInactive", method = RequestMethod.GET)
	public String showEmployeeInactive(@RequestParam(value = "page", required = false) Integer page,
									   @RequestParam(value = "s", required = false) String search, Model model) {
		
		Pagination pagination = null;

		if (search != null && !search.equals("")) {
			pagination = new Pagination(service.getEmployeesInactive(search).size(), page);
			model.addAttribute("Employee", service.getPagedEmployeesInactive(search, pagination.getBegin(), pagination.getQuantity()));
		}else {
			pagination = new Pagination(service.getEmployees().size(), page);
			model.addAttribute("Employee", service.getPagedEmployeesInactive(pagination.getBegin(), pagination.getQuantity()));
		}

		model.addAttribute("url", "EmployeeInactive");
		model.addAttribute("pagination", pagination);

		return "Employee";
	}

	/**
	 * this method is to show all employee informations
	 * 
	 * @param employeeId
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping(value = "/EmployeeView", method = RequestMethod.GET)
	public String EmployeeView(@RequestParam("id") String employeeId, Model model) {

		Employee employee = new EmployeeService().getEmployee(employeeId);

		if (employee.getMentorId() != null) {
			model.addAttribute("mentor", new EmployeeService().getEmployee(employee.getMentorId()));
		} else {
			model.addAttribute("mentor", null);
		}
		
		model.addAttribute("yearGFT", new EmployeeService().calcYears(employee));
		model.addAttribute("Employee", employee);
		model.addAttribute("Employeels", new EmployeeService().getEmployees());
		model.addAttribute("jobs", new JobService().getJobs());
		model.addAttribute("costcenters", new CostCenterService().getCostCenters());
		model.addAttribute("rateprfs", new RatePrfService().getRatePrfs());
		model.addAttribute("Project", new ProjectService().getProjects());
		model.addAttribute("Customer", new CustomerService().getCustomers());
		model.addAttribute("Unit", new UnitService().getUnits());
		model.addAttribute("EmployeeAssignment", employee.getEmployeeassignment());
		model.addAttribute("Assignment", new EmployeeAssignment());
		model.addAttribute("MentorHistory", new MentorHistoryService().getMentorHistorys());

		return "EmployeeView";
	}

	/**
	 * this method is to show employee registration page
	 * 
	 * @param employee
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping(value = "/EmployeeRegistration", method = RequestMethod.GET)
	public String showEmployeeRegistration(@ModelAttribute("Employee") Employee employee, Model model) {
		model.addAttribute("job", service.getJobs());
		model.addAttribute("costcenter", service.getCostCenters());
		model.addAttribute("rateprf", service.getRatePrfs());

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
	 * 
	 * @return
	 * 
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

		List<Employee> employeeLs = new EmployeeService().getEmployees();
		Employee employee = new Employee();

		boolean checker = service.existEmployee(employeeLs, id);

		if (checker) {
			Date data = new EmployeeService().formatdate(joinDate);

			employee.setId(id);
			employee.setName(name);
			employee.setSap(sap);
			employee.setJoinDate(data);
			employee.setWorkplace(workplace);
			employee.setExtension(extension);
			employee.setWsName(wsName);
			employee.setActive(0);
			employee.setJob(new JobService().getJob(jobId));
			employee.setRate_Prf(new RatePrfService().getRatePrf(ratePrfId));
			employee.setCost_Center(new CostCenterService().getCostCenter(costId));

			service.addEmployee(employee);

			SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication()
							.getName().toUpperCase()
					+ " ADDED the Employee (ID): " + id);
		}

		showEmployee(null, null, model);

		model.addAttribute("checker", checker ? 1 : 0);

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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/EmployeeUpdate", method = RequestMethod.GET)
	public String EmployeeUpdate(
			@RequestParam(Paths.ATTRIBUTE_EC_ID) String employeeId,
			@RequestParam(Paths.ATTRIBUTE_EC_JOB) String jobId,
			@RequestParam(Paths.ATTRIBUTE_EC_COST) String costCenterId,
			@RequestParam(Paths.ATTRIBUTE_EC_RATE) int ratePrfId, Model model)
			throws Exception {

		EmployeeService employeeService = new EmployeeService();
		JobService jobService = new JobService();
		RatePrfService ratePrfService = new RatePrfService();
		CostCenterService costCenterService = new CostCenterService();

		Employee employee = employeeService.getEmployee(employeeId);

		Job job = jobService.getJob(jobId);
		RatePrf rateprf = ratePrfService.getRatePrf(ratePrfId);
		CostCenter cost = costCenterService.getCostCenter(costCenterId);

		employee.setJob(job);
		employee.setRate_Prf(rateprf);
		employee.setCost_Center(cost);

		model.addAttribute(Paths.ATTRIBUTE_EC_JOB, job);
		model.addAttribute(Paths.ATTRIBUTE_EC_COST_CENTER, cost);
		model.addAttribute(Paths.ATTRIBUTE_EC_RATE_PRF, rateprf);

		if (employee.getMentorId() != null) {
			model.addAttribute("mentor", new EmployeeService().getEmployee(employee.getMentorId()));
		} else {
			model.addAttribute("mentor", null);
		}
		
		model.addAttribute("qtyMentor", employeeService.getQtyMentor());
		model.addAttribute(Paths.ATTRIBUTE_EC_QTY_MENTEE, employeeService.getQtyMentee());
		model.addAttribute(Paths.ATTRIBUTE_EC_EMPLOYEE, employee);
		model.addAttribute(Paths.ATTRIBUTE_EC_EMPLOYEE_LIST, employeeService.getMentees(employeeId));
		model.addAttribute("notMentees", employeeService.getNotMentees(employeeId, job.getPosition()));
		model.addAttribute(Paths.ATTRIBUTE_EC_JOBS, jobService.getJobs());
		model.addAttribute(Paths.ATTRIBUTE_EC_COST_CENTERS, costCenterService.getCostCenters());
		model.addAttribute(Paths.ATTRIBUTE_EC_RATE_PRFS, ratePrfService.getRatePrfs());
		model.addAttribute(Paths.ATTRIBUTE_EC_PROJECT, new ProjectService().getProjects());
		model.addAttribute(Paths.ATTRIBUTE_EC_CUSTOMER, new CustomerService().getCustomers());
		model.addAttribute(Paths.ATTRIBUTE_EC_UNIT, new UnitService().getUnits());
		model.addAttribute(Paths.ATTRIBUTE_EC_EMPLOYEE_ASSIGNMENT, employee.getEmployeeassignment());
		model.addAttribute(Paths.ATTRIBUTE_EC_ASSIGNMENT, new EmployeeAssignment());
		model.addAttribute(Paths.ATTRIBUTE_EC_MENTOR_HISTORY, new MentorHistoryService().getMentorHistorys());

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
	 * 
	 * @return
	 * @throws Exception
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
			@RequestParam("costCenter") String costId,
			@RequestParam(value = "mentorId", required = false) String mentor,
			Model model) throws Exception {

		Employee employee = new Employee();
		EmployeeService service = new EmployeeService();

		employee.setId(id);
		employee.setName(name);
		employee.setSap(sap);
		employee.setJoinDate(new EmployeeService().formatdate(joinDate));
		employee.setWorkplace(workplace);
		employee.setExtension(extension);
		employee.setWsName(wsName);
		employee.setActive(0);
		employee.setJob(new JobService().getJob(jobId));
		employee.setRate_Prf(new RatePrfService().getRatePrf(ratePrfId));
		employee.setCost_Center(new CostCenterService().getCostCenter(costId));

		if (service.getEmployee(id).getMentorId() == null || (mentor != null && !mentor.equals("") && !service.getEmployee(id).getMentorId().equals(mentor))) {
			EmployeeAddMentee(id, mentor, jobId, costId, ratePrfId, model);
		}

		if (mentor != null && mentor.equals(id)) {
			model.addAttribute("verifyMent", 0);
			EmployeeUpdate(id, jobId, costId, ratePrfId, model);

			return "EmployeeUpdate";
		} else {
			employee.setMentorId(mentor);

			service.alterEmployee(employee);

			showEmployee(null, null, model);

			SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase()
					+ " ALTER the Employee (ID): " + id);
			
			return "Employee";
		}
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
	 * 
	 * @return
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/EmployeeStatus", method = RequestMethod.POST)
	public String EmployeeInactivate(@RequestParam("id") String id,
			@RequestParam("job") String jobId,
			@RequestParam("rate") int ratePrfId,
			@RequestParam("cost") String costId,
			@RequestParam("leaving_date") String leavingDate, Model model)
			throws ParseException {
		Employee employee = service.getEmployee(id);

		employee.setActive(1);
		employee.setLeavingDate(service.formatdate(leavingDate));
		employee.setJob(new JobService().getJob(jobId));
		employee.setRate_Prf(new RatePrfService().getRatePrf(ratePrfId));
		employee.setCost_Center(new CostCenterService().getCostCenter(costId));
		
		service.alterEmployee(employee);
		
		SystemLogs.writeLogs(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " DISABLE"
				+ " the Employee (ID): " + id.toUpperCase() + ", Leave Date: "
				+ leavingDate.toUpperCase());
		
		model.addAttribute("ControlMessages", 7);
		
		showEmployee(null, null, model);

		return "Employee";
	}

	/**
	 * this method is to add new mentee to employee
	 * 
	 * @param employeeId
	 * @param mentorId
	 * @param jobId
	 * @param costCenterId
	 * @param ratePrfId
	 * @param model
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/EmployeeAddMentee", method = RequestMethod.POST)
	public String EmployeeAddMentee(@RequestParam("id") String employeeId,
			@RequestParam("employeeId") String mentorId,
			@RequestParam("job") String jobId,
			@RequestParam("cost") String costCenterId,
			@RequestParam("rate") int ratePrfId, Model model) throws Exception {

		EmployeeService service = new EmployeeService();
		Employee employee = service.getEmployee(employeeId);
		Employee mentor = service.getEmployee(mentorId);

		if ((employee.getMentorId() == null || !employee.getMentorId().equals(mentorId)) && 
				(mentor != null && employee.getJob().getPosition() < mentor.getJob().getPosition())) {

			int verifyMent = service.verifyMent(employeeId, mentorId);

			if (verifyMent == 1) {
				// Inserindo novo mentee para employee
				Date sysDate = Calendar.getInstance().getTime();
				String oldMentor = employee.getMentorId();

				MentorHistory mentorhistory = new MentorHistory();
				MentorHistoryService mentorhistoryservice = new MentorHistoryService();

				employee.setMentorId(mentorId);
				employee.setTutorId(null);
				service.alterEmployee(employee);

				// Atualizando finish date employee History
				mentorhistory.setEmployee(employee);
				mentorhistory.setMentorId(mentorId);
				mentorhistory.setFinishDate(null);
				mentorhistory.setStartDate(sysDate);
				mentorhistoryservice.addFinishDate(employeeId, oldMentor, sysDate);

				// Inserindo novo mentee na History
				mentorhistoryservice.addMentorHistory(mentorhistory);

				// Atualizando Mentor para "is mentor"
				if (mentor != null){
					mentor.setIsMentor(1);
					service.alterEmployee(mentor);
				}
			}
			// to return to the same employee
			model.addAttribute("verifyMent", verifyMent);

		} else {
			// to return to the same employee
			model.addAttribute("verifyMent", 2);
		}
		
		employeeId = mentorId;
		
		EmployeeUpdate(employeeId, jobId, costCenterId, ratePrfId, model);
		
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
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/EmployeeAddTutor", method = RequestMethod.POST)
	public String EmployeeAddTutor(@RequestParam("id") String id,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("job") String jobId,
			@RequestParam("cost") String costCenterId,
			@RequestParam("rate") int ratePrfId, 
			Model model) throws Exception {

		Date sysDate = Calendar.getInstance().getTime();

		MentorHistoryService mentorhistoryservice = new MentorHistoryService();

		// verificando se o mentee exist
		int verifyMent = service.verifyMent(id, employeeId);

		if (verifyMent == 1) {
			MentorHistory mentorhistory = new MentorHistory();

			// Inserindo novo mentee para employee
			Employee employee = service.getEmployee(id);
			String oldTutor = employee.getMentorId();

			employee.setTutorId(employeeId);
			employee.setMentorId(null);
			service.alterEmployee(employee);

			// Atualizando finish date employee History
			mentorhistory.setEmployee(employee);
			mentorhistory.setTutorId(employeeId);
			mentorhistory.setFinishDate(null);
			mentorhistory.setStartDate(sysDate);
			mentorhistoryservice.addFinishDate(id, oldTutor, sysDate);

			// Inserindo novo mentee na History
			mentorhistoryservice.addMentorHistory(mentorhistory);

			// Atualizando Mentor para "is tutor"
			Employee tutor = service.getEmployee(employeeId);
			tutor.setIsTutor(1);
			service.alterEmployee(tutor);
		}

		model.addAttribute("verifyMent", verifyMent);

		EmployeeUpdate(employeeId, jobId, costCenterId, ratePrfId, model);

		return "EmployeeUpdate";
	}

}
