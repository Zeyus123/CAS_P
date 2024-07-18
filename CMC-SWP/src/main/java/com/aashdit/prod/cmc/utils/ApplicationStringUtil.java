package com.aashdit.prod.cmc.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class ApplicationStringUtil 
{
	public static String convertAmountToINR(String price) 
	{
		final DecimalFormat df2 = new DecimalFormat("#.##");
		String amount = df2.format(Double.parseDouble(price)).toString();

		String amountToBeConverted = "";
		String inrFormat = "";
		String val = "";

		if (amount.indexOf(".") != -1) 
		{
			amountToBeConverted = amount.substring(0, amount.indexOf("."));
			val = amount.substring(amount.indexOf(".") + 1);
			if (val.length() == 0) {
				val = val + "00";
			}
			if (val.length() == 1) {
				val = val + "0";
			}
		}
		else
		{
			amountToBeConverted = amount;
			val = "00";
		}

		StringBuilder strBuilder = new StringBuilder();
		char amountArray[] = amountToBeConverted.toCharArray();
		int length1 = 0, length2 = 0;

		for (int i = amountArray.length - 1; i >= 0; i--) 
		{
			if (length1 < 3) 
			{
				strBuilder.append(amountArray[i]);
				length1++;
			} 
			else if (length2 < 2) 
			{
				if (length2 == 0) 
				{
					strBuilder.append(",");
					strBuilder.append(amountArray[i]);
					length2++;
				} 
				else 
				{
					strBuilder.append(amountArray[i]);
					length2 = 0;
				}
			}
		}
		inrFormat = strBuilder.reverse().append(".").append(val).toString();

		if (foundScientificNotation(inrFormat)) 
		{
			inrFormat = new BigDecimal(inrFormat).toPlainString();
			Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			inrFormat = format.format(new BigDecimal(inrFormat));
		}

		return inrFormat;
	}

	public static boolean foundScientificNotation(String numberString) 
	{
		try {
			new BigDecimal(numberString);
		} catch (NumberFormatException e) {
			return false;
		}

		return numberString.toUpperCase().contains("E");
	}

	public static String TotalAmtOfTDSDedu(String price, String tds) 
	{
		Double total = 0.0D;
		Double totalDedc = 0.0D;
		total = (100 * Double.parseDouble(price) / (100 - Double.parseDouble(tds)));

		totalDedc = total - Double.parseDouble(price);

		return totalDedc.toString();
	}
	
	/**
	 * @return Value (String) in #,##,##,### sdFormat without any decimal value
	 * @description Convert value to INR Format i.e. #,##,##,### without any decimal
	 *              value
	 */
	public static String convertValueToINRFormatWithoutDecimal(String strInputValue) 
	{
		String finalFormattedAmount = convertAmountToINRFormat(strInputValue);
		if (finalFormattedAmount.indexOf(".") != -1) {
			finalFormattedAmount = finalFormattedAmount.substring(0, finalFormattedAmount.indexOf("."));
		}

		return finalFormattedAmount;
	}

	
	/**
	 * @return Amount (String) in #,##,##,###.## sdFormat
	 * @description Convert amount to INR Format i.e. #,##,##,###.##
	 */
	public static String convertAmountToINRFormat(String strInputAmount) 
	{
		if (strInputAmount.equals(""))
			strInputAmount = "0.00";
		String amountToBeConverted = "";
		String finalFormattedAmount = "";
		String decimalValue = "";

		DecimalFormat df1 = new DecimalFormat("#");
		df1.setMaximumFractionDigits(2);
		strInputAmount = df1.format(Double.parseDouble(strInputAmount));

		if (strInputAmount.indexOf(".") != -1) 
		{
			amountToBeConverted = strInputAmount.substring(0, strInputAmount.indexOf("."));
			decimalValue = strInputAmount.substring(strInputAmount.indexOf(".") + 1);
			if (decimalValue.length() == 0)
				decimalValue = decimalValue + "00";
			if (decimalValue.length() == 1)
				decimalValue = decimalValue + "0";
		} 
		else 
		{
			amountToBeConverted = strInputAmount;
			decimalValue = "00";
		}

		StringBuilder stringBuilder = new StringBuilder();
		char amountArray[] = amountToBeConverted.toCharArray();
		int length1 = 0, length2 = 0;

		for (int i = amountArray.length - 1; i >= 0; i--) 
		{
			if (length1 < 3) 
			{
				stringBuilder.append(amountArray[i]);
				length1++;
			} 
			else if (length2 < 2) 
			{
				if (length2 == 0) {
					stringBuilder.append(",");
					stringBuilder.append(amountArray[i]);
					length2++;
				} else {
					stringBuilder.append(amountArray[i]);
					length2 = 0;
				}
			}
		}

		finalFormattedAmount = stringBuilder.reverse().append(".").append(decimalValue).toString();

		if (foundScientificNotation(finalFormattedAmount)) {
			finalFormattedAmount = new BigDecimal(finalFormattedAmount).toPlainString();
			Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			finalFormattedAmount = format.format(new BigDecimal(finalFormattedAmount));
		}
		if (finalFormattedAmount.contains("Rs."))
			finalFormattedAmount = finalFormattedAmount.replaceAll("Rs.", "");
		return finalFormattedAmount.trim();
	}
	
	/**
	 * @author Tapan K.
	 * @since  07/11/2023
	 * @descr  Left-Pad a string with a character for specific length
	 */
	public static String lpadString(String strInput, int totalLength, String strReplaceWith) 
	{
		if (!strInput.equals(""))
			return StringUtils.leftPad(strInput, totalLength, strReplaceWith);
		else
			return "";
	}
	
	/**
	* @author Tapan
	* @version 1.0
	* @since 13-08-2017
	* @param 
	* @return Amount (String) in #,##,##,### sdFormat
	* @description Convert amount to Next Rounded Integer value (Without decimals) irrespective of the decimal value.
	*/
	public static String convertAmountToNextRoundValue(Double dblInputAmount) 
	{
		String strFormattedOutputValue = "0";
		//strInputAmount = strInputAmount.replaceAll(",","");
		Double dblCeilAmount = Math.ceil(dblInputAmount);
		int doubleToIntAmount = dblCeilAmount.intValue();
//		String strFormattedValue = String.valueOf(doubleToIntAmount);
		String commaSeparateValue = convertAmountToINRFormat(String.valueOf(doubleToIntAmount));
		if(commaSeparateValue.indexOf(".") != -1)
			strFormattedOutputValue = commaSeparateValue.substring(0,commaSeparateValue.indexOf("."));
		else
			strFormattedOutputValue = commaSeparateValue;
		
		return strFormattedOutputValue;
	}
	public static String getAssessmentYearAsYYYYtoYYFromFinYear(String strFinYear)
	{
		String[] strFinYearArr = strFinYear.split("-");
		int intAssmntStartYear = Integer.valueOf(strFinYearArr[0])+1;
		int intAssmntEndYear = Integer.valueOf(strFinYearArr[1])+1;

		return intAssmntStartYear +"-"+ intAssmntEndYear;
	}
	
	public static String getStringTodayYearAsYYYY()
	{
		Calendar calendarObj = Calendar.getInstance();
		String strTodayYearAsYYYY = "";
		strTodayYearAsYYYY = String.valueOf(calendarObj.get(Calendar.YEAR));
		return strTodayYearAsYYYY;
	}

	/**
	* @author Tapan
	* @since  21/02/20245
	* @return The calculated Professional Tax based on Month & TotalEarningAmount
	*/
	public static Double getProfessionalTaxByMonthAndTotalEarning(Double totalEarningAmount, Integer monthAsInt)
	{
		Double totalProfTaxAmount = 0.0;

		// Upto Rs.13304 >> Prof.Tax = Rs. 0 -----
		if(totalEarningAmount <= ApplicationConstants.CONST_PRTAX_0TAX_LIMIT)
		{
			totalProfTaxAmount = 0.0;
		}
		
		// Upto Rs.13305 to Rs.25000 - Prof.Tax = Rs. 125 p.m. -----
		if(totalEarningAmount > ApplicationConstants.CONST_PRTAX_0TAX_LIMIT && totalEarningAmount < ApplicationConstants.CONST_PRTAX_125TAX_LIMIT)
		{
			totalProfTaxAmount = 125.0;
		}

		// More than Rs.25001 - Prof.Tax = Rs. 200 p.m. & Rs. 300 in March -----
		if(totalEarningAmount > ApplicationConstants.CONST_PRTAX_125TAX_LIMIT)
		{
			if(monthAsInt == 3)		totalProfTaxAmount = 300.0;
			else					totalProfTaxAmount = 200.0;
		}
		
		return totalProfTaxAmount;
	}

}