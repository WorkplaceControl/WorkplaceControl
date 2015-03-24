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
import org.apache.poi.ss.usermodel.Workbook;
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
	List<Employee> employeeLs = new ArrayList<Employee>();
	List<CostCenter> costcenters = new ArrayList<CostCenter>();
	List<Job> jobs = new ArrayList<Job>();
	List<Unit> units = new ArrayList<Unit>();
	List<Customer> customers = new ArrayList<Customer>();
	List<Project> projects = new ArrayList<Project>();
	List<EmployeeAssignment> empassigns = new ArrayList<EmployeeAssignment>();
	List<RatePrf> rates = new ArrayList<RatePrf>(); 
	private String fileName = null;
	private String name = "";
	private String surname1 = "";
	private String surname2 = "";
	private String ws_name = null;
	private String sap = "";
	private String title = "";
	private String id = "";
	private String description = "";
	private int numId = 0;	
	
	
	
	/**
     * this code will return a list with the costcenter in the sheet
     */
	 public List<CostCenter> excelCostCenter(String fileName) throws IOException, InvalidFormatException, ParseException{
   	  
   	  int i = 0;
   	  String path = fileName;	
   	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
   	  Sheet sheet1 = wb.getSheetAt(0);
   	  
   	  
       for (Row row : sheet1) {
    	   CostCenter costcenter = new CostCenter();
    	   
           for (Cell cell : row) {
               CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
               System.out.print(cellRef.formatAsString());
               System.out.print(" - ");
              
               switch (cell.getCellType()) {
                   case Cell.CELL_TYPE_STRING:

                       	  if(cellRef.formatAsString().substring(0 , 2).equals("$L")){   
                       		    title = cell.getRichStringCellValue().getString();

                       	  }
                       	  
                       	  if(cellRef.formatAsString().substring(0 , 2).equals("$K")){   
                       		 	id = cell.getRichStringCellValue().getString();

                    	  }

                       break;
                   case Cell.CELL_TYPE_NUMERIC:
	                	   
                	   if(cellRef.formatAsString().substring(0 , 2).equals("$K")){
	               		  	double aux = cell.getNumericCellValue();
	               		  	DecimalFormat df = new DecimalFormat("00");
	               		  	id  = df.format(aux).toString();

	                	  }
               }
               
           }
           i++;
      
           if(i >= 2){

         	  costcenter.setId(id);
         	  costcenter.setTitle(title);
         	  System.out.println("title = " + costcenter.getTitle());
         	  System.out.println("id = " + costcenter.getId());
         	  costcenter.setActive(0);
         	  costcenters.add(costcenter);
           }
           	  title="";
           	  id="";
       }
       return costcenters;

   }
   
    /**
     * this code will return a list with the Job in the sheet
     */
    
    public List<Job> excelJob(String fileName) throws IOException, InvalidFormatException, ParseException{
    	  title = "";
    	  int i = 0;
    	  String path = fileName;	
    	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
    	  Sheet sheet1 = wb.getSheetAt(0);
        
        for (Row row : sheet1) {
        	Job job = new Job();

            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print(cellRef.formatAsString());
                System.out.print(" - ");
               

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$F")){   
                        		    id = cell.getRichStringCellValue().getString();   
                        	  }
                        	  
                        break;
                   
                }
                
            }
            i++;
       
            if(i >= 2 && !id.equals(null)){
            	StringTokenizer st = new StringTokenizer(id);
            	System.out.println(" ");
        		System.out.println("======= tokenizer =========== ");
        		System.out.println(" ");
            	
	        		id = st.nextToken();
	        	if(st.hasMoreElements()){
	            	String traco = st.nextToken();
	            	System.out.println("id : " + id);
	        		System.out.println("traco : " + traco);
            	}
            	while(st.hasMoreElements()) {
            		title = title + " " + st.nextElement();
            		System.out.println("title : " + title);
            		
            	}
            		
            	if(title.equals("")){
        			title = id;
        		}
            	job.setId(id);
            	job.setTitle(title);
            	job.setActive(0);
            	job.setPosition(1);
            	jobs.add(job);
            	title = "";
            }
            	id = "";
        }
        return jobs;

    }
    
    /**
     * this code will return a list with the Unit in the sheet
     */
    
    public List<Unit> excelUnit( String fileName ) throws IOException, InvalidFormatException, ParseException{
    	  
    	  int i = 0;
    	  String path =  fileName;	
    	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
    	  Sheet sheet1 = wb.getSheetAt(0);
        
        for (Row row : sheet1) {
        	Unit unit = new Unit();

            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print(cellRef.formatAsString());
                System.out.print(" - ");
               
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        	  
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$V")){   
                        		    description = cell.getRichStringCellValue().getString();   
                        	  }

                        break;
                }                
            }
            i++;
       
            if(i >= 3){

            	unit.setDescription( description );
            	unit.setActive( 0 );
            	units.add( unit );
            }
            	description = "";
        }
        return units;
    }
    
	
    
    /**
     * this code will return a list with the Customer in the sheet
     */
    
    public List<Customer> excelCustomer( String fileName ) throws IOException, InvalidFormatException, ParseException{
    	  int i = 0;
    	  String unitDesc = "";
    	  String path =  fileName;	
    	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
    	  Sheet sheet1 = wb.getSheetAt(0);
        
        for (Row row : sheet1) {
        	Unit unit = new Unit();
        	Customer customer = new Customer();

            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print(cellRef.formatAsString());
                System.out.print(" - ");
               
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        	  
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$U")){   
                        		    description = cell.getRichStringCellValue().getString();   
                        	  }
                        	  
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$V")){
                          		    unitDesc = cell.getRichStringCellValue().getString() ;	  	
                          	  }
                        	  
                        break;
           	  
                }  
            }
            i++;
       
            if(i >= 2){
            	units = new UnitService().getUnits();
            		for(int s=0 ; s<units.size() ; s++ ){
            			if(unitDesc.equals(units.get(s).getDescription())){
            				unit = units.get(s);
            			}
            		}
            	customer.setUnit(unit);	
            	customer.setDescription(description);
            	customers.add(customer);
            }
            	description = "";
            	unit = null;
        }
        return customers;
    }
    
    /**
     * this code will return a list with the project in the sheet
     */
    
    public List<Project> excelProject( String fileName ) throws IOException, InvalidFormatException, ParseException{
          String cusDesc = "" ;
    	  int i = 0;
    	  String path =   fileName;	
    	  Workbook wb = WorkbookFactory.create(new FileInputStream(path));
    	  Sheet sheet1 = wb.getSheetAt(0);
        
        for (Row row : sheet1) {
        	Project project = new Project();
        	Customer customer = new Customer();
        	
            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print(cellRef.formatAsString());
                System.out.print(" - ");
               
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        	  
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$T")){   
                        		    description = cell.getRichStringCellValue().getString();   
                        	  }
                        	  
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$U")){   
                      		    cusDesc = cell.getRichStringCellValue().getString();   
                      	  }
                        break;   
                } 
            }
            i++;
       
            if(i >= 2){
            	customers = new CustomerService().getCustomers();
        		for(int s=0 ; s<customers.size() ; s++ ){
        			if(cusDesc.equals(customers.get(s).getDescription())){
        				customer = customers.get(s);
        			}
        		}
        		project.setCustomer(customer);
            	project.setDescription(description);
            	projects.add(project);
            }
            	description = "";
            	customer = null;
        }
        return projects;
    }
	
	/**
     * this code will return a list with the Employee in the sheet
     */
	public List<Employee> excelEmployee( String fileName ) throws IOException, InvalidFormatException, ParseException{
  
  	  String projDesc = "";
  	  String jobId;
  	  String costId = "";
  	  String rateDesc = "";
  	  String mentor;
  	  String workplace = "";
  	  String extension = "";
  	  int ramal = 0;
  	  CostCenter cost = new CostCenter();
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
                    	  if(cellRef.formatAsString().substring(0 , 2).equals("$A")){   
                  		        name = cell.getRichStringCellValue().getString();   
                  	  	  }
                    	
                      	  if(cellRef.formatAsString().substring(0 , 2).equals("$C")){   
                      		    ws_name = cell.getRichStringCellValue().getString();   
                      	  }
                      	  
                      	  if(cellRef.formatAsString().substring(0 , 2).equals("$D")){  	
                      		    id = cell.getRichStringCellValue().getString();  	
                      	  }

                      	  if(cellRef.formatAsString().substring(0 , 2).equals("$F")){
                      		    String profile = cell.getRichStringCellValue().getString();
                      		    StringTokenizer st = new StringTokenizer(profile);
                      		    job.setId(st.nextToken());
                      		    System.out.println("Job Id : " + job.getId());
                      	  }
                      	  
                      	  if(cellRef.formatAsString().substring(0 , 2).equals("$G")){
                      		  	rateDesc = cell.getRichStringCellValue().getString();  	
                      	  }
                      	
	                      
	                      if(cellRef.formatAsString().substring(0 , 2).equals("$R")){
	                  		  	workplace = cell.getRichStringCellValue().getString();  	
	                  	  }
	                      
//	                      if(cellRef.formatAsString().substring(0 , 2).equals("$S")){
//	                  		  	extension = cell.getRichStringCellValue().getString();
//	                  		  
//	                  		  	
//	                  	  }
	                      
	                      if(cellRef.formatAsString().substring(0 , 2).equals("$T")){
	                    	  	projDesc = cell.getRichStringCellValue().getString();  	
	                  	  }
	                      
                      	  
                        break;

                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell) && cellRef.formatAsString().substring(0 , 2).equals("$E")) {
                      	      	employee.setJoinDate(cell.getDateCellValue());
                      	 
                        } 
                        if (DateUtil.isCellDateFormatted(cell) && cellRef.formatAsString().substring(0 , 2).equals("$Q")) {
                	      	employee.setLeavingDate(cell.getDateCellValue());
                        }
                      	
                        if(cellRef.formatAsString().substring(0 , 2).equals("$S")){
                		  	double aux = cell.getNumericCellValue();
                		  	DecimalFormat df = new DecimalFormat("00");
                		  	extension = df.format(aux).toString();
                			System.out.println("Extension excel: " + extension);
                	  }
                        
                        if(cellRef.formatAsString().substring(0 , 2).equals("$B")){
                		  	double aux = cell.getNumericCellValue();
                		  	DecimalFormat df = new DecimalFormat("00");
                		  	sap = df.format(aux).toString();
                			System.out.println("Extension excel: " + extension);
                	  }
                        
                        if(cellRef.formatAsString().substring(0 , 2).equals("$K")){
                		  	double aux = cell.getNumericCellValue();
                		  	DecimalFormat df = new DecimalFormat("00");
                		  	costId = df.format(aux).toString();
                			System.out.println("Extension excel: " + extension);
                	  }
                        break;   	  
                 }
                        
                   
            }
                
            
            i++;
            if(i >= 2){
	          	RatePrf rateprf = new RatePrf();
	          	rates = new RatePrfService().getRatePrfs();
	    		for(int s=0 ; s<rates.size() ; s++ ){
	    			if( rateDesc.equals(rates.get(s).getTitle()) ){
	    				rateprf = rates.get(s);
	    			}
	    		}
	    		
	    		if(rateprf.getId() == 0 ){
	    			rateprf.setId(1);
	    		}
	    		
	
	      		employee.setName(name);
	      		employee.setSap(sap);      		
	          	employee.setWsName(ws_name);
	          	employee.setActive(0);
	          	employee.setId(id);
	          	employee.setJob(job);
	          	employee.setRate_Prf(rateprf);
	          	cost.setId(costId);
	          	employee.setCost_Center(cost);
	          	employee.setWorkplace(workplace);
	          	ramal = Integer.parseInt(extension);
	          	employee.setExtension(ramal);
	          	
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
    
    public List<EmployeeAssignment> excelEmployeeAssign( String fileName ) throws IOException, InvalidFormatException, ParseException{
    	  String projDesc = "";
    	  String empId = "";
    	  int i = 0;
    	  String path = fileName;	
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
                        	  
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$D")){   
                        		    empId = cell.getRichStringCellValue().getString();   
                        	  }
                        	  
                        	  if(cellRef.formatAsString().substring(0 , 2).equals("$T")){   
                      		        projDesc = cell.getRichStringCellValue().getString();   
                      	  }
                        break;   
                } 
            }
            i++;
       
            	
            if(i >=2){

            	    projects = new ProjectService().getProjectsInactive();
            	      	for(int s=0 ; s<projects.size() ; s++ ){
            	      		if(projDesc.equals(projects.get(s).getDescription())){
            	      				project = projects.get(s);
            	      		}
            	      	}
            	      	
            	      	
	            	employeeLs = new EmployeeService().getEmployeesInactive();
	        		for(int s=0 ; s<employeeLs.size() ; s++ ){
	        			if(empId.equals(employeeLs.get(s).getId())){
	        				employee = employeeLs.get(s);
	        			}
	        		}
	        		empassign.setActive(0);
	        		empassign.setEmployee(employee);
	        		empassign.setProject(project);
	        		empassigns.add(empassign);
            }
        }
        return empassigns;
    }
  
    
}
