package com.aashdit.prod.cmc.framework.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationDateUtils {
	static Calendar calendarObj;
	static SimpleDateFormat sdfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
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
	
	public static String getStringNowAsHrMi24HrFormat()
	{
		Calendar ct = new GregorianCalendar();
		Date dt = ct.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
		return sdf.format(dt);
	}
	
	/**
	 * Bellow static method added for current Fin Year
	 * @author by Mukesh
	 * @Date 25-04-2020
	 * @return
	 */
	public static String getCurrentFinancialYear() {
		calendarObj = Calendar.getInstance();
		String currFinYearAsYYYYtoYYYY = "";
		int finYrStartDay = 1;
		int finYrStartMon = 4;
		int currYear = Integer.parseInt(getStringTodayYearAsYYYY());
		int currMonth = Integer.parseInt(getStringTodayMonthAsMM());
		int currDay = 0;
		if (getStringTodayDateAsDD().equals(""))
			currDay = calendarObj.get(Calendar.DATE);
		else
			currDay = Integer.parseInt(getStringTodayDateAsDD());
		int finYrStartYear = currYear;
		String currDate = finYrStartYear + "-" + finYrStartMon + "-" + finYrStartDay;
		DateFormat sdfStrToDate = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat sdfDateToStr = new SimpleDateFormat("dd/MM/yyyy");
		Date currDateObj = new Date();
		try {
			currDateObj = (Date) sdfStrToDate.parse(currDate);
			calendarObj.setTime(currDateObj);
			calendarObj.add(Calendar.YEAR, 1);
			calendarObj.add(Calendar.DAY_OF_MONTH, -1);
			String finYrEndDate = "";
			finYrEndDate = sdfDateToStr.format(calendarObj.getTime());
			int finYrEndYear = Integer.parseInt(finYrEndDate.substring(finYrEndDate.length() - 4));
			if (finYrStartYear != finYrEndYear) {
				if ((currMonth < finYrStartMon) || (currMonth == finYrStartMon && currDay < finYrStartDay))
					currFinYearAsYYYYtoYYYY = (finYrStartYear - 1) + "-" + finYrStartYear;
				else if ((currMonth == finYrStartMon && currDay >= finYrStartDay) || currMonth > finYrStartMon)
					currFinYearAsYYYYtoYYYY = finYrStartYear + "-" + (finYrStartYear + 1);
			} else
				currFinYearAsYYYYtoYYYY = finYrStartYear + "-" + finYrEndYear;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currFinYearAsYYYYtoYYYY;
	}

	

	public static String getStringTodayDateAsDD() {
		calendarObj = Calendar.getInstance();
		String strTodayDateAsDD = "";
		int todayDate = calendarObj.get(Calendar.DATE);
		if (todayDate < 10)
			strTodayDateAsDD = "0" + todayDate;
		else
			strTodayDateAsDD = String.valueOf(todayDate);

		return strTodayDateAsDD;
	}

	public static String getStringTodayMonthAsMM() {
		calendarObj = Calendar.getInstance();
		String strTodayMonthAsMM = "";
		int todayMonth = calendarObj.get(Calendar.MONTH) + 1;
		if (todayMonth < 10)
			strTodayMonthAsMM = "0" + todayMonth;
		else
			strTodayMonthAsMM = String.valueOf(todayMonth);

		return strTodayMonthAsMM;
	}

	public static String getStringTodayYearAsYYYY() {
		calendarObj = Calendar.getInstance();
		String strTodayYearAsYYYY = "";
		strTodayYearAsYYYY = String.valueOf(calendarObj.get(Calendar.YEAR));
		return strTodayYearAsYYYY;
	}
	
	/**
	 * @purpose
	 * This is for get list of date  by
	 * @param year
	 * @param month
	 * @author mukesh
	 * @Date 02/05/2020
	 * @return
	 */
	public static List<String> findListOfMonthByYearAndMonth(int year,int month)
	{
		List<String> monthList = new ArrayList<String>();
		 SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		    Calendar cal = Calendar.getInstance();
		    cal.clear();
		    cal.set(year, month - 1, 1);
		    int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    for (int i = 0; i < daysInMonth; i++) {
		      //  System.out.println(fmt.format(cal.getTime()));
		    	monthList.add(fmt.format(cal.getTime()));
		        cal.add(Calendar.DAY_OF_MONTH, 1);
		    }	
		    return monthList;
	}
	/**
	 * @author mukesh
	 * get date list of current month and year
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<String> findListOfDateByYearAndMonth(int year,int month)
	{
		List<String> monthList = new ArrayList<String>();
		 SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		    Calendar cal = Calendar.getInstance();
		    cal.clear();
		    cal.set(year, month - 1, 1);
		    int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    for (int i = 0; i < daysInMonth; i++) {
		    	monthList.add(fmt.format(cal.getTime()));
		        cal.add(Calendar.DAY_OF_MONTH, 1);
		    }	
		    return monthList;
	}
	/**
	 * @author mukesh
	 * get date list of every range 
	 * by year and month
	 * @param year
	 * @param month
	 * @return
	 */
	public static Map<Integer,List<String>> findListOfDateOfEveryrangeByYearMonth(int year,int month){
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
			Map<Integer,List<String>> dateListOfrange = new HashMap<Integer,List<String>>(); 
			
			List<String> rangeList1st = new ArrayList<String>();
			List<String> rangeList2nd = new ArrayList<String>();
			List<String> rangeList3rd = new ArrayList<String>();
			List<String> rangeList4th = new ArrayList<String>();
			List<String> rangeList5th = new ArrayList<String>();
			List<String> rangeList6th = new ArrayList<String>();
			
			List<String> dateListOfMonth = findListOfDateByYearAndMonth(year,month);
			int totalrange = getTotalrangeInMonthByYearMonth(year,month);
			for(String date:dateListOfMonth) {
				int rangeNo = 0;
				try {
					rangeNo = getrangeNoOfMonthFromAnyDate(fmt.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(rangeNo==1)
					rangeList1st.add(date);
				if(rangeNo==2)
					rangeList2nd.add(date);
				if(rangeNo==3)
					rangeList3rd.add(date);
				if(rangeNo==4)
					rangeList4th.add(date);
				if(rangeNo==5)
					rangeList5th.add(date);
				if(rangeNo==6)
					rangeList6th.add(date);
			}
			for(int i = 1; i <= totalrange; i++) {
				if(i==1)
					dateListOfrange.put(1,rangeList1st);
				if(i==2)
					dateListOfrange.put(2,rangeList2nd);
				if(i==3)
					dateListOfrange.put(3,rangeList3rd);
				if(i==4)
					dateListOfrange.put(4,rangeList4th);
				if(i==5)
					dateListOfrange.put(5,rangeList5th);
				if(i==6)
					dateListOfrange.put(6,rangeList6th);
				
			}
		   
	    return dateListOfrange;
}

	/**
	 * @author mukesh
	 * get range no by passing date
	 * @param inputDateAsYYYYDDMM
	 * @return
	 */
	public static int getrangeNoOfMonthFromAnyDate(Date inputDateAsYYYYDDMM)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDateAsYYYYDDMM);
		int intrangeNoInMonth = cal.get(Calendar.DAY_OF_MONTH);

		return intrangeNoInMonth;
	}

	/**
	 * @author mukesh
	 * get day name by date
	 * @param dtInputDate
	 * @return
	 * @throws ParseException
	 */
	public static String getDayNameFromAnyDate(Date dtInputDate) throws ParseException
	{
		SimpleDateFormat dtFormat = new SimpleDateFormat("E");
		String strDayName = dtFormat.format(dtInputDate);
		return strDayName;
	}	
	
	/**
	 * @author mukesh
	 * get total range present by year and month
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getTotalrangeInMonthByYearMonth(int year,int month) {
		 SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		 Calendar cal = Calendar.getInstance();
		 cal.set(year, month - 1, 1);
		 System.out.println("=========First Day Of Month========="+cal.getTime());
	     int start = cal.get(Calendar.DAY_OF_YEAR);
	     cal.set(year, month - 1,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	     int end = cal.get(Calendar.DAY_OF_YEAR);
	     return (end-start)+1;
	}
	public static long getDaysBetweenTwoDate(String fromDate, String toDate)
	{
		//Parsing the date
		LocalDate dateBefore = LocalDate.parse(fromDate);
		LocalDate dateAfter = LocalDate.parse(toDate);
			
		//calculating number of days in between
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		//long daysBetween = dateBefore.until(dateAfter, ChronoUnit.DAYS); //alternate method for calculate date
		return noOfDaysBetween;
				  
	}

	/**
	 * @author surya
	 * @version 1.0
	 * @since 19-09-2017
	 * @param 
	 * @return date
	 * @throws ParseException 
	 * @description 
	 */
	public static int numberOfDaysInMonth(int month, int year) 
	{
		Calendar monthStart = new GregorianCalendar(year, (month-1), 1);
		return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @author mukesh
	 * find Date Range Between Two date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<LocalDate> findDateRangeBetweenTwoDate(String startDate,String endDate) {
		long numOfDays = getDaysBetweenTwoDate(startDate,endDate);
		LocalDate startdate = LocalDate.parse(startDate);
		List<LocalDate> daysRange = new ArrayList<LocalDate>((int) numOfDays); 
		daysRange = Stream.iterate(startdate, date -> date.plusDays(1)).limit(numOfDays).collect(Collectors.toList());

		return daysRange;
	}
	
	/**
	 * find list of date on 
	 * the basis of range date
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Map<String,List<LocalDate>> findListOfDateOfRangeByFromTotodate(String fromDate,String toDate){
		
		Map<String,List<LocalDate>> dateListOfrange = new TreeMap<String,List<LocalDate>>(); 
		
		List<LocalDate> rangeList1 = new ArrayList<LocalDate>();List<LocalDate> rangeList7 = new ArrayList<LocalDate>();
		List<LocalDate> rangeList2 = new ArrayList<LocalDate>();List<LocalDate> rangeList8 = new ArrayList<LocalDate>();
		List<LocalDate> rangeList3 = new ArrayList<LocalDate>();List<LocalDate> rangeList9 = new ArrayList<LocalDate>();
		List<LocalDate> rangeList4 = new ArrayList<LocalDate>();List<LocalDate> rangeList10 = new ArrayList<LocalDate>();
		List<LocalDate> rangeList5 = new ArrayList<LocalDate>();List<LocalDate> rangeList11 = new ArrayList<LocalDate>();
		List<LocalDate> rangeList6 = new ArrayList<LocalDate>();List<LocalDate> rangeList12 = new ArrayList<LocalDate>();
	
		List<LocalDate> dateListOfRange = new ArrayList<LocalDate>();
		dateListOfRange = findDateRangeBetweenTwoDate(fromDate,toDate);
		int count = 0;
		for(LocalDate date:dateListOfRange) {
				if(count <=7) {
					rangeList1.add(date);
					count++;
				}
				if(count>7 && count<=14) {
					rangeList2.add(date);
					count++;
				}
				if(count>14 && count<=21) {
					rangeList3.add(date);
					count++;
				}
				if(count>21 && count<=28) {
					rangeList4.add(date);
					count++;
				}
				if(count>28 && count<=35) {
					rangeList5.add(date);
					count++;
				}
				if(count>35 && count<=42) {
					rangeList6.add(date);
					count++;
				}
				if(count>42 && count<=49) {
					rangeList7.add(date);
					count++;
				}
				if(count>49 && count<=56) {
					rangeList8.add(date);
					count++;
				}
				if(count>56 && count<=63) {
					rangeList9.add(date);
					count++;
				}
				if(count>63 && count<=70) {
					rangeList10.add(date);
					count++;
				}
				if(count>70 && count<=77) {
					rangeList11.add(date);
					count++;
				}
				if(count>77 && count<=84) {
					rangeList12.add(date);
					count++;
				}
		}
		String range1 = "",range2 = "",range3 = "",range4 = "",range5 = "",range6 = "",range7 = "",range8 = "",range9 = "",range10 = "",range11 = "",range12 = "";
		if(!rangeList1.isEmpty())
			 range1 = rangeList1.get(0).toString()+"_to_"+rangeList1.get(rangeList1.size()-1).toString();
		else
			range1 = "range1";
		if(!rangeList2.isEmpty())
			range2 = rangeList2.get(0).toString()+"_to_"+rangeList2.get(rangeList2.size()-1).toString();
		else
			range2 = "range2";
		if(!rangeList3.isEmpty())
			range3 = rangeList3.get(0).toString()+"_to_"+rangeList3.get(rangeList3.size()-1).toString();
		else
			range3 = "range3";
		if(!rangeList4.isEmpty())
			range4 = rangeList4.get(0).toString()+"_to_"+rangeList4.get(rangeList4.size()-1).toString();
		else
			range4 = "range4";
		if(!rangeList5.isEmpty())
			range5 = rangeList5.get(0).toString()+"_to_"+rangeList5.get(rangeList5.size()-1).toString();
		else
			range5 = "range5";
		if(!rangeList6.isEmpty())
			range6 = rangeList6.get(0).toString()+"_to_"+rangeList6.get(rangeList6.size()-1).toString();
		else
			range6 = "range6";
		if(!rangeList7.isEmpty())
			range7 = rangeList7.get(0).toString()+"_to_"+rangeList7.get(rangeList7.size()-1).toString();
		else
			range7 = "range7";
		if(!rangeList8.isEmpty())
			 range8 = rangeList8.get(0).toString()+"_to_"+rangeList8.get(rangeList8.size()-1).toString();
		else
			range8 = "range8";
		if(!rangeList9.isEmpty())
			 range9 = rangeList9.get(0).toString()+"_to_"+rangeList9.get(rangeList9.size()-1).toString();
		else
			range9 = "range9";
		if(!rangeList7.isEmpty())
			range10 = rangeList10.get(0).toString()+"_to_"+rangeList10.get(rangeList10.size()-1).toString();
		else
			range10 = "range10";
		if(!rangeList11.isEmpty())
			range11 = rangeList11.get(0).toString()+"_to_"+rangeList11.get(rangeList11.size()-1).toString();
		else
			range11 = "range11";
		if(!rangeList12.isEmpty())
			range12 = rangeList12.get(0).toString()+"_to_"+rangeList12.get(rangeList12.size()-1).toString();
		else
			range12 = "range12";
		
		dateListOfrange.put(range1, rangeList1);
		dateListOfrange.put(range2, rangeList2);
		dateListOfrange.put(range3, rangeList3);
		dateListOfrange.put(range4, rangeList4);
		dateListOfrange.put(range5, rangeList5);
		dateListOfrange.put(range6, rangeList6);
		dateListOfrange.put(range7, rangeList7);
		dateListOfrange.put(range8, rangeList8);
		dateListOfrange.put(range9, rangeList9);
		dateListOfrange.put(range10, rangeList10);
		dateListOfrange.put(range11, rangeList11);
		dateListOfrange.put(range12, rangeList12);
  
    return dateListOfrange;
}
	/**
	 * get financial year list by size(Last number of year)
	 * @Author Mukesh Kumar
	 * @date 30/05/2020
	 */
	public static List<String> getFinancialYearListBySize() {
		int num = 5;
		String currentFnYr = getCurrentFinancialYear();
		int firstYr = Integer.parseInt(currentFnYr.split("-")[0]);
		List<String> finYearList = new ArrayList<String>();
		for(int i=firstYr;i>firstYr-num;i--) {
			int fy = i;
			int sy = i + 1;
			String finYear = Integer.toString(fy)+"-"+Integer.toString(sy);
			finYearList.add(finYear);
		}
		return finYearList;
	}
	
}
