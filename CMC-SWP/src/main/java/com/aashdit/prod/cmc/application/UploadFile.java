package com.aashdit.prod.cmc.application;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.web.multipart.MultipartFile;

import com.aashdit.prod.cmc.framework.core.util.RandomString;

public class UploadFile {
	
		static ResourceBundle rb = ResourceBundle.getBundle("application");
		private static final String UPLOAD_DIRECTORY = rb.getString("UPLOAD.FILE.PATH");
		/**
		 * GET PATH TO STORE DOCUMENT
		 **/
		public static String getPathToStoreDocument(String module){	
			//String rootpath= System.getProperty("catalina.home") + File.separator + "webapps" + File.separator + UPLOAD_DIRECTORY;
			
			String rootpath = UPLOAD_DIRECTORY;
			//CREATE ROOT PATH FOLDER
			File rootDir = new File(rootpath);
				if (!rootDir.exists()) {
					rootDir.mkdir();
				}
			//CREATE CHILD FOLDER
			String uploadPath = rootpath+File.separator+module;
			File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}
			 return uploadPath;
		}
		
		public static String upload(MultipartFile multipartFile,String module,String extension) throws IOException {
				try{
						BufferedOutputStream stream = null;
						String uploadPath =getPathToStoreDocument(module);
						RandomString rd = new RandomString(15);
						//
						String newFileName = rd.nextString() + extension;
						
						String filePath = uploadPath + File.separator + newFileName;
						File storeFile = new File(filePath);
						byte[] bytes = multipartFile.getBytes();

						// saves the file on disk
						stream = new BufferedOutputStream(new FileOutputStream(storeFile));
						stream.write(bytes);
						stream.close();
						String docname = newFileName;
						filePath=addCurrenDateTimeToDocAndRenameIt(docname,uploadPath,filePath);
						return filePath;
				}catch(Exception ex){
						return "";
				}
			}
		
		
		/**
		 * ADD CURRENT DATE TIME TO AVOID DUCUMENT NAME DUBLICACY
		 */
		public static  String addCurrenDateTimeToDocAndRenameIt(String docname,String uploadPath,String filePath) throws IOException {
			
			if(docname.contains("."))
			{				 
				String data[]=docname.split("\\.");				
				Date dt=new Date();
				Calendar cal = Calendar.getInstance();				
				
				String meridiem = cal.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault());				 
				String currdt="_"+dt.getYear()+"_"+dt.getMonth()+"_"+dt.getDay()+"_"+meridiem;
				String filePathNew = uploadPath + File.separator +"kym_"+data[0]+currdt+"."+data[1];
				
				filePath=renameFile(filePath,filePathNew);
			}
			return filePath;
		}
	   public static  String newChangeFileName(String docname) throws IOException {
			
		   String newName="";
			if(docname.contains("."))
			{				 
				String data[]=docname.split("\\.");				
				Date dt=new Date();
				Calendar cal = Calendar.getInstance();				
				
				String meridiem = cal.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault());				 
				String currdt="_"+dt.getYear()+"_"+dt.getMonth()+"_"+dt.getDay()+"_"+meridiem;
				newName ="pms_"+data[0]+currdt+"."+data[1];
				
				
			}
			return newName;
		}
		
		/**
		 * RENAME DOCUMENT NAME
		 */
		public static String renameFile(String filePath, String filePathNew) throws IOException 
		{
			File srcFile = new File(filePath);
			boolean bSucceeded = false;
			try 
			{
				File destFile = new File(filePathNew);
				if (destFile.exists()) 
				{
					if (!destFile.delete()) 
					{
						throw new IOException(filePath + " could not be renamed to " + filePathNew);
					}
				}
				if (!srcFile.renameTo(destFile)) 
				{
					throw new IOException(filePath + " could not be renamed to " + filePathNew);
				} 
				else 
				{
					bSucceeded = true;
					if (bSucceeded == true) 
					{
						return filePathNew;
					}
				}
			} 
			finally 
			{
				if (bSucceeded) 
				{
					// srcFile.delete();
				}
			}
			return null;
		}
		
		 public static void copyFile(InputStream in, OutputStream out) throws IOException
	     {
	         byte[] buffer = new byte[1024];
	         int read;
	         while ((read = in.read(buffer)) != -1)
	         {
	             out.write(buffer, 0, read);
	         }
	     }
	}



