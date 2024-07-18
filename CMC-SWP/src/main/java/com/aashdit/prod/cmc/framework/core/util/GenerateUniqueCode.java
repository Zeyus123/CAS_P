package com.aashdit.prod.cmc.framework.core.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateUniqueCode {
		
		private static final Logger logger = LoggerFactory.getLogger(GenerateUniqueCode.class);
		
		public static String getGeneratedCode(String formatFor,String formatname,Long seqNo){
				
				ResourceBundle rb = ResourceBundle.getBundle("messages");
				String format = rb.getString(formatFor);
				String generatedFormat = format;
				
				LocalDate parsedDate = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
				String text = parsedDate.format(formatter);
				LocalDate currentDate = LocalDate.parse(text, formatter);
				
				LocalTime parseTime= LocalTime.now();
				 String strDateFormat = "HHmm";
				 DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(strDateFormat);
				 String time = parseTime.format(timeFormatter);
				 LocalTime currentTime=LocalTime.parse(time, timeFormatter);
			
				 
				 
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM");
				
				if (generatedFormat.contains("%C")) {
						generatedFormat = generatedFormat.replaceAll("%C",formatname);
				}
				
			// 4 digit Year
				if (generatedFormat.contains("%Y")) {
					dateFormatter = DateTimeFormatter.ofPattern("yyyy");
					generatedFormat = generatedFormat.replaceAll("%Y", parsedDate.format(dateFormatter));
				}

				// 2 digit Year
				if (generatedFormat.contains("%y")) {
					dateFormatter = DateTimeFormatter.ofPattern("yy");
					generatedFormat = generatedFormat.replaceAll("%y", parsedDate.format(dateFormatter));
				}

				// 3 digit Month in characters
				if (generatedFormat.contains("%M")) {
					dateFormatter = DateTimeFormatter.ofPattern("MMM");
					generatedFormat = generatedFormat.replaceAll("%M", parsedDate.format(dateFormatter).toUpperCase());
				}

				// Numerical Month
				if (generatedFormat.contains("%m")) {
					dateFormatter = DateTimeFormatter.ofPattern("MM");
					generatedFormat = generatedFormat.replaceAll("%m", parsedDate.format(dateFormatter));
				}

				// numerical corresponding date
				if (generatedFormat.contains("%d")) {
					dateFormatter = DateTimeFormatter.ofPattern("dd");
					generatedFormat = generatedFormat.replaceAll("%d", parsedDate.format(dateFormatter));
				}
				
				// numerical corresponding time
				
				if (generatedFormat.contains("%t")) {
					generatedFormat = generatedFormat.replaceAll("%t", time);
				}
				
				
				int index = format.indexOf("%S");
				int length = format.length();
				String last = format.substring(index, length);
				
				if (generatedFormat.contains("%S")) {
						String formattedSeqNo = "";
						int indexFound = format.indexOf("%S") + 2;
						String digitCount = format.substring(indexFound);
						formattedSeqNo = String.format("%0" + digitCount + "d", seqNo);
						generatedFormat = generatedFormat.replaceAll(last, formattedSeqNo);
					}
				return generatedFormat;
		}
}
