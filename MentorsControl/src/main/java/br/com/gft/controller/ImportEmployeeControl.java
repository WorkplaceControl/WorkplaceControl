package br.com.gft.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.RatePrf;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.CostCenterService;
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.EmployeeAssignService;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.MentorsService.JobService;
import br.com.gft.MentorsService.ProjectService;
import br.com.gft.MentorsService.UnitService;
import br.com.gft.logs.SystemLogs;

@Controller
public class ImportEmployeeControl {
	private ExcelContent excelContent = new ExcelContent();

	@RequestMapping(value = "/showImport", method = RequestMethod.GET)
	public String showImport(Model model) {
		return "importExcel";
	}

	@RequestMapping(value = "/InitialImport", method = RequestMethod.GET)
	public String showImportInitial(Model model) {
		return "ImportInitial";
	}

	@RequestMapping(value = "/UploadNewEmployee", method = RequestMethod.POST)
	public String singleSave(@RequestParam("file") MultipartFile file, Model model) throws Exception {
		String fileName = null;
		List<Employee> employees = null;
		BufferedOutputStream buffStream = null;
		
		if (!file.isEmpty()) {
			try {
				fileName = file.getOriginalFilename();
				buffStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
				buffStream.write(file.getBytes());
				buffStream.close();

				employees = showExcelContent(fileName);
				
				model.addAttribute("employee", employees);
				model.addAttribute("viewHab", 1);
			} catch (Exception e) {
				model.addAttribute("viewHab", 1);
			}
		} else {
			return "Unable to upload. File is empty.";
		}
		
		return "importExcel";
	}

	@RequestMapping(value = "/UploadInitial", method = RequestMethod.POST)
	public String singleInitialImport(@RequestParam("file") MultipartFile file, Model model) {
		String fileName = null;
		
		if (!file.isEmpty()) {
			try {
				fileName = file.getOriginalFilename();
				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
				buffStream.write(file.getBytes());
				buffStream.close();

				model.addAttribute("fileName", fileName);
				model.addAttribute("viewHab", 1);
				
				return "ImportInitial";
			} catch (Exception e) {
				return "You failed to upload " + fileName + ": " + e.getMessage();
			}
		} else {
			return "Unable to upload. File is empty.";
		}
	}

	/**
	 * this method is to receive a List of employee and send values to database
	 * this list is create with the sheet data
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping(value = "/processNewEmployee", method = RequestMethod.POST)
	public String proccessImport(Model model) {
		List<Employee> employees = new ArrayList<Employee>();
		EmployeeService employeeService = new EmployeeService();

		for (int i = 0; i < employees.size(); i++) {
			employees.get(i).setActive(0);
			employeeService.addEmployee(employees.get(i));
		}
		
		return "importExcel";
	}

	/**
	 * this method is to receive a List of cost center and send values to
	 * database this list is create with the sheet data
	 * 
	 * @param model
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/processExcelInitial", method = RequestMethod.POST)
	public String proccessExcelinitial(@RequestParam("file") String file, Model model) throws Exception {
		JobService jobService = new JobService();
		UnitService unitService = new UnitService();
		ProjectService projectService = new ProjectService();
		CustomerService customerService = new CustomerService();
		EmployeeService employeeService = new EmployeeService();
		CostCenterService costCenterService = new CostCenterService();
		EmployeeAssignService employeeAssignmentService = new EmployeeAssignService();
		
		List<Job> jobError = new ArrayList<Job>();
		List<Unit> unitError = new ArrayList<Unit>();
		List<Project> projectError = new ArrayList<Project>();
		List<Customer> customerError = new ArrayList<Customer>();
		List<Employee> employeeError = new ArrayList<Employee>();
		List<CostCenter> costCenterError = new ArrayList<CostCenter>();
		List<EmployeeAssignment> employeeAssignmentError = new ArrayList<EmployeeAssignment>();
		
		List<Job> jobWarning = new ArrayList<Job>();
		List<Unit> unitWarning = new ArrayList<Unit>();
		List<Project> projectWarning = new ArrayList<Project>();
		List<Customer> customerWarning = new ArrayList<Customer>();
		List<Employee> employeeWarning = new ArrayList<Employee>();
		List<CostCenter> costCenterWarning = new ArrayList<CostCenter>();
		List<EmployeeAssignment> employeeAssignmentWarning = new ArrayList<EmployeeAssignment>();
		
		/**
		 * this code is to insert cost center into data base validate if it is
		 * not registered before
		 */
		int numCostCenter = 0;
		for (CostCenter costCenter : excelContent.excelCostCenter(file)) {
			if (costCenter.getId().equals("") || costCenter.getTitle().equals("")) {
				costCenterError.add(costCenter);
			} else if (costCenterService.getCostCenter(costCenter.getId()) != null) {
				costCenterWarning.add(costCenter);
			} else {
				costCenter.setActive(0);
				costCenterService.addCostCenter(costCenter);
				numCostCenter++;
			}
		}
		
		/**
		 * this code is to insert Job into database validate if it is not
		 * registered before
		 */
		int numJob = 0;
		for (Job job : excelContent.excelJob(file)) {
			if (job.getId().equals("") || job.getTitle().equals("")) {
				jobError.add(job);
			} else if (jobService.getJob(job.getId()) != null) {
				jobWarning.add(job);
			} else {
				jobService.processJob(job);
				numJob++;
			}
		}

		/**
		 * this code is to insert unit into data base validate if it is not
		 * registered before
		 */
		int numUnit = 0;
		for (Unit unit : excelContent.excelUnit(file)) {
			if (unit.getDescription().equals("")) {
				unitError.add(unit);
			} else if (unitService.getUnit(unit.getId()) != null) {
				unitWarning.add(unit);
			} else {
				unit.setActive(0);
				unitService.addUnit(unit);
				numUnit++;
			}
		}

		/**
		 * this code is to insert customers into data base validate if it is
		 * not registered before
		 */
		int numCustomer = 0;
		for (Customer customer : excelContent.excelCustomer(file)) {
			if (customer.getDescription().equals("")) {
				customerError.add(customer);
			} else if (customerService.getCustomer(customer.getDescription()) != null) {
				customerWarning.add(customer);
			} else {
				customer.setActive(0);
				customerService.addCustomer(customer);
				numCustomer++;
			}
		}

		/**
		 * this code is to insert projects into data base validate if it is not
		 * registered before
		 */
		int numProject = 0;
		for (Project project : excelContent.excelProject(file)) {
			if (project.getDescription().equals("")) {
				projectError.add(project);
			} else if (projectService.getProject(project.getDescription()) != null) {
				projectWarning.add(project);
			} else {
				project.setActive(0);
				projectService.addProject(project);
				numProject++;
			}
		}

		/**
		 * this code is to insert Employee into data base validate if it is not
		 * registered before
		 */
		int numEmployee = 0;
		for (Employee employee : excelContent.excelEmployee(file)) {
			if (employee.getId().equals("")
					|| employee.getSap().equals("")
					|| employee.getJoinDate().equals(null)
					|| employee.getJob().getId().equals(null)
					|| employee.getCostCenter().getId().equals(null) 
					|| employee.getWsName().equals("")) {
				employeeError.add(employee);
			} else if (employeeService.getEmployee(employee.getId()) != null) {
				employeeWarning.add(employee);
			} else {
				employee.setActive(0);
				employeeService.addEmployee(employee);
				numEmployee++;
			}
		}
		
		/**
		 * this code is to insert Employee to project into data base validate
		 * if it is not registered before
		 */
		int numEmployeeAssignment = 0;
		for (EmployeeAssignment employeeAssignment : excelContent.excelEmployeeAssign(file)) {
			
			if (employeeAssignment.getEmployee().getId().equals("") || employeeAssignment.getProject().getId() == 0) {
				employeeAssignmentError.add(employeeAssignment);
			} else if (employeeAssignmentService.getEmployeeAssigns(employeeAssignment.getEmployee().getId()) != null) {
				employeeAssignmentWarning.add(employeeAssignment);
			} else {
				employeeAssignment.setActive(0);
				employeeAssignmentService.addEmployeeAssign(employeeAssignment);
				numEmployeeAssignment++;
			}
		}

		model.addAttribute("numJob", numJob);
		model.addAttribute("numUnit", numUnit);
		model.addAttribute("numProject", numProject);
		model.addAttribute("numCustomer", numCustomer);
		model.addAttribute("numEmployee", numEmployee);
		model.addAttribute("numCostCenter", numCostCenter);
		model.addAttribute("numEmployeeAssignment", numEmployeeAssignment);

		model.addAttribute("jobError", jobError);
		model.addAttribute("unitError", unitError);
		model.addAttribute("projectError", projectError);
		model.addAttribute("employeeError", employeeError);
		model.addAttribute("customerError", customerError);
		model.addAttribute("costCenterError", costCenterError);
		model.addAttribute("employeeAssignmentError", employeeAssignmentError);
		
		model.addAttribute("jobWarning", jobWarning);
		model.addAttribute("unitWarning", unitWarning);
		model.addAttribute("projectWarning", projectWarning);
		model.addAttribute("employeeWarning", employeeWarning);
		model.addAttribute("customerWarning", customerWarning);
		model.addAttribute("costCenterWarning", costCenterWarning);
		model.addAttribute("employeeAssignmentWarning", employeeAssignmentWarning);
		
		if( numJob > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU " + numJob + " Job(s)");
		};
		if( numUnit > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU " + numUnit + " Unit(s)");
		};
		if( numProject > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU " + numProject + " Project(s)");
		};
		if( numCustomer > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU " + numCustomer + " Customer(s)");
		};
		if( numEmployee > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU " + numEmployee + " Employee(s)");
		};
		if( numCostCenter > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU " + numCostCenter + " Cost Center(s)");
		};
		if( numEmployeeAssignment > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU " + numEmployeeAssignment + "Employee(s) Assignment(s)");
		};
		if( jobError.size() > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " OCORRERAM " + jobError.size() + "ERROS ao incluir Job(s)");
		};
		if( unitError.size() > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " OCORRERAM " + unitError.size() + "ERROS ao incluir Unit(s)");
		};
		if( projectError.size() > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " OCORRERAM " + projectError.size() + "ERROS ao incluir Project(s)");
		};
		if( employeeError.size() > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " OCORRERAM " + employeeError.size() + "ERROS ao incluir Job(s)");
		};
		if( customerError.size() > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " OCORRERAM " + customerError.size() + "ERROS ao incluir Customer(s)");
		};
		if( costCenterError.size() > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " OCORRERAM " + jobError.size() + "ERROS ao incluir Cost(s) Center(s)");
		};
		if( employeeAssignmentError.size() > 0){
			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " OCORRERAM " + employeeAssignmentError.size() + "ERROS ao incluir Employee(s) Assignment(s)");
		};
//		if( jobWarning.size() > 0){
//			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Employee (ID): " + jobWarning.size());
//		};
//		if( unitWarning.size() > 0){
//			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Employee (ID): " + unitWarning.size());
//		};
//		if( projectWarning.size() > 0){
//			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Employee (ID): " + projectWarning.size());
//		};
//		if( employeeWarning.size() > 0){
//			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Employee (ID): " + employeeWarning.size());
//		};
//		if( customerWarning.size() > 0){
//			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Employee (ID): " + customerWarning.size());
//		};
//		
//		if( costCenterWarning.size() > 0){
//			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Employee (ID): " + costCenterWarning.size());
//		};
//		if( employeeAssignmentWarning.size() > 0){
//			new SystemLogs((Calendar.getInstance().getTime().toString()) + " --- " + SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase() + " INCLUIU o Employee (ID): " + employeeAssignmentWarning.size());
//		};
		
		return "ResultImportInitial";
	}

	/**
	 * this method id to add projects to data base when user received new
	 * projects from email
	 * 
	 * @param model
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/processexcelProject", method = RequestMethod.POST)
	public String proccessExcelInitial(Model model) throws Exception {
		return "importExcel";
	}

	public List<Employee> showExcelContent(String file) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<Employee> employees = new ArrayList<Employee>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(file)).getSheetAt(0);

		for (Row row : sheet) {
			Job job = new Job();
			RatePrf rateprf = new RatePrf();
			Employee employee = new Employee();
			CostCenter costcenter = new CostCenter();

			String name = "";
			String surname1 = "";
			String surname2 = "";
			
			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if (cellRef.formatAsString().substring(0, 2).equals("$B")) {
						surname1 = cell.getRichStringCellValue().getString();
					}

					if (cellRef.formatAsString().substring(0, 2).equals("$C")) {
						surname2 = cell.getRichStringCellValue().getString();
					}

					if (cellRef.formatAsString().substring(0, 2).equals("$D")) {
						name = cell.getRichStringCellValue().getString();
					}

					if (cellRef.formatAsString().substring(0, 2).equals("$E")) {
						String profile = cell.getRichStringCellValue().getString();
						job.setId(profile);
						employee.setJob(job);
					}

					if (cellRef.formatAsString().substring(0, 2).equals("$G")) {
						String id = cell.getRichStringCellValue().getString();
						employee.setId(id);
					}
					break;

				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell) && cellRef.formatAsString().substring(0, 2).equals("$F")) {
						employee.setJoinDate(cell.getDateCellValue());
					} else {
						if (cellRef.formatAsString().substring(0, 2).equals("$A")) {
							DecimalFormat df = new DecimalFormat("00");
							employee.setSap(df.format(cell.getNumericCellValue()).toString());
						}
						
						if (cellRef.formatAsString().substring(0, 2).equals("$H")) {
							DecimalFormat df = new DecimalFormat("00");
							costcenter.setId(df.format(cell.getNumericCellValue()).toString());
							employee.setCost_Center(costcenter);
						}
					}
					break;
				}
			}
			
			i++;
			
			if (i >= 3) {
				rateprf.setId(1);
				
				employee.setRate_Prf(rateprf);
				employee.setWsName(surname1 + " " + surname2 + " , " + name);
				employee.setActive(0);
				
				employees.add(employee);
			}
		}

		return employees;
	}

	/**
	 * this code will return a list with the project in the sheet
	 */
	public List<EmployeeAssignment> excelEmployeeAssign(String file) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<EmployeeAssignment> employeeAssignments = new ArrayList<EmployeeAssignment>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(file)).getSheetAt(0);

		for (Row row : sheet) {
			int numId = 0;
			String id = null;
			Project project = new Project();
			Employee employee = new Employee();
			EmployeeAssignment empassign = new EmployeeAssignment();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if (cellRef.formatAsString().substring(0, 2).equals("$B")) {
						id = cell.getRichStringCellValue().getString();
					}

					break;

				case Cell.CELL_TYPE_NUMERIC:
					if (cellRef.formatAsString().substring(0, 2).equals("$A")) {
						numId = (int) cell.getNumericCellValue();
					}
				}
			}
			
			i++;

			if (i >= 3) {
				employee.setId(id);
				project.setId(numId);
				empassign.setEmployee(employee);
				empassign.setProject(project);
				employeeAssignments.add(empassign);
			}
		}

		return employeeAssignments;
	}

}