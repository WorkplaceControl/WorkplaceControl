package br.com.gft.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import br.com.gft.MentorsCommon.CostCenter;
import br.com.gft.MentorsCommon.Customer;
import br.com.gft.MentorsCommon.Employee;
import br.com.gft.MentorsCommon.EmployeeAssignment;
import br.com.gft.MentorsCommon.Job;
import br.com.gft.MentorsCommon.Project;
import br.com.gft.MentorsCommon.RatePrf;
import br.com.gft.MentorsCommon.Unit;
import br.com.gft.MentorsService.CustomerService;
import br.com.gft.MentorsService.EmployeeService;
import br.com.gft.MentorsService.ProjectService;
import br.com.gft.MentorsService.RatePrfService;
import br.com.gft.MentorsService.UnitService;

public class ExcelContent {

	/**
	 * this code will return a list with the costCenter in the sheet
	 */
	public List<CostCenter> excelCostCenter(String fileName) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<CostCenter> costCenters = new ArrayList<CostCenter>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(fileName)).getSheetAt(0);

		for (Row row : sheet) {
			String id = "";
			String title = "";
			CostCenter costCenter = new CostCenter();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (cellRef.formatAsString().substring(0, 2).equals("$L")) {
							title = cell.getRichStringCellValue().getString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$K")) {
							id = cell.getRichStringCellValue().getString();
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (cellRef.formatAsString().substring(0, 2).equals("$K")) {
							id = new DecimalFormat("00").format(cell.getNumericCellValue()).toString();
						}
						break;
				}
			}
			
			i++;

			if (i >= 2) {
				costCenter.setId(id);
				costCenter.setTitle(title);
				costCenter.setActive(0);
				
				costCenters.add(costCenter);
			}
		}

		return costCenters;
	}

	/**
	 * this code will return a list with the Job in the sheet
	 */
	public List<Job> excelJob(String fileName) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<Job> jobs = new ArrayList<Job>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(fileName)).getSheetAt(0);

		for (Row row : sheet) {
			String id = "";
			String title = "";
			Job job = new Job();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				if (cell.getCellType() == Cell.CELL_TYPE_STRING && cellRef.formatAsString().substring(0, 2).equals("$F")) {
					id = cell.getRichStringCellValue().getString();
				}
			}
			
			i++;

			if (i >= 2 && !id.equals("")) {
				StringTokenizer st = new StringTokenizer(id);

				id = st.nextToken();
				
				if (st.hasMoreElements()) {
					//String traco = st.nextToken();
				}
				
				while (st.hasMoreElements()) {
					title = title + " " + st.nextElement();
				}

				if (title.equals("")) {
					title = id;
				}
				
				job.setId(id);
				job.setTitle(title);
				job.setActive(0);
				job.setPosition(1);
				
				jobs.add(job);
			}
		}

		return jobs;
	}

	/**
	 * this code will return a list with the Unit in the sheet
	 */
	public List<Unit> excelUnit(String fileName) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<Unit> units = new ArrayList<Unit>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(fileName)).getSheetAt(0);

		for (Row row : sheet) {
			String description = "";
			Unit unit = new Unit();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				if (cell.getCellType() == Cell.CELL_TYPE_STRING && cellRef.formatAsString().substring(0, 2).equals("$V")) {
					description = cell.getRichStringCellValue().getString();
				}
			}
			
			i++;

			if (i >= 3) {
				unit.setDescription(description);
				unit.setActive(0);
				
				units.add(unit);
			}
			
			description = "";
		}
		
		return units;
	}

	/**
	 * this code will return a list with the Customer in the sheet
	 */
	public List<Customer> excelCustomer(String fileName) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<Customer> customers = new ArrayList<Customer>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(fileName)).getSheetAt(0);

		for (Row row : sheet) {
			String unitDesc = "";
			String description = "";
			Unit unit = new Unit();
			Customer customer = new Customer();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cellRef.formatAsString().substring(0, 2).equals("$U")) {
						description = cell.getRichStringCellValue().getString();
					}
					
					if (cellRef.formatAsString().substring(0, 2).equals("$V")) {
						unitDesc = cell.getRichStringCellValue().getString();
					}
				}
			}
			
			i++;

			if (i >= 2) {
				for (Unit item : new UnitService().getUnits()) {
					if (unitDesc.equals(item.getDescription())) {
						unit = item;
					}
				}
				
				customer.setUnit(unit);
				customer.setDescription(description);
				
				customers.add(customer);
			}
		}
		
		return customers;
	}

	/**
	 * this code will return a list with the project in the sheet
	 */
	public List<Project> excelProject(String fileName) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<Project> projects = new ArrayList<Project>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(fileName)).getSheetAt(0);

		for (Row row : sheet) {
			String cusDesc = "";
			String description = "";
			Project project = new Project();
			Customer customer = new Customer();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cellRef.formatAsString().substring(0, 2).equals("$T")) {
						description = cell.getRichStringCellValue().getString();
					}
					
					if (cellRef.formatAsString().substring(0, 2).equals("$U")) {
						cusDesc = cell.getRichStringCellValue().getString();
					}
				}
			}
			
			i++;

			if (i >= 2) {
				for (Customer item : new CustomerService().getCustomers()) {
					if (cusDesc.equals(item.getDescription())) {
						customer = item;
					}
				}
				
				project.setCustomer(customer);
				project.setDescription(description);
				
				projects.add(project);
			}
		}
		
		return projects;
	}

	/**
	 * this code will return a list with the Employee in the sheet
	 */
	public List<Employee> excelEmployee(String fileName) throws IOException,InvalidFormatException, ParseException {
		List<Employee> employees = new ArrayList<Employee>();
		Sheet sheet = WorkbookFactory.create(new FileInputStream(fileName)).getSheetAt(0);

		for (Row row : sheet) {
			int i = 0;
			int ramal = 0;
			String id = "";
			String name = "";
			String ws_name = "";
			String sap = "";
			String costId = "";
			String rateDesc = "";
			String workplace = "";
			String extension = "";
			
			Job job = new Job();
			RatePrf rateprf = new RatePrf();
			Employee employee = new Employee();
			CostCenter costCenter = new CostCenter();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (cellRef.formatAsString().substring(0, 2).equals("$A")) {
							name = cell.getRichStringCellValue().getString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$C")) {
							ws_name = cell.getRichStringCellValue().getString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$D")) {
							id = cell.getRichStringCellValue().getString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$F")) {
							String profile = cell.getRichStringCellValue().getString();
							StringTokenizer st = new StringTokenizer(profile);
							job.setId(st.nextToken());
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$G")) {
							rateDesc = cell.getRichStringCellValue().getString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$R")) {
							workplace = cell.getRichStringCellValue().getString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$T")) {
							
						}
						break;

					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell) && cellRef.formatAsString().substring(0, 2).equals("$E")) {
							employee.setJoinDate(cell.getDateCellValue());
						}
						
						if (DateUtil.isCellDateFormatted(cell) && cellRef.formatAsString().substring(0, 2).equals("$Q")) {
							employee.setLeavingDate(cell.getDateCellValue());
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$S")) {
							extension = new DecimalFormat("00").format(cell.getNumericCellValue()).toString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$B")) {
							sap = new DecimalFormat("00").format(cell.getNumericCellValue()).toString();
						}
	
						if (cellRef.formatAsString().substring(0, 2).equals("$K")) {
							costId = new DecimalFormat("00").format(cell.getNumericCellValue()).toString();
						}
						break;
				}
			}

			i++;
			
			if (i >= 2) {
				
				for (RatePrf item : new RatePrfService().getRatePrfs()) {
					if (rateDesc.equals(item.getTitle())) {
						rateprf = item;
					}
				}
				
				if (rateprf.getId() == 0) {
					rateprf.setId(1);
				}

				ramal = Integer.parseInt(extension);
				
				costCenter.setId(costId);
				
				employee.setName(name);
				employee.setSap(sap);
				employee.setWsName(ws_name);
				employee.setActive(0);
				employee.setId(id);
				employee.setJob(job);
				employee.setRate_Prf(rateprf);
				employee.setCost_Center(costCenter);
				employee.setWorkplace(workplace);
				employee.setExtension(ramal);

				employees.add(employee);
			}
		}
		
		return employees;
	}

	/**
	 * this code will return a list with the project in the sheet
	 */
	public List<EmployeeAssignment> excelEmployeeAssign(String fileName) throws IOException, InvalidFormatException, ParseException {
		int i = 0;
		List<EmployeeAssignment> employeeAssignments = new ArrayList<EmployeeAssignment>();
		Sheet sheet1 = WorkbookFactory.create(new FileInputStream(fileName)).getSheetAt(0);

		for (Row row : sheet1) {
			String empId = "";
			String projDesc = "";
			Project project = new Project();
			Employee employee = new Employee();
			EmployeeAssignment empassign = new EmployeeAssignment();

			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cellRef.formatAsString().substring(0, 2).equals("$D")) {
						empId = cell.getRichStringCellValue().getString();
					}

					if (cellRef.formatAsString().substring(0, 2).equals("$T")) {
						projDesc = cell.getRichStringCellValue().getString();
					}
				}
			}
			
			i++;

			if (i >= 2) {
				for (Project item : new ProjectService().getProjectsInactive()) {
					if (projDesc.equals(item.getDescription())) {
						project = item;
					}
				}

				for (Employee item : new EmployeeService().getEmployeesInactive()) {
					if (empId.equals(item.getId())) {
						employee = item;
					}
				}
				
				empassign.setActive(0);
				empassign.setEmployee(employee);
				empassign.setProject(project);
				
				employeeAssignments.add(empassign);
			}
		}
		
		return employeeAssignments;
	}

}