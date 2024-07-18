package com.aashdit.prod.cmc.utils;

import java.util.HashMap;
import java.util.Map;

public class ApplicationConstants 
{
	public static final String CONST_FILEUPLOAD_PREFIX = "Upload-Doc";

	public static final int FINYEAR_STARTDAY = 1;
	public static final int FINYEAR_STARTMONTH = 4;
//	
	public static final int ATTENDANCE_PROCESS_START_DATE = 01;
	
//	public static final int ATTENDANCE_PROCESS_END_DATE = 20;
//	public static final int PAYROLL_PROCESS_START_DATE = 21;
//	public static final int PAYROLL_PROCESS_END_DATE = 20;
	
	public static final int NO_OF_PAY_YEARS_BEFORE_CURRENT_YEAR = 10;
	
	public static final String COLLEGE = "COLLEGE";
	public static final String COLLEGE_GOV_TYPE="GOV";
	public static final String COLLEGE_NONGOV_TYPE="NON-GOV";
	public static final String DEPARTMENT = "DEPARTMENT";
	
	public static final String LEAVE_MODULE_CODE = "LEAVE-APPLY";
	public static final String WFLOW_INITIAL_STATUS = "PENDING";
	public static final String WFLOW_FINAL_STATUS = "APPROVED";
	public static final String WFLOW_FINAL_STATUS_REJECT = "REJECTED";
	public static final String WFLOW_INITIAL_SENT_STATUS = "SENT_FOR_VERIFICATION";
	public static final String TRANSFER_MODULE_CODE = "TRANSFER";
	public static final String TOUR_MODULE_CODE = "TOUR-REQUEST";
	public static final String SUSPENSION_MODULE_CODE= "SUSPENSION";
	public static final String REVOKE_MODULE_CODE= "REVOKE";
	
	public static final String PROMOTION_MODULE_CODE = "PROMOTION";
	public static final String DEMOTION_MODULE_CODE = "DEMOTION";
	public static final String WFLOW_INPROGRESS_STATUS = "INPROGRESS";

	
	public static final String PAYROLL_PROCESS_STATUS_DRAFT = "DRAFT";
	public static final String PAYROLL_PROCESS_STATUS_FINAL = "FINALIZED";
	public static final String PAYROLL_PROCESS_STATUS_ACTRNSFRED = "AC-TRANSFERRED";
	
	// Below variables are used to send "CURRENT" or "PREVIOUS" type in fetching last drawn salary -----
	public static final String CONST_PAYHEAD_MONTHTYPE_CURRENT = "CURRENT";
	public static final String CONST_PAYHEAD_MONTHTYPE_PREVIOUS = "PREVIOUS";

	// Below variables are used to send "PAYSLIP" or "PAYHEAD" type in fetching mapped payheads to a staff -----
	public static final String CONST_PAYHEAD_FETCHTYPE_PAYSLIP = "PAYSLIP";
	public static final String CONST_PAYHEAD_FETCHTYPE_PAYHEAD = "PAYHEAD";

	// Below variables are used for the BASIC, GP & DA payhead comparison texts -----
	public static final String CONST_PR_PAYHEAD_BASIC = "BASIC";
	public static final String CONST_PR_PAYHEAD_GP = "GP";
	public static final String CONST_PR_PAYHEAD_DA = "DA";
	
	// Below variables are used for Professional Tax calculation -----
	public static final Double CONST_PRTAX_0TAX_LIMIT = 13304.0;
	public static final Double CONST_PRTAX_125TAX_LIMIT = 25000.0;
	

	
	// ####################################################################################################################
	public static final String Par_doc = "PARDOC";
	public static final String StudentCode = "STUD";
	private static final Map<String, String> staticMap = new HashMap<>();
	static {
		staticMap.put("AADH", "AADHAR");
		staticMap.put("PROF", "PROFILE_PICTURE");
		staticMap.put("DISABLED", "DISABLED_CERTIFICATE");
		staticMap.put("DOC", "DOCUMENTS");
		staticMap.put("ENROLLDOC", "ENROLLMENTDOCUMENTS");
		staticMap.put("LEAVEDOC", "LEAVE_DOCUMENTS");
		
	}

	public static String getDOCFileValue(String key) {
		return staticMap.getOrDefault(key, null);
	}

	public static final long FILE_SIZE = (2 * 1024 * 1024);
	
	public static final String CONST_TYPE_EARNING = "EARNING";
	public static final String CONST_TYPE_DEDUCTION = "DEDUCTION";
	public static final String CONST_TYPE_LNADVANCE = "LNADVANCE";
	
	public static final String DRAFT = "DRAFT";
	public static final String FINALIZED = "FINALIZED";

	public static final String REG_DEPUT = "REG/DEPUT";
	public static final String REG = "REG";
	public static final String DEPUT = "DEPUT";
	public static final String CONT = "CONT";

	public static final String ONSERVICE = "ONSERVICE";
	public static final String SUSPENDED = "SUSPENDED";

	public static final String TYPE_REGULAR = "REGULAR";
	public static final String TYPE_ARREAR = "ARREAR";

	public static final String SUBJECT = "SUBJECT";
	public static final String PAPER = "PAPER";
	public static final String UNIT = "UNIT";
	public static final String CHAPTER = "CHAPTER";
	public static final String SYLLABUS = "SYLLABUS";
	public static final String PROGRAM = "PROGRAM";
	public static final String BATCH = "BATCH";
	public static final String SEMESTER = "SEMESTER";
	public static final String STREAM = "STREAM";

	public static final String REMUN = "REMUN";
	public static final String BASIC = "BASIC";
	public static final String PENDING = "PENDING";

	public static final String WFLOW_WDR_STATUS = "WITHDRAWN";
	public static final String FULL_WDR_STATUS = "FULL_WDR";
	public static final String HALF_WDR_STATUS = "HALF_WDR";
	
	public static final String USER_LEVEL_COLLEGE = "COLG";
	public static final String USER_LEVEL_HEAD_DEPARTMENT = "DEPT";
	public static final String USER_LEVEL_UNIVERSITY = "UNV";

	public static final String USER_TYPE_ADMIN = "ADMIN";

	public static final String USER_TYPE_STAFF = "STAFF";
	// Below variables used for staff type 
	public static final String STUDENT = "STUDENT";
	public static final String ALL = "ALL";
	public static final String TEACHING = "TEACHING";
	public static final String NON_TEACHING = "NON-TEACHING";

	public static final String ONE_TIME = "ONE_TIME";
	
	public static final Map<String, String> CONST_TRANSFER_TYPE_MAP = new HashMap<>();

	static {
		CONST_TRANSFER_TYPE_MAP.put("STM", "Stream/Department Transfer");
	}

	public static String containsValue(String key) {
		return CONST_TRANSFER_TYPE_MAP.getOrDefault(key, "N/A");
	}

	public static enum StudentApplicationStatus {
		APPLIED, APPROVED, REJECTED, REVERTED
	}

	public static enum LoanAndAdvanceStatus {
		APPLIED, APPROVED,CANCELED, REJECTED, CLOSED
	}
	public static enum TimeTableStatus {
		DRAFT, PUBLISHED, CANCELLED,PENDING
	}
	public static ApplicationConstants.TimeTableStatus getStatusEnum(String status) {
		try {
			if(status!=null && !status.isEmpty()){
			return ApplicationConstants.TimeTableStatus.valueOf(status);
		}else{
			return TimeTableStatus.DRAFT;
		}
		} catch (IllegalArgumentException e) {
			// Enum constant not present for the given status
			return TimeTableStatus.DRAFT;
		}
	}
	public static final  String TTCONFIGCODE="TT-CONFIG-";

}
