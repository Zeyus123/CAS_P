/**
 * 
 */
package com.aashdit.prod.cmc.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Tapan K.
 * @version 1.0
 * @since 05-04-2017
 */

public class ApplicationDateUtils
{
	static SimpleDateFormat sdfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");

	static int finYrStartDay = ApplicationConstants.FINYEAR_STARTDAY;
	static int finYrStartMon = ApplicationConstants.FINYEAR_STARTMONTH;
	static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

	

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 05-04-2017
	 * @return today's DATE (e.g. 9 as 09)
	 */
	public static String getStringTodayDateAsDD()
	{
		Calendar calendarObj = Calendar.getInstance();
		String strTodayDateAsDD = "";
		int todayDate = calendarObj.get(Calendar.DATE);
		if(todayDate < 10)
			strTodayDateAsDD = "0"+todayDate;
		else
			strTodayDateAsDD = ""+todayDate;

		return strTodayDateAsDD;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 05-04-2017
	 * @param 
	 * @return today's month as MM (e.g. Feb as 02)
	 * @description 
	 */
	public static String getStringTodayMonthAsMM()
	{
		Calendar calendarObj = Calendar.getInstance();
		String strTodayMonthAsMM = "";
		int todayMonth = calendarObj.get(Calendar.MONTH)+1;
		if(todayMonth < 10)
			strTodayMonthAsMM = "0"+todayMonth;
		else
			strTodayMonthAsMM = ""+todayMonth;

		return strTodayMonthAsMM;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 05-04-2017
	 * @return today's Year as YYYY (2016)
	 */
	public static String getStringTodayYearAsYYYY()
	{
		Calendar calendarObj = Calendar.getInstance();
		String strTodayYearAsYYYY = "";
		strTodayYearAsYYYY = String.valueOf(calendarObj.get(Calendar.YEAR));
		return strTodayYearAsYYYY;
	}

	/**
	* @author Tapan K.
	* @since  05-04-2017
	* @return today as DD/MM/YYYY
	*/
	public static String getStringTodayAsDDMMYYYY()
	{
		Calendar calendarObj = Calendar.getInstance();
		String strTodayAsDDMMYYYY = "";
		calendarObj.clear(Calendar.HOUR); 
		calendarObj.clear(Calendar.MINUTE); 
		calendarObj.clear(Calendar.SECOND);
		Date todayDate = calendarObj.getTime();
		strTodayAsDDMMYYYY = sdfDDMMYYYY.format(todayDate);
		return strTodayAsDDMMYYYY;
	}

	/**
	* @author Tapan K.
	* @since 05-04-2017
	* @return today as DD/MM/YY
	*/
	public static String getStringTodayAsDDMMYY()
	{
		String strTodayAsDDMMYY = "";

		Calendar calendarObj = Calendar.getInstance();
		String strTodayAsDDMMYYYY = "";
		calendarObj.clear(Calendar.HOUR); 
		calendarObj.clear(Calendar.MINUTE); 
		calendarObj.clear(Calendar.SECOND);
		Date todayDate = calendarObj.getTime();
		strTodayAsDDMMYYYY = sdfDDMMYYYY.format(todayDate);
		strTodayAsDDMMYY = strTodayAsDDMMYYYY.substring(0,6) + strTodayAsDDMMYYYY.substring(8);
	
		return strTodayAsDDMMYY;
	}

	/**
	 * @author Tapan K.
	 * @since 05-04-2017
	 * @return current time as hh:mi am (12 Hr Format)
	 */
	public static String getStringNowAsHrMiAm()
	{
		Calendar ct = new GregorianCalendar();
		Date dt = ct.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
		return sdf.format(dt);
	}

	/**
	 * @author Tapan K.
	 * @since 15-09-2017
	 * @return current time as hh:mi (24Hr Format)
	 */
	public static String getStringNowAsHrMi24HrFormat()
	{
		Calendar ct = new GregorianCalendar();
		Date dt = ct.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
		return sdf.format(dt);
	}

	
	/**
	* @author Tapan K.
	* @version 1.0
	* @since 09-04-2019
	* @return the List of Dates between 2 dates
	* @throws ParseException 
	*/
	public static List<Date> getListOfDatesBetween2Dates(String strFromDate, String strToDate) throws ParseException
	{
		List<Date> datesList = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		long lnStartTime = sdf.parse(strFromDate).getTime();
		long lnEndTime = sdf.parse(strToDate).getTime();
		long lnInterval = 24 * 1000 * 60 * 60; // 1 hour in millis
		while (lnStartTime <= lnEndTime)
		{
			datesList.add(new Date(lnStartTime));
			lnStartTime += lnInterval;
		}

		return datesList;
	}
	
	/**
	* @author Tapan K.
	* @since  09-04-2019
	* @return No. of days in a month & year
	*/
	public static int calculateNoOfDaysInMonthYear(int month, int year) 
	{
		Calendar monthStart = new GregorianCalendar(year, (month-1), 1);
		return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	* @author Tapan K.
	* @since  09-04-2019
	* @return No. of Years, Months & Days as "Years/Months/Days" between 2 dates
	*/
	public static String getNoOfYyMmDaysBetweenTwoDates(Date fromDate, Date toDate)
	{
		int years = 0;
		int months = 0;
		int days = 0;

		// create calendar object for birth day
		Calendar startDate = Calendar.getInstance();
		startDate.setTimeInMillis(fromDate.getTime());

		// create calendar object for current day
		// long currentTime = System.currentTimeMillis();
		Calendar endDate = Calendar.getInstance();
		endDate.setTimeInMillis(toDate.getTime());

		// Get difference between years
		years = endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR);
		int currMonth = endDate.get(Calendar.MONTH) + 1;
		int birthMonth = startDate.get(Calendar.MONTH) + 1;

		// Get difference between months
		months = currMonth - birthMonth;

		// if month difference is in negative then reduce years by one and calculate the number of months.
		if (months < 0)
		{
			years--;
			months = 12 - birthMonth + currMonth;
			if (endDate.get(Calendar.DATE) < startDate.get(Calendar.DATE))
				months--;
		}
		else if (months == 0 && endDate.get(Calendar.DATE) < startDate.get(Calendar.DATE))
		{
			years--;
			months = 11;
		}

		// Calculate the days
		if (endDate.get(Calendar.DATE) > startDate.get(Calendar.DATE))
			days = endDate.get(Calendar.DATE) - startDate.get(Calendar.DATE);
		else if (endDate.get(Calendar.DATE) < startDate.get(Calendar.DATE))
		{
			int today = endDate.get(Calendar.DAY_OF_MONTH);
			endDate.add(Calendar.MONTH, -1);
			days = endDate.getActualMaximum(Calendar.DAY_OF_MONTH) - startDate.get(Calendar.DAY_OF_MONTH) + today;
		}
		else
		{
			days = 0;
			if (months == 12)
			{
				years++;
				months = 0;
			}
		}
		// Return Date Difference
		return years + "/" + months + "/" + days;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 22-08-2017
	 * @param 
	 * @return the Full Month Name List in a Map with Month No.
	 * @throws 
	 * @description 
	 */
	public static Map<Integer,String> getAllMonthListFullNameInMap()
	{
		Map<Integer,String> mapMonthListInFullName = new HashMap<Integer,String>();
		mapMonthListInFullName.put(1, "January");
		mapMonthListInFullName.put(2, "February");
		mapMonthListInFullName.put(3, "March");
		mapMonthListInFullName.put(4, "April");
		mapMonthListInFullName.put(5, "May");
		mapMonthListInFullName.put(6, "June");
		mapMonthListInFullName.put(7, "July");
		mapMonthListInFullName.put(8, "August");
		mapMonthListInFullName.put(9, "September");
		mapMonthListInFullName.put(10, "October");
		mapMonthListInFullName.put(11, "November");
		mapMonthListInFullName.put(12, "December");
		return mapMonthListInFullName;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 22-08-2017
	 * @param 
	 * @return the Short Month Name List in a Map with Month No.
	 * @throws 
	 * @description 
	 */
	public static Map<Integer,String> getAllMonthListShortNameInMap()
	{
		Map<Integer,String> mapMonthListInShort = new HashMap<Integer,String>();
		mapMonthListInShort.put(1, "JAN");
		mapMonthListInShort.put(2, "FEB");
		mapMonthListInShort.put(3, "MAR");
		mapMonthListInShort.put(4, "APR");
		mapMonthListInShort.put(5, "MAY");
		mapMonthListInShort.put(6, "JUN");
		mapMonthListInShort.put(7, "JUL");
		mapMonthListInShort.put(8, "AUG");
		mapMonthListInShort.put(9, "SEP");
		mapMonthListInShort.put(10, "OCT");
		mapMonthListInShort.put(11, "NOV");
		mapMonthListInShort.put(12, "DEC");
		return mapMonthListInShort;
	}

	/** ========================================================= FIN-YEAR METHODS  ========================================================= */
	/**
	 * @author  Tapan K.
	 * @version 1.0
	 * @since   02-05-2017
	 * @return  The Assessment year range e.g. 2016-2017
	 */
	public static String getCurrAssessmentYearAsYYYYtoYY()
	{
		String[] strCurrFinYearArr = getCurrFinYearAsYYYYtoYYYY().split("-");
		int intAssmntStartYear = Integer.valueOf(strCurrFinYearArr[0])+1;
		int intAssmntEndYear = Integer.valueOf(strCurrFinYearArr[1])+1;

		return intAssmntStartYear +"-"+ intAssmntEndYear;
	}

	/**
	 * @author  Tapan K.
	 * @version 1.0
	 * @since   02-05-2017
	 * @return the assessment year range e.g. 2016-2017
	 */
	public static String getAssessmentYearAsYYYYtoYYYYFromFinYear(String strFinYear)
	{
		String[] strFinYearArr = strFinYear.split("-");
		int intAssmntStartYear = Integer.valueOf(strFinYearArr[0])+1;
		int intAssmntEndYear = Integer.valueOf(strFinYearArr[1])+1;

		return intAssmntStartYear +"-"+ intAssmntEndYear;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 05-04-2017
	 * @return the fin year range e.g. 2015-2016
	 */
	public static String getCurrFinYearAsYYYYtoYYYY()
	{
		Calendar calendarObj = Calendar.getInstance();
		String currFinYearAsYYYYtoYYYY = "";
		int currYear = Integer.parseInt(getStringTodayYearAsYYYY());
		int currMonth = Integer.parseInt(getStringTodayMonthAsMM());
		int currDay = 0;

		if(getStringTodayDateAsDD().equals(""))
			currDay = calendarObj.get(Calendar.DATE);
		else
			currDay = Integer.parseInt(getStringTodayDateAsDD());

		int finYrStartYear = currYear;

		// Get the year of Fin Year end day -------- 
		String currDate = finYrStartYear +"-"+ finYrStartMon +"-"+ finYrStartDay;
		DateFormat sdfStrToDate = new SimpleDateFormat("yyyy-MM-dd"); 
		Date currDateObj = new Date();
		try
		{
			currDateObj = (Date)sdfStrToDate.parse(currDate);
			calendarObj.setTime(currDateObj);

			calendarObj.add(Calendar.YEAR, 1);
			calendarObj.add(Calendar.DAY_OF_MONTH, -1);

			String finYrEndDate = "";
			finYrEndDate = sdfDDMMYYYY.format(calendarObj.getTime());
			int finYrEndYear = Integer.parseInt(finYrEndDate.substring(finYrEndDate.length()-4));
			// Till this : Get the year of previous day of FinYear start day -------- 

			if(finYrStartYear != finYrEndYear)
			{
				if((currMonth < finYrStartMon) || (currMonth == finYrStartMon && currDay < finYrStartDay))
					currFinYearAsYYYYtoYYYY = (finYrStartYear-1) +"-"+ finYrStartYear;
				else if((currMonth == finYrStartMon && currDay >= finYrStartDay) || currMonth > finYrStartMon)
					currFinYearAsYYYYtoYYYY = finYrStartYear +"-"+ (finYrStartYear+1);
			}
			else
				currFinYearAsYYYYtoYYYY = finYrStartYear +"-"+ finYrEndYear;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return currFinYearAsYYYYtoYYYY;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 19-09-2018
	 * @param any date in string sdFormat dd/MM/yyyy
	 * @return The finYear as YYYY-YYYY from any String date
	 */
	public static String getFinYearAsYYYYtoYYYYForAnyDate(String strInDate)
	{
		String strFinYearAsYYYYtoYYYY = "";

		if(strInDate != null && !strInDate.equals(""))
		{
			String finYrStartEndDatesConcat = getFinYearStartAndEndDatesForAnyDate(strInDate);
			String finYrStartYear = finYrStartEndDatesConcat.substring(6,10);
			String finYrEndYear = finYrStartEndDatesConcat.substring(finYrStartEndDatesConcat.length()-4);
			strFinYearAsYYYYtoYYYY = finYrStartYear +"-"+ finYrEndYear;
		}

		return strFinYearAsYYYYtoYYYY;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 22-03-2018
	 * @param any date in string sdFormat dd/MM/yyyy
	 * @return The finYear Start Date & End Date with a separator "~"
	 */
	public static String getFinYearStartAndEndDatesForAnyDate(String strInDate)
	{
		String strFnYearStartAndEndDatesConcat = "";
		String finYearStartDate = "";
		String finYearEndDate = "";		

		if(strInDate != null && !strInDate.equals(""))
		{
			Calendar calendarObj = Calendar.getInstance();
			int intMonthTmp = Integer.parseInt(strInDate.substring(3,5));
			int intYearTmp = Integer.parseInt(strInDate.substring(6,10));
			int finYrStartYear = intYearTmp;

			if(intMonthTmp == 1 || intMonthTmp == 2 || intMonthTmp == 3)
				finYrStartYear = finYrStartYear-1;

			String finYearStartDateTemp = finYrStartYear +"-"+ finYrStartMon +"-"+ finYrStartDay;
			DateFormat sdfStrToDate = new SimpleDateFormat("yyyy-MM-dd"); 
			Date dateObj = new Date();
			try
			{
				dateObj = (Date)sdfStrToDate.parse(finYearStartDateTemp);
				calendarObj.setTime(dateObj);

				finYearStartDate = sdfDDMMYYYY.format(calendarObj.getTime());

				calendarObj.add(Calendar.YEAR, 1);
				calendarObj.add(Calendar.DAY_OF_MONTH, -1);

				finYearEndDate = sdfDDMMYYYY.format(calendarObj.getTime());
				strFnYearStartAndEndDatesConcat = finYearStartDate +"~"+ finYearEndDate;
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}

		return strFnYearStartAndEndDatesConcat;
	}

	/**
	 * @author Tapan K.
	 * @since  12/01/2024
	 * @return Start Date & End Date for a Fin-Year (YYYY-YYYY) with a separator "#"
	 */
	public static String getStartDateAndEndDateForAnyFinYear(String finYearAsYyyyToYyyy)
	{
		String[] yearsArr = finYearAsYyyyToYyyy.split("-");
		String startYear = yearsArr[0];
		String endYear = String.valueOf(Integer.valueOf(startYear) +1);
		String startDate = "01/04/"+startYear;
		String endDate = "31/03/"+endYear;
		
		return startDate +"#"+ endDate;
	}

	public static List<Integer> getYearListDescending(int minusXyearsPrev, int plusYyearsNext)
	{
		List<Integer> yearList = new ArrayList<Integer>();
		int currYear = Integer.valueOf(getStringTodayYearAsYYYY());
		for(int i=currYear+plusYyearsNext; i>=currYear-minusXyearsPrev; i--)
		{
			yearList.add(i);
		} 
		return yearList;
	}
	
	/**
	 * @author  Tapan K.
	 * @version 1.0
	 * @since   23-04-2019
	 * @return  The list of Fin Years (YYYY-YYYY) as per input of Start Year
	 */
	public static List<String> getFinYearsListDescStartingCurrFinYrAsYYYYtoYYYY(int intLowestYear)
	{
		String currFinYear = getCurrFinYearAsYYYYtoYYYY();
		String[] currFinYrYearsArr = currFinYear.split("-");
		Integer currFinYrEndYearInt = Integer.valueOf(currFinYrYearsArr[1]);

		List<String> finYearList = new ArrayList<String>();

		for(int x=currFinYrEndYearInt; x>intLowestYear; x--)
		{
			int startYear = currFinYrEndYearInt - 1;
			String strEndYrTmp = String.valueOf(currFinYrEndYearInt);
			String strFinYr = startYear +"-"+ strEndYrTmp;

			finYearList.add(strFinYr);

			currFinYrEndYearInt--;
		}
		return finYearList;
	}


	/** =========================================================  END : FIN-YEAR METHODS  ========================================================= */

	/**
	 * @author  Akash Behera
	 * @version 1.0
	 * @since   07-12-2023
	 * @return  returns true if current day is equal to the last day of the month
	 */
	
	public static boolean isLastDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int lastDayOfMonth = currentDate.lengthOfMonth();

        return currentDay == lastDayOfMonth;
    }
	
	
	/**
	 * @author  Akash Behera
	 * @version 1.0
	 * @since   07-12-2023
	 * @return  returns true if current day is equal to the last day of the quarter
	 */
    public static boolean isLastDayOfQuarter() {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int lastDayOfMonth = currentDate.lengthOfMonth();

        boolean isLastDayOfQuarter = false;
        boolean isQuarterMonth = currentDate.getMonthValue() == 1 || currentDate.getMonthValue() == 4 || currentDate.getMonthValue() == 7 || currentDate.getMonthValue() == 10;
        if (isQuarterMonth && (currentDay == lastDayOfMonth)) {
            isLastDayOfQuarter = true;
        }

        return isLastDayOfQuarter;
    }
    
    
	/**
	 * @author  Akash Behera
	 * @version 1.0
	 * @since   07-12-2023
	 * @return  returns true if current day is equal to the last day of the half year
	 */
    public static boolean isLastDayOfHalfYear() {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int lastDayOfMonth = currentDate.lengthOfMonth();

        boolean isLastDayOfHalfYear = false;
        boolean isHalfMonth = currentDate.getMonthValue() == 1 || currentDate.getMonthValue() == 7;
        if (isHalfMonth && (currentDay == lastDayOfMonth)) {
        	isLastDayOfHalfYear = true;
        }

        return isLastDayOfHalfYear;
    }
    
	/**
	 * @author  Akash Behera
	 * @version 1.0
	 * @param strAnnualMonth i.e. configured in app_config_parameters 
	 * @since   07-12-2023
	 * @return  returns true if current day is equal to the last day of the year
	 */
    public static boolean isLastDayOfYear(String strAnnualMonth) {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int lastDayOfMonth = currentDate.lengthOfMonth();

        boolean isLastDayOfYear = false;
        boolean isAnnual = currentDate.getMonthValue() == Integer.valueOf(strAnnualMonth);
        if (isAnnual && (currentDay == lastDayOfMonth)) {
        	isLastDayOfYear = true;
        }

        return isLastDayOfYear;
    }
    
    
/**
 * @author Tapan K.
 * @version 1.0
 * @since 25-09-2017
 * @param String strDateOfBirth, int intRetirementAge
 * @return the Retirement Date based on DOB and Retirement Age
 * @throws ParseException 
 * @description Returns the Retirement Date based on DOB (If DOB is on 1st, then 'x' years-1day, else last date of month falling after adding 'x' years
 */
public static String getRetirementDateBasedOnDOB(String strDateOfBirth, int intRetirementAge) throws ParseException
{
	String strOutRetirementDate = "";
	String strDateAfterAddingYears = "";
	String strPrevDateAfterAddingYears = "";
	Calendar calendarObj = Calendar.getInstance();

	// Convert the Input String Date to a Date() sdFormat ---
	Date dtDob = sdfDDMMYYYY.parse(strDateOfBirth);

	/**  (1) ------------------   ADD NO OF YEARS TO AN INPUT DATE   ------------------- */
	// Add 'n' no of years ---
	calendarObj.setTime(dtDob);
	calendarObj.add(Calendar.YEAR, intRetirementAge);
	Date daysPlusYearsDate = calendarObj.getTime();

	strDateAfterAddingYears = sdfDDMMYYYY.format(daysPlusYearsDate);

	/**  (2) ------------------   GET DATE-1 AFTER ADDING NO OF YEARS TO AN INPUT DATE   ------------------- */
	Date myDate = sdfDDMMYYYY.parse(strDateAfterAddingYears);

	// Use the Calendar class to subtract one day
	calendarObj.setTime(myDate);
	calendarObj.add(Calendar.DAY_OF_YEAR, -1);

	Date previousDate = calendarObj.getTime();
	strPrevDateAfterAddingYears = sdfDDMMYYYY.format(previousDate);

	/**  (3) ------------------   GET LAST DATE OF MONTH FALLING AFTER ADDING NO OF YEARS TO AN INPUT DATE   ------------------- */

	Date monthEndDate = getMonthEndDate(previousDate);

	/** (4) --------- CHECK OF DOB IS ON 1st DATE OR NOT ----------- */
	// If On 1st, then Retirement Date = strPrevDateAfterAddingYears
	int dateOnly = Integer.parseInt(strDateOfBirth.substring(0, strDateOfBirth.indexOf("/")));
	if(dateOnly == 1)
	{
		strOutRetirementDate = strPrevDateAfterAddingYears;
	}
	// Else, Retirement Date = Last date of the month of adding 'x' years to DOB
	else
	{
		strOutRetirementDate = sdfDDMMYYYY.format(monthEndDate);;
	}
	return strOutRetirementDate;
}

/**
 * @author Tapan K.
 * @since  29-12-2023
 * @return The end date after a month for a given Date (dd/MM/yyyy)
 * @descr  For 01/01/2023, EndDate will be 31/01/2023, For 15/01/2023, it will be 14/02/2023 and so on ...
 */
public static String getNextMonthlyEndDateForGivenDate(String dateAsDdMmYyyy) throws ParseException 
{
	Date dateDt = sdfDDMMYYYY.parse(dateAsDdMmYyyy);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateDt);

    if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) 
    {
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
    } 
    else 
    {
        calendar.roll(Calendar.MONTH, true);
    }
    return sdfDDMMYYYY.format(calendar.getTime().getTime() - MILLIS_IN_A_DAY);
}


/**
 * @author surya
 * @version 1.0
 * @since 15-06-2017
 * @param 
 * @return date
 * @throws ParseException 
 * @description 
 */
public static Date getMonthEndDate(Date date) throws ParseException 
{
	Date endDate;
	Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(date);
	calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	setTimeToEndofDay(calendar);
	endDate = calendar.getTime();
	return sdfDDMMYYYY.parse(sdfDDMMYYYY.format(endDate));
}

private static void setTimeToEndofDay(Calendar calendar) 
{
	calendar.set(Calendar.HOUR_OF_DAY, 23);
	calendar.set(Calendar.MINUTE, 59);
	calendar.set(Calendar.SECOND, 59);
	calendar.set(Calendar.MILLISECOND, 999);
}

	public static String calculateMonthEndDate(int month, int year)
	{
		int[] daysInAMonth = { 29, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int day = daysInAMonth[month];
		boolean isLeapYear = new GregorianCalendar().isLeapYear(year);

		if (isLeapYear && month == 2)
		{
			day++;
		}
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		java.util.Date monthEndDate = new java.util.Date(gc.getTime().getTime());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(monthEndDate);
	}

	public static int numberOfDaysInMonth(int monthId, int processingYear) {
		Calendar monthStart = new GregorianCalendar(processingYear, (monthId-1), 1);
		return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @author Ipsita
	 * @date 28-12-2018
	 * @param fromDate
	 * @param tillDate
	 * @return
	 * @throws ParseException
	 */
	public static Map<Integer,Map<Integer,Integer>> getMonthYearListForArrearSalary(String fromDate, String tillDate) throws ParseException
	{
		Date dtFromDate = sdfDDMMYYYY.parse(fromDate);
		Date dtTillDate = sdfDDMMYYYY.parse(tillDate);
		// String dateYearDiff = ApplicationDateUtils.calculateDateDifference(dtFromDate, dtTillDate);
		String dateYearDiff = ApplicationDateUtils.calculateDateDifferenceModified(dtFromDate, dtTillDate);

		String dateYearDiff1[] = dateYearDiff.split(": ");
		String dateYearDiff2[] = dateYearDiff1[1].split("/");
		Integer yearDiff = Integer.parseInt(dateYearDiff2[0]);

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(dtFromDate);
		Calendar finishCalendar = Calendar.getInstance();
		finishCalendar.setTime(dtTillDate);

		Map<Integer, Map<Integer, Integer>> datesList = null;

		Map<Integer, Integer> map1 = null;
		while (beginCalendar.before(finishCalendar))
		{
			// add one month to date per loop
			if (datesList != null)
			{
				for (Map.Entry<Integer, Map<Integer, Integer>> m1 : datesList.entrySet())
				{
					if (m1.getKey() == beginCalendar.get(Calendar.YEAR))
					{
						map1 = m1.getValue();
						map1.put(beginCalendar.get(Calendar.MONTH) + 1, beginCalendar.get(Calendar.YEAR));
						datesList.put(m1.getKey(), map1);
					}
					else if (datesList != null && !datesList.containsKey(beginCalendar.get(Calendar.YEAR)))
					{
						map1 = new HashMap<Integer, Integer>();
						map1.put(beginCalendar.get(Calendar.MONTH) + 1, beginCalendar.get(Calendar.YEAR));
						datesList.put(beginCalendar.get(Calendar.YEAR), map1);
						break;
					}
				}
			}
			else
			{
				datesList = new HashMap<Integer, Map<Integer, Integer>>();
				map1 = new HashMap<Integer, Integer>();
				if (yearDiff > 0)
				{
					map1.put(beginCalendar.get(Calendar.MONTH) + 1, beginCalendar.get(Calendar.YEAR));
					datesList.put(beginCalendar.get(Calendar.YEAR), map1);
				}
				else
				{
					map1.put(beginCalendar.get(Calendar.MONTH) + 1, beginCalendar.get(Calendar.YEAR));
					datesList.put(beginCalendar.get(Calendar.YEAR), map1);
				}
			}
			beginCalendar.add(Calendar.MONTH, 1);
		}
		return datesList;
	}
	
	public static String calculateDateDifferenceModified(Date from, Date to)
	{
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.setTime(from);
		toDate.setTime(to);
		int increment = 0;
		int year, month, day;
		if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH))
		{
			increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		// DAY CALCULATION
		if (increment != 0)
		{
			day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
			increment = 1;
		}
		else
		{
			day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
		}

		// MONTH CALCULATION
		if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH))
		{
			month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
			increment = 1;
		}
		else
		{
			month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
			increment = 0;
		}

		// YEAR CALCULATION
		year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
		// return year+"\tYears\t\t"+month+"\tMonths\t\t"+day+"\tDays";

		return "Difference : " + year + "/" + month + "/" + day;
	}


	
	public static String generateSalaryBillNo(String monthNumAndYearHashConcat) 
	{
		int monthNum = 0;
		int payYear = 0;
		String finYear = "";
		if(monthNumAndYearHashConcat.indexOf("#") != -1)
		{
			monthNum = Integer.parseInt(monthNumAndYearHashConcat.substring(0, monthNumAndYearHashConcat.indexOf("#")));
			payYear = Integer.parseInt(monthNumAndYearHashConcat.substring(monthNumAndYearHashConcat.indexOf("#")+1));
			finYear = getFinYearAsYYYYtoYYFromMonthYear(monthNum, payYear);

		}
		else
		{
			monthNum = Integer.parseInt(monthNumAndYearHashConcat);
			payYear = Integer.parseInt(getStringTodayYearAsYYYY());
			finYear = getCurrFinYearAsYYYYtoYY();
		}

		int currMonthFinancialNo = getFinancialMonthNo(monthNum);
		return currMonthFinancialNo + "/" + finYear;
	}
	
	public static String getFinYearAsYYYYtoYYFromMonthYear(int intMonth, int intYear)
	{
		String strFinYearAsYYYYtoYYFromMonthYear = "";

		try
		{
			if(!String.valueOf(intMonth).equals("") && intMonth != 0 && !String.valueOf(intYear).equals("") && intYear != 0)
			{
				if(intMonth >= 4 && intMonth <= 12)
					strFinYearAsYYYYtoYYFromMonthYear = intYear +"-"+ String.valueOf(intYear+1).substring(2,4);
				else
					strFinYearAsYYYYtoYYFromMonthYear = (intYear-1) +"-"+ String.valueOf(intYear).substring(2,4);
			}
			else
				strFinYearAsYYYYtoYYFromMonthYear = getCurrFinYearAsYYYYtoYY();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			strFinYearAsYYYYtoYYFromMonthYear = getCurrFinYearAsYYYYtoYY();
		}

		return strFinYearAsYYYYtoYYFromMonthYear;
	}

	public static String getFinYearAsYYYYtoYYYYFromMonthYear(int intMonth, int intYear)
	{
		String strFinYearAsYYYYtoYYYYFromMonthYear = "";

		try
		{
			if(!String.valueOf(intMonth).equals("") && intMonth != 0 && !String.valueOf(intYear).equals("") && intYear != 0)
			{
				if(intMonth >= 4 && intMonth <= 12)
					strFinYearAsYYYYtoYYYYFromMonthYear = intYear +"-"+ String.valueOf(intYear+1).substring(0,4);
				else
					strFinYearAsYYYYtoYYYYFromMonthYear = (intYear-1) +"-"+ String.valueOf(intYear).substring(0,4);
			}
			else
				strFinYearAsYYYYtoYYYYFromMonthYear = getCurrFinYearAsYYYYtoYYYY();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			strFinYearAsYYYYtoYYYYFromMonthYear = getCurrFinYearAsYYYYtoYYYY();
		}

		return strFinYearAsYYYYtoYYYYFromMonthYear;
	}

	public static String getCurrFinYearAsYYYYtoYY()
	{
		Calendar calendarObj = Calendar.getInstance();
		String currFinYearAsYYYYtoYY = "";
		int currYear = Integer.parseInt(getStringTodayYearAsYYYY());
		int currMonth = Integer.parseInt(getStringTodayMonthAsMM());
		int currDay = 0;

		if(getStringTodayDateAsDD().equals(""))
			currDay = calendarObj.get(Calendar.DATE);
		else
			currDay = Integer.parseInt(getStringTodayDateAsDD());

		int finYrStartYear = currYear;

		// Get the year of Fin Year end day -------- 
		String currDate = finYrStartYear +"-"+ finYrStartMon +"-"+ finYrStartDay;
		DateFormat sdfStrToDate = new SimpleDateFormat("yyyy-MM-dd"); 
		Date currDateObj = new Date();
		try
		{
			currDateObj = (Date)sdfStrToDate.parse(currDate);
			calendarObj.setTime(currDateObj);

			calendarObj.add(Calendar.YEAR, 1);
			calendarObj.add(Calendar.DAY_OF_MONTH, -1);

			String finYrEndDate = "";
			finYrEndDate = sdfDDMMYYYY.format(calendarObj.getTime());
			int finYrEndYear = Integer.parseInt(finYrEndDate.substring(finYrEndDate.length()-4));
			// Till this : Get the year of previous day of FinYear start day -------- 

			if(finYrStartYear != finYrEndYear)
			{
				if((currMonth < finYrStartMon) || (currMonth == finYrStartMon && currDay < finYrStartDay))
				{
					currFinYearAsYYYYtoYY = (finYrStartYear-1) +"-"+ String.valueOf(finYrStartYear).substring(2,4);
				}
				else if((currMonth == finYrStartMon && currDay >= finYrStartDay) || currMonth > finYrStartMon)
				{
					currFinYearAsYYYYtoYY = finYrStartYear +"-"+ String.valueOf(finYrStartYear+1).substring(2,4);
				}
			}
			else
				currFinYearAsYYYYtoYY = finYrStartYear +"-"+ String.valueOf(finYrEndYear).substring(2,4);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return currFinYearAsYYYYtoYY;
	}
	
	public static int getFinancialMonthNo(int actualMonthNo)
	{
		int financialMonthNo = 0;
		if(actualMonthNo>3 && actualMonthNo<=12)
			financialMonthNo = actualMonthNo-3;
		else
			financialMonthNo = actualMonthNo+9;

		return financialMonthNo;
	}
	
	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 02-12-2017
	 * @param 
	 * @return Get the month name from month number (1-Jan)
	 * @throws 
	 * @description 
	 */
	public static String getMonthNameMmmFromMonthNum(int monthNo) 
	{
		String monthName = "";
		if(monthNo != 0)
		{
			monthName = new DateFormatSymbols().getMonths()[monthNo-1];
			return monthName.substring(0,3);
		}
		else
			return monthName;
	}
	public static Map<Integer, Integer> getDateList(String fromDate, String tillDate) throws ParseException
	{
		Date dtFromDate = sdfDDMMYYYY.parse(fromDate);
		Date dtTillDate = sdfDDMMYYYY.parse(tillDate);
		String dateYearDiff = ApplicationDateUtils.calculateDateDifferenceModified(dtFromDate, dtTillDate);

		String dateYearDiff1[] = dateYearDiff.split(": ");
		String dateYearDiff2[] = dateYearDiff1[1].split("/");
		Integer yearDiff = Integer.parseInt(dateYearDiff2[0]);

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(dtFromDate);
		Calendar finishCalendar = Calendar.getInstance();
		finishCalendar.setTime(dtTillDate);

		Map<Integer,Integer> emplUserList = new HashMap<Integer,Integer>();

		while (beginCalendar.before(finishCalendar))
		{
			// add one month to date per loop
			if (yearDiff > 0)
			{
				emplUserList.put(beginCalendar.get(Calendar.MONTH) + 1, beginCalendar.get(Calendar.YEAR));
			}
			else
			{
				emplUserList.put(beginCalendar.get(Calendar.MONTH) + 1, beginCalendar.get(Calendar.YEAR));
			}
			beginCalendar.add(Calendar.MONTH, 1);
		}
		return emplUserList;
	}
	

	
	public static String getFinYearAsYYYYtoYYAsPerCurrDate()
	{
		String strReqdFinYear = "";
		String strDateForComparison = "";
		String strDDMMForComparison = "01/01";
		String strCurrFinYear = getCurrFinYearAsYYYYtoYY();
		String strEndYrInCurrFinYr = "";

		try
		{
			strEndYrInCurrFinYr = strCurrFinYear.substring(0,2).concat(strCurrFinYear.substring(strCurrFinYear.indexOf("-")+1));
			strDateForComparison = strDDMMForComparison +"/"+ strEndYrInCurrFinYr;

			Calendar calendarObj = Calendar.getInstance();
			Date dtTodayDate = calendarObj.getTime();
			if(dtTodayDate.before(sdfDDMMYYYY.parse(strDateForComparison)))
				strReqdFinYear = getPreviousFinYearAsYYYYtoYYByXyears(1);
			else
				strReqdFinYear = strCurrFinYear;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return strReqdFinYear;
	}
	public static String getPreviousFinYearAsYYYYtoYYByXyears(int intDeductByXYears)
	{
		String currFinYear = getCurrFinYearAsYYYYtoYY();
		String strPrevFinYear = "";
		String strYearsArr[] = currFinYear.split("-");
		Integer intStartYear = Integer.parseInt(strYearsArr[0]) - intDeductByXYears;
		Integer intEndYear = Integer.parseInt(strYearsArr[1]) - intDeductByXYears;
		strPrevFinYear = intStartYear +"-"+ intEndYear;
		return strPrevFinYear;
	}
	
	public static int getCurrentYearFromFinancialYear(String financialYear) {
        String[] years = financialYear.split("-");
        String startYearString = years[0];
        int startYear = Integer.parseInt(startYearString);

        return startYear;
    }
	public static List<String> getFinYears()
	{
		String currFinYear = getCurrFinYearAsYYYYtoYY();
		List<String> fiYearList = new ArrayList<String>();
		String years[] = currFinYear.split("-");
		Integer prevYear = Integer.parseInt(years[0]);
		Integer postYear = Integer.parseInt(years[1]);
		for(int i=0;i<20;i++)
		{
			String year ="";
			if(postYear<10)
				year = prevYear+"-0"+postYear;
			else
				year = prevYear+"-"+postYear;
			
			fiYearList.add(year);
			prevYear=prevYear-1;
			postYear=postYear-1;
		}
		return fiYearList;
	}

	/**
	 * @author Tapan K.
	 * @version 1.0
	 * @since 018-01-2024
	 * @param 
	 * @return convert YYYY-DD-MM to DD/MM/YYYY
	 * @description 
	 */
	public static int getWeekNoOfMonthFromAnyDate(Date inputDateAsYYYYDDMM)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDateAsYYYYDDMM);
		int intWeekNoInMonth = cal.get(Calendar.WEEK_OF_MONTH);

		return intWeekNoInMonth;
	}
	
	/**
	 * @author Anuradha
	 * @since 07-02-2024
	 * @return year as financialyear
	 */
	
	public static String getFinancialYear(String selectedYear, Long selectedMonth) {
		String startYear = null; // Declare startYear as an Integer and initialize as null
	    
	    // Check if selectedYear is not null and has at least 4 characters
		if (selectedMonth >= 4) {
	        // Extract the start year from the selectedYear string
	        startYear = String.valueOf(selectedYear.substring(0, 4));
	    }
	    else {
	    	  startYear = String.valueOf(selectedYear.substring(5, 9));
	    }
	    // If the selected month is April or later, it's part of the next financial year
	    return startYear;
	}
	
	/**
	 * @author Akash
	 * @since 22-02-2024
	 * @return financial year from current month and year
	 */
	
	public static String getFinancialYearFromCurrMonthAndYear(Integer currMonth,Integer currYear) {
	    String startYear = ""; 
	    String endYear = "";
	    
	    if (currMonth >= 4) {
	        startYear = currYear.toString();
	        endYear = String.valueOf(currYear + 1);
	    } else {
	        startYear = String.valueOf(currYear - 1);
	        endYear = currYear.toString();
	    }
	    String finYear = startYear+"-"+endYear;
	    
	    return finYear;
	}

	
	
}
