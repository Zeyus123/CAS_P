
package com.aashdit.prod.cmc.utils;

import java.math.BigDecimal;

public class ConvertAmountToWords
{
	/** String array of words for tens Names */
	static final String[] tensNames = { "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };
	/** String array of words for ones Names */
	static final String[] onesNames = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
	/**
	 * Creates a new AmountToWordConverter object.
	 */
	public ConvertAmountToWords()
	{
	}
	/**
	 * This method converts the given amount string into words
	 * @param inAmountInString String Amount
	 * @return String output
	 * @throws Exception
	 */
	public static String convertToWords(String inAmountInString) throws Exception
	{
		if(isScientificNotation(inAmountInString)){
			inAmountInString = new BigDecimal(inAmountInString).toPlainString();
		}
		String amountInWordsOut = "";

		try
		{
			if(!inAmountInString.equals("") && !inAmountInString.equalsIgnoreCase("null") && Float.parseFloat(inAmountInString) > 0)
			{
				String totalAmount = inAmountInString;
				int indexOfDecimal = totalAmount.indexOf(".");
				String beforeDecimal = totalAmount;
				if (indexOfDecimal > -1)
				{
					beforeDecimal = totalAmount.substring(0, indexOfDecimal);
				}
				
				if(Double.parseDouble(beforeDecimal) > 1)
					amountInWordsOut = "Rupees " + evaluate(beforeDecimal);
				else
					amountInWordsOut = "Rupees " + evaluate(beforeDecimal);
				
				if (indexOfDecimal > -1)
				{
					String decimalValue = totalAmount.substring((indexOfDecimal + 1));
					if(decimalValue.length()==1)
					{
						decimalValue = decimalValue+"0";
					}
					if(Integer.parseInt(decimalValue) > 0)
						amountInWordsOut += (" and " + evaluate(decimalValue) + "Paise only");
					else
						amountInWordsOut += ("only");
				}
				else
				{
					amountInWordsOut += ("only");
				}
			}
			else
				amountInWordsOut = "-";
		}
		catch (Exception exception)
		{
			System.out.println("Error in AmountToWordConverter.converter(String aStrAmount) method");
			throw exception;
		}
		return amountInWordsOut;
	}
	/**
	 * This method converts the given amount string into words
	 * @param aStrAmount String Amount
	 * @return String output
	 * @throws Exception
	 */
	public String convertToWordsFromInteger(int iAmount) throws Exception
	{
		String output = "";
		Integer i = null;
		try
		{
			i = new Integer(iAmount);
			output = convertToWords(i.toString());
		}
		catch (Exception exception)
		{
			System.out.println("Error in AmountToWordConverter.converter(int iAmount) method");
			throw exception;
		}
		return output;
	}
	/**
	 * This method is used to validate the number in string sdFormat
	 * @param text
	 *            String
	 * @return String
	 * @throws Exception
	 */
	private static String evaluate(String text) throws Exception
	{
		long number = 0;
		try
		{
			number = Long.parseLong(text);
		}
		catch (NumberFormatException eNumberFormatException)
		{
			System.out.println("Error in AmountToWordConverter.evaluate(String text) method");
			throw eNumberFormatException;
		}
		return evaluate(number);
	}
	/**
	 * This method is used to compute the number in words
	 * @param number
	 *            long
	 * @return String
	 */
	private static String evaluate(long number)
	{
		long temp = number;
		long crore = temp / 10000000;
		temp %= 10000000;
		long lakh = temp / 100000;
		temp %= 100000;
		long thousands = temp / 1000;
		temp %= 1000;
		long hundreds = temp / 100;
		temp %= 100;
		StringBuffer result = new StringBuffer(30);
		if (crore > 0)
		{
			result.append(evaluate(crore) + "Crore ");
		}
		if (lakh > 0)
		{
			result.append(evaluate(lakh) + "Lakh ");
		}
		if (thousands > 0)
		{
			result.append(evaluate(thousands) + "Thousand ");
		}
		if (hundreds > 0)
		{
			result.append(evaluate(hundreds) + "Hundred ");
		}
		if (temp != 0)
		{
			/*if (number >= 100)
			{
				result.append("and ");
			}*/
			if ((0 < temp) && (temp <= 19))
			{
				result.append(onesNames[(int) temp]+ " ");
			}
			else
			{
				long tens = temp / 10;
				long ones = temp % 10;
				result.append(tensNames[(int) tens] + " ");
				result.append(onesNames[(int) ones] + " ");
			}
		}
		if ((result.toString()).trim().equals(""))
		{
			result.append(" zero ");
		}
		return result.toString();
	}
	
	/**
	* @author Ipsita
	* @version 1.0
	* @since 01-08-2017
	* @param 
	* @return check exponential number
	* @description 
	*/
	public static boolean isScientificNotation(String numberString) 
	{
	    // Validate number
	    try 
	    {
	        new BigDecimal(numberString);
	    } 
	    catch (NumberFormatException e) 
	    {
	        return false;
	    }

	    // Check for scientific notation
	    return numberString.toUpperCase().contains("E");   
	}
}