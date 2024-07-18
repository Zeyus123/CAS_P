package com.aashdit.prod.cmc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ComparatorsForSorting {

	public static void sortHolidaysAndNonWrkDatesOnDate(List<String> weeklyOffAndHolidayDatesList)
	{
		Collections.sort(weeklyOffAndHolidayDatesList, new Comparator<String>() 
		{
			public int compare(String compObj1, String compObj2)
			{
				String strCompObj1 = compObj1.substring(0, compObj1.indexOf("(")).trim();
				String strCompObj2 = compObj2.substring(0, compObj2.indexOf("(")).trim();
				
				Date dtCompDate1 = null;
				Date dtCompDate2 = null;
				try
				{
					dtCompDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(strCompObj1);
					dtCompDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(strCompObj2);
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				
				int intComparedValue = 0;
				intComparedValue = dtCompDate1.compareTo(dtCompDate2);
				return intComparedValue;
			}
		});
	}

	public static List<Map.Entry<Integer,Map<Integer,Integer>>> sortMonthYearMapForPayArrearList(Map<Integer, Map<Integer, Integer>> emplUserList) 
	{
		List<Map.Entry<Integer,Map<Integer,Integer>>> outputSortedMap = new ArrayList<Map.Entry<Integer,Map<Integer,Integer>>>(emplUserList.entrySet());
		Collections.sort(outputSortedMap, new Comparator<Map.Entry<Integer,Map<Integer,Integer>>>() 
		{
			public int compare(Map.Entry<Integer,Map<Integer,Integer>> object1, Map.Entry<Integer,Map<Integer,Integer>> object2)
			{
				return object1.getKey().compareTo(object2.getKey());
			}
		});
		return outputSortedMap;
	}
	
	public static List<Map.Entry<Integer, Integer>> sortMonthYearMapIn7Pay(Map<Integer, Integer> emplUserList)
	{
		List<Map.Entry<Integer, Integer>> outputSortedMap = new ArrayList<Map.Entry<Integer, Integer>>(emplUserList.entrySet());
		Collections.sort(outputSortedMap, new Comparator<Map.Entry<Integer, Integer>>() 
		{
			public int compare(Map.Entry<Integer, Integer> object1, Map.Entry<Integer, Integer> object2)
			{
				return object1.getValue().compareTo(object2.getValue());
			}
		});
		return outputSortedMap;
	}

		public static void sortFinYearsListDescending(List<String> finYearsList)
	{
		Collections.sort(finYearsList, new Comparator<String>() 
		{
			public int compare(String obj1, String obj2)
			{
				int intComparedValue = 0;
				intComparedValue = obj2.compareTo(obj1);
				return intComparedValue;
			}
		});
	}

}
