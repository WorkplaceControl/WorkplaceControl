package br.com.gft.share;

import org.springframework.web.bind.annotation.RequestParam;

public class Paths {
	
	/* MainControl */
	public static final String  ATTRIBUTE_MC_USER = "user";
	public static final String 	ATTRIBUTE_MC_ROLE_USER = "roleUser";
	public static final String 	ATTRIBUTE_MC_QTY_NEWS = "qtyNews";
	public static final String 	ATTRIBUTE_MC_NEW_EMPLOYEE = "newEmployee";
	public static final String 	ATTRIBUTE_MC_QTY_PENDING = "qtyPending";
	public static final String 	ATTRIBUTE_MC_EMPLOYEE_PENDING = "employeePending";
	public static final String 	ATTRIBUTE_MC_QTY_ERRORS = "qtyErrors";
	public static final String 	ATTRIBUTE_MC_EMPLOYEE_ERROR = "employeeError";
	
	/*MentorHistoryControl*/
	public static final String 	ATTRIBUTE_MHC_HIST_ID = "histId";
	public static final String 	ATTRIBUTE_MHC_EMPLOYEE_ID = "employeeId";
	public static final String 	ATTRIBUTE_MHC_START_DATE = "start_date";
	public static final String 	ATTRIBUTE_MHC_FINISH_DATE = "finish_date";
	public static final String 	ATTRIBUTE_MHC_JOB = "job";
	public static final String 	ATTRIBUTE_MHC_COST = "cost";
	public static final String 	ATTRIBUTE_MHC_RATE= "rate";
	
	/*UsersControl*/
		/*ProcessUser*/
	public static final String 	ATTRIBUTE_UC_USERNAME = "username";
	public static final String 	ATTRIBUTE_UC_PASS = "pass";
	public static final String 	ATTRIBUTE_UC_REPASS = "repass";
	public static final String 	ATTRIBUTE_UC_USER_ROLE = "userRole";
	public static final String 	ATTRIBUTE_UC_USERS_CONTROL_MESSAGE = "usersControlMessage";
		
		/*UpdateUser*/
	public static final String ATTRIBUTE_UC_NEW_PASS = "newPass";
	public static final String ATTRIBUTE_UC_REP_PASS = "repPass";
	public static final String ATTRIBUTE_UC_OLD_PASS = "oldPass";
}