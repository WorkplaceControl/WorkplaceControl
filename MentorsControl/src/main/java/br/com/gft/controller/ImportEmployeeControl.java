package br.com.gft.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



@Controller
public class ImportEmployeeControl {
	List<Employee> employeeLs = null;
	List<CostCenter> costcenters = null;
	List<Job> jobs = null;
	List<Unit> units = null;
	List<Customer> customers = null;
	List<Project> projects = null;
	List<EmployeeAssignment> empassigns = new ArrayList<EmployeeAssignment>();
	List<Employee> employeeerror = new ArrayList<Employee>();
	List<CostCenter> costerror = new ArrayList<CostCenter>();
	List<Job> joberror = new ArrayList<Job>();
	List<Unit> uniterror = new ArrayList<Unit>();
	List<Customer> customererror = new ArrayList<Customer>();
	List<Project> projecterror = new ArrayList<Project>();
	List<Employee> employeerror = new ArrayList<Employee>();
	List<EmployeeAssignment> empassignerror = new ArrayList<EmployeeAssignment>();
	private String fileName = null;
	private String name = "";
	private String surname1 = "";
	private String surname2 = "";
	private String ws_name = null;
	private String sap = "";
	private String id;
	private int numId;
	ExcelContent excelcontent = new ExcelContent();
	
	private static final Logger logger = LoggerFactory
            .getLogger(ImportEmployeeControl.class);
	
	
	
		@RequestMapping(value = "/showImport", method = RequestMethod.GET)
		public String showImport(Model model) {
			
			return "importExcel";
		}
		
		@RequestMapping(value = "/InitialImport", method = RequestMethod.GET)
		public String showImportInitial(Model model) {
			
			return "ImportInitial";
		}
	
	
	    @RequestMapping(value="/UploadNewEmployee", method=RequestMethod.POST )
	    public String singleSave(@RequestParam("file") MultipartFile file , Model model) throws Exception{
	    	employeeLs = new ArrayList<Employee>();
	    	int x = 1;
	    	if (!file.isEmpty()) {
	            try {
	                fileName = file.getOriginalFilename();
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream buffStream = 
	                        new BufferedOutputStream(new FileOutputStream(new File(fileName)));
	                buffStream.write(bytes);
	                buffStream.close();
	                
	                List<Employee> employees = showExcelContent();
	                model.addAttribute("employee" , employees);
	                model.addAttribute("viewHab", x);
	                return "importExcel";
	            } catch (Exception e) {
	                return "You failed to upload " + fileName + ": " + e.getMessage();
	            }
	        } else {
	            return "Unable to upload. File is empty.";
	        }
	    }
	    
	    @RequestMapping(value="/UploadInitial", method=RequestMethod.POST )
	    public String singleInitialImport(@RequestParam("file") MultipartFile file , Model model){
	    	employeeLs = new ArrayList<Employee>();
	    	int x = 1;
	    	if (!file.isEmpty()) {
	            try {
	                fileName = file.getOriginalFilename();
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream buffStream = 
	                        new BufferedOutputStream(new FileOutputStream(new File(fileName)));
	                buffStream.write(bytes);
	                buffStream.close();
	                
	                model.addAttribute("fileName" , fileName + " imported with success");
	                model.addAttribute("viewHab", x);
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
	     * @param model
	     * @return
	     */
	    
	    @RequestMapping(value="/processNewEmployee", method=RequestMethod.POST )
	    public String proccessImport(Model model){
	    	
	    	EmployeeService employeeservice = new EmployeeService();
	    	
	    	for(int i=0 ; i < employeeLs.size() ; i++){
	    		employeeLs.get(i).setActive(0);
	    		employeeservice.addEmployee(employeeLs.get(i));
	    	}
	    	return "importExcel";
	    }
	    
	    /**
	     * this method is to receive a List of cost center and send values to database
	     * this list is create with the sheet data
	     * @param model
	     * @return
	     * @throws Exception 
	     */
	    
	    @RequestMapping(value="/processExcelInitial", method=RequestMethod.POST )
	    public String proccessExcelinitial( Model model) throws Exception{
	    	int index = 0;
	    	CostCenterService costcenterservice = new CostCenterService();
	    	JobService jobservice = new JobService();
	    	UnitService unitservice = new UnitService();
	    	CustomerService customerservice = new CustomerService();
	    	ProjectService projectservice = new ProjectService();
	    	EmployeeService employeeservice = new EmployeeService();
	    	EmployeeAssignService empassignservice = new EmployeeAssignService();
	    	
	    	
	    	
	    	
	    	
	    	
	    	/**
	    	 * this code is to insert cost center into data base
	    	 * validante if it is not regitered before 
	    	 */
	    	int numCost = 1;
	    	costcenters = excelcontent.excelCostCenter(fileName);
	    	for(int i=0 ; i < costcenters.size() ; i++){
	    		costcenters.get(i).setActive(0);
	    		List<CostCenter> costTest = costcenterservice.getCostCenters();
	    		if(costTest.size() == 0){
	    			costcenterservice.addCostCenter(costcenters.get(i));
	    			costTest = costcenterservice.getCostCenters();
	    		}
	    			for(int s=0 ; s<costTest.size() ; s++ ) {
	    				System.out.println("id costs : " + costcenters.get(i).getId() + " = " + " costcenters test : " + costTest.get(s).getId() );
		    			if(costcenters.get(i).getId().equals(costTest.get(s).getId()) ){
		    			}else{	
		    				index++;
		    			}
		    				System.out.println(index + " = " + costTest.size() );
		    				if(index == costTest.size() || costTest.size() == 0){
		    					if(costcenters.get(i).getId().equals("") || costcenters.get(i).getTitle().equals("")){
		    						costerror.add(costcenters.get(i));
		    					}else{
		    						costcenterservice.addCostCenter(costcenters.get(i));
		    						numCost++;
		    					}
		    				}
		    			}
	    			index = 0;
	    	}
	    	
	    	/**
	    	 * this code is to insert Job into database 
	    	 * validante if it is not regitered before 
	    	 */
	    	int numJob = 1;
	    	jobs = excelcontent.excelJob(fileName);
	    	for(int i=0 ; i < jobs.size() ; i++){
	    		jobs.get(i).setActive(0);
	    		List<Job> jobTest = jobservice.getJobsInactive();
	    		if(jobTest.size() == 0){
	    			jobservice.processJob(jobs.get(i));
	    			jobTest = jobservice.getJobsInactive();
	    		}
    			for(int s=0 ; s< jobTest.size() ; s++ ) {
    				System.out.println("id jobs : " + jobs.get(i).getId() + " = " + " Job test : " + jobTest.get(s).getId() );
	    			if(jobs.get(i).getId().equals(jobTest.get(s).getId()) ){
	    			}else{
	    				index++;
	    			}
	    			System.out.println(index + " = " + jobs.size() );
	    			if(index == jobTest.size()){
	    					if(jobs.get(i).getId().equals("") || jobs.get(i).getTitle().equals("")){	
	    						joberror.add(jobs.get(i));
	    					}else{
	    						jobservice.processJob(jobs.get(i));
	    						numJob++;
	    					}
	    			}
    			}
    			index = 0;
	    	}
	    	
	    	/**
	    	 * this code is to insert unit into data base
	    	 * validante if it is not regitered before 
	    	 */
	    	int numUnit = 1;
	    	units = excelcontent.excelUnit(fileName);
	    	for(int i=0 ; i < units.size() ; i++){
	    		
	    		List<Unit> unitTest = unitservice.getUnits();
	    		if(unitTest.size() == 0){
	    			unitservice.addUnit(units.get(i));
	    			unitTest = unitservice.getUnits();
	    		}
	    		units.get(i).setActive(0);
    			for(int s=0 ; s<unitTest.size() ; s++ ) {
    				System.out.println("id units : " + units.get(i).getDescription() + " = " + " unit test : " + unitTest.get(s).getDescription() );
	    			if(units.get(i).getDescription().equals( unitTest.get(s).getDescription() ) ){
	    			}else{
	    				index++;
	    			}
	    			System.out.println(index + " = " + unitTest.size() );
	    			if(index == unitTest.size()){
    					if( units.get(i).getDescription().equals("") ){
    						uniterror.add(units.get(i));
    					}else{
    						unitservice.addUnit(units.get(i));
    						numUnit++;
    					}
	    			}
    			}
    		  index = 0;
	    	}
	    	
    		
    		/**
    	    * this code is to insert customers into data base
    	    * validante if it is not regitered before 
    	    */
	    	int numCustomer = 1;
	    	customers = excelcontent.excelCustomer(fileName);
	    	for(int i=0 ; i < customers.size() ; i++){
	    		
	    		List<Customer> customerTest = customerservice.getCustomers();
	    		if(customerTest.size() == 0){
	    			customerservice.addCustomer(customers.get(i));
	    			customerTest = customerservice.getCustomers();
	    		}
	    		customers.get(i).setActive(0);
    			for(int s=0 ; s<customerTest.size() ; s++ ) {
    				System.out.println("id units : " + customers.get(i).getDescription() + " = " + " unit test : " + customerTest.get(s).getDescription() );
	    			if(customers.get(i).getDescription().equals(customerTest.get(s).getDescription())){	
	    			}else{
	    				index++;
	    			}
	    			System.out.println(index + " = " + customerTest.size() );
	    			if(index == customerTest.size()){
    					if( customers.get(i).getDescription().equals("") ){
    						customererror.add(customers.get(i));
    					}else{
    						customers.get(i).setActive(0);
    						customerservice.addCustomer(customers.get(i));
    						numCustomer++;
    					}
	    			}
    			}
    			index = 0;
	    	}
	    	
	    	/**
	    	 * this code is to insert projects into data base
	    	 * validante if it is not regitered before 
	    	 */
	    	int numProj = 1;
	    	projects = excelcontent.excelProject(fileName);
	    	for(int i=0 ; i < projects.size() ; i++){
	    		List<Project> projectTest = projectservice.getProjects();
	    		projects.get(i).setActive(0);
	    		if(projectTest.size() == 0){
	    			projectservice.addProject(projects.get(i));
	    			projectTest = projectservice.getProjects();
	    		}
    			for(int s=0 ; s<projectTest.size() ; s++ ) {
    				System.out.println("id projects : " + projects.get(i).getDescription() + " = " + " project test : " + projectTest.get(s).getDescription() );
    				if( projects.get(i).getDescription().equals( projectTest.get(s).getDescription() ) ){
	    			}else{
	    				index++;
	    			}
    				System.out.println(index + " = " + projectTest.size() );
	    			if( index == projectTest.size()){
    					if(	projects.get(i).getDescription().equals( "" )	){
    						projecterror.add(projects.get(i));
    					}else{
    						projects.get(i).setActive(0);
    						projectservice.addProject(projects.get(i));
    						numProj++;
    					}
	    			}
    			}
    			index = 0;	
	    	}
	    	
	    	/**
	    	 * this code is to insert Employee into data base
	    	 * validante if it is not regitered before 
	    	 */
	    	int numEmployee = 1;
	    	employeeLs = excelcontent.excelEmployee(fileName);
	    	for(int i=0 ; i < employeeLs.size() ; i++){
	    		employeeLs.get(i).setActive(0);
	    		 
	    		if(employeeLs.get(i).getId().equals("") || 
	    			 employeeLs.get(i).getSap().equals("") ||
	    			 employeeLs.get(i).getJoinDate().equals(null) ||
	    			 employeeLs.get(i).getJob().getId().equals(null) ||
	    			 employeeLs.get(i).getCostCenter().getId().equals(null) ||
	    			 employeeLs.get(i).getWsName().equals("")){
	    			
	    			  employeerror.add(employeeLs.get(i)); 	
	    		  }else{
	    			  employeeservice.addEmployee(employeeLs.get(i));
	    			  numEmployee++;
	    		  }
	    	}
	    	
	    	
	    	/**
	    	 * this code is to insert Employee to project into data base
	    	 * validante if it is not regitered before 
	    	 */
	    	int numEmpAssign = 1;
	    	empassigns = excelcontent.excelEmployeeAssign(fileName);
	    	for(int i=0 ; i < empassigns.size() ; i++){
	    		empassigns.get(i).setActive(0);
	    		
	    		if(empassigns.get(i).getEmployee().getId().equals("") || empassigns.get(i).getProject().getId() == 0){
	    			empassignerror.add(empassigns.get(i));
	    		}else{	
	    			empassignservice.addEmployeeAssign(empassigns.get(i));
	    			numEmpAssign++;
	    		}
	    	}
	    	
	    	
	    	model.addAttribute("numCost" , numCost );
	    	model.addAttribute("numJob" , numJob );
	    	model.addAttribute("numUnit" ,  numUnit);
	    	model.addAttribute("numCustomer" , numCustomer );
	    	model.addAttribute("numProj" , numProj );
	    	model.addAttribute("numEmployee" , numEmployee );
	    	model.addAttribute("numEmpAssign" , numEmpAssign );
	    	model.addAttribute("employeeerror" , employeeerror );
	    	model.addAttribute("costerror" , costerror);
	    	model.addAttribute("joberror" , joberror);
	    	model.addAttribute("uniterror" , uniterror);
	    	model.addAttribute("customererror" , customererror);
	    	model.addAttribute("projecterror" , projecterror);
	    	model.addAttribute("empassignerror" , empassignerror);
	    	
	    	
	    	return "ResultImportInitial";
	    }
	    
	    /**
	     * this method id to add projects to data base when user received new projects from email
	     * @param model
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value="/processexcelProject", method=RequestMethod.POST )
	    public String proccessExcelInitial(Model model) throws Exception{
	    	return "importExcel";
	    }
	    	
	    public List<Employee> showExcelContent() throws IOException, InvalidFormatException, ParseException{
	    	  
	    	  int i = 0;
	    	  String path =  fileName;	
	    	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
	    	  System.out.println("Sucesso !!!");
	    	  Sheet sheet1 = wb.getSheetAt(0);
              
              for (Row row : sheet1) {
            	  Employee employee = new Employee();
            	  CostCenter costcenter = new CostCenter();
            	  Job job = new Job();
            	  
                  for (Cell cell : row) {
                      CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                      System.out.print(cellRef.formatAsString());
                      System.out.print(" - ");
                     

                      switch (cell.getCellType()) {
                          case Cell.CELL_TYPE_STRING:

	                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$B")){   
	                        		    surname1 = cell.getRichStringCellValue().getString();   
	                        	  }
	                        	  
	                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$C")){  	
	                        		    surname2 = cell.getRichStringCellValue().getString();  	
	                        	  }
	                        	  
	                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$D")){
	                        		  	name = cell.getRichStringCellValue().getString();
	                        		    	
	                        	  }
	                        	  
	                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$E")){
	                        		    String profile = cell.getRichStringCellValue().getString();
	                        		    job.setId(profile);
	                        		    employee.setJob(job);
	                        		    
	                        	  }
	                        	  
	                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$G")){
	                        		  	String id = cell.getRichStringCellValue().getString();
	                        		    employee.setId(id);	    	
	                        	  }
                              break;
  
                          case Cell.CELL_TYPE_NUMERIC:
                              if (DateUtil.isCellDateFormatted(cell) && cellRef.formatAsString().substring(0 , 2).equals("$F")) {
                            	      	employee.setJoinDate(cell.getDateCellValue());
                              } else {
                            	  if(cellRef.formatAsString().substring(0 , 2).equals("$A")){
                            		  	double aux = cell.getNumericCellValue();
                            		  	DecimalFormat df = new DecimalFormat("00");
                            		  	sap = df.format(aux).toString();
                            		  	employee.setSap(sap);
                            	  }
                            	  if(cellRef.formatAsString().substring(0 , 2).equals("$H")){
                            		  	double aux = cell.getNumericCellValue();
                            		  	DecimalFormat df = new DecimalFormat("00");
                            		  	costcenter.setId(df.format(aux).toString());
                            		  	employee.setCost_Center(costcenter);
                            		  	System.out.println("cost : " + employee.getCostCenter().getId());
                            	  }
                            	   	  
                              }
                              break;
                         
                      }
                      
                  }
                  i++;
                  ws_name = surname1 + " " + surname2 + " , " + name ;
                  if(i >= 3){
                	  RatePrf rateprf = new RatePrf();
                	  rateprf.setId(1);
                	  employee.setRate_Prf(rateprf);
                	  employee.setWsName(ws_name);
                	  employee.setActive(0);
                      employeeLs.add(employee);
                  }
                  System.out.println("WorkSpace name = " + ws_name);
                  name = "";
              	  surname1 = "";
              	  surname2 = "";
              }
              return employeeLs;

	    }
	    
       

	    
	    
	    /**
         * this code will return a list with the project in the sheet
         */
	    
	    public List<EmployeeAssignment> excelEmployeeAssign() throws IOException, InvalidFormatException, ParseException{
	    	  int numId2 = 0;
	    	  int i = 0;
	    	  String path =  fileName;	
	    	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
	    	  Sheet sheet1 = wb.getSheetAt(0);
            
            for (Row row : sheet1) {
            	Project project = new Project();
            	Employee employee = new Employee();
            	EmployeeAssignment empassign = new EmployeeAssignment(); 

            	
                for (Cell cell : row) {
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    System.out.print(cellRef.formatAsString());
                    System.out.print(" - ");
                   

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
	                        	  
	                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$B")){   
	                        		    id = cell.getRichStringCellValue().getString();   
	                        	  }

                            break;
                            
                        case Cell.CELL_TYPE_NUMERIC:
                               	  if(cellRef.formatAsString().substring(0 , 2).equals("$A")){
                               		  numId = (int) cell.getNumericCellValue() ;	  	
                               	  }
 
                    }
                    
                }
                i++;
           
                if(i >= 3){
                	employee.setId(id);
                	project.setId(numId);
                	empassign.setEmployee(employee);
                	empassign.setProject(project);
                	empassigns.add(empassign);
                }

            }
            return empassigns;

	    }
 
}
	    
	    
	 

