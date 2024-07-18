package com.aashdit.prod.cmc.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.aashdit.prod.cmc.framework.core.util.RandomString;

/**
 * @author Soumyaranjan satapathy
 * @date 13.10.2020
 */

public class UploadFile {

	  // private static final String UPLOAD_DIRECTORY = "ouhm_uploaded_files";

    private final static Logger logger = Logger.getLogger(UploadFile.class);

//	/**
//	 * GET PATH TO STORE DOCUMENT
//	 **/
//	public static String getPathToStoreDocument(String module) {
//		String rootpath = System.getProperty("catalina.home") + File.separator + "webapps" + File.separator
//				+ UPLOAD_DIRECTORY;
//		// CREATE ROOT PATH FOLDER
//		File rootDir = new File(rootpath);
//		if (!rootDir.exists()) {
//			rootDir.mkdir();
//		}
//		// CREATE CHIELD FOLDER
//		String uploadPath = rootpath + File.separator + module;
//		File uploadDir = new File(uploadPath);
//		if (!uploadDir.exists()) {
//			uploadDir.mkdir();
//		}
//		return uploadPath;
//	}
    /**
     * STORE DOCUMENT ON FOLDER STRUCTURE
     */

//	@SuppressWarnings("resource")
//	public static String upload(String base64FileString,String path) throws IOException {
// 
//		BufferedOutputStream stream = null;
//		boolean bool = false;
//		//String uploadPath =getPathToStoreDocument(module);
//		String filePath = path + File.separator ;
//		File storeFile = new File(filePath);
//		bool = storeFile.exists();
//		if (bool == true) 
//		{
//			// check doc exits or not .if true than exist else false
//			return null;
//		}
//		String delims="_";
//	    String[] parts = base64FileString.split(delims);
//	    String imageString = parts[0];
//	    String extension = parts[1];
//	    byte[] imageByteArray = Base64.decodeBase64(imageString);
//	    String docName="";
//		
//	    filePath =storeFile.getAbsolutePath()  + "sample" +"."+extension;
//	    		
//		new FileOutputStream(filePath).write(imageByteArray);
//		
//		docName="sample" +"."+extension;
//		filePath=addCurrenDateTimeToDocAndRenameIt(docName,path,filePath,"OTHER");
//		
//		
//		return filePath;
//	}

    /**
     * STORE BENEFICIARY DOCUMENT ON FOLDER STRUCTURE
     *
     * @param base64File,path,uniqueCode
     * @author Somyaranjan
     */

    public static String uploadForHeads(String base64File, MultipartFile file, String path, String uniqueCode)
            throws IOException {

        String filePath = "";
        byte[] byteBase64 = null;
        String uniqFileName = null;
        Path uploadPath = null;
        String fileExtension = "";
        String returnData = "";
        try {
            if (file.getOriginalFilename() != null) {
                fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                Boolean fileSuspicious = null;
                if (fileExtension.equalsIgnoreCase("pdf")) {
                    fileSuspicious = new PdfDocumentDetectorImpl().isSafe(file.getInputStream());
                } else {
                    fileSuspicious = true;
                }
                if (fileSuspicious) {
//                    if (path.endsWith("ARTICLE-275" + File.separator)) {
//                        filePath = path + File.separator + uniqueCode;
//                    } else {
//                        filePath = path;
//                    }

                	filePath = path + File.separator + uniqueCode;
                	
                    if (Optional.ofNullable(base64File).isPresent()) {
                        String[] parts = base64File.split("_");
                        String imageString = parts[0];
                        String extension = parts[1];
                        byteBase64 = Base64.decodeBase64(imageString);
                        uniqFileName = addCurrenDateTimeToDocAndRenameItModified(uniqueCode, extension);
                    } else {
                        byteBase64 = file.getBytes();
                        uniqFileName = addCurrenDateTimeToDocAndRenameItModified(uniqueCode, FilenameUtils.getExtension(file.getOriginalFilename()));
                    }

                    File checkFolderPath = new File(filePath);
                    if (!checkFolderPath.exists()) {
                        checkFolderPath.mkdirs();
                    }

                    uploadPath = Paths.get(filePath.concat(File.separator + uniqFileName));
                    Files.write(uploadPath, byteBase64);
                    returnData = uploadPath.getFileName().toString();
                } else {
                    returnData = null;
                    throw new MyException("One or more suspicious files were found. Unable to save data.");
                }
            }
            return returnData;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return returnData;
        }
    }

    public static String saveUploadedMultipartFile(MultipartFile file, String fileLocation, String uniqueFileName) throws IOException {
        String fileName = null;
        String fileExtension = "";
        String returnData = "";
        Boolean fileSuspicious = false;
        try {
            if (!file.isEmpty()) {
                fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                if (fileExtension.equalsIgnoreCase("pdf")) {
                    fileSuspicious = new PdfDocumentDetectorImpl().isSafe(file.getInputStream());
                } else {
                    fileSuspicious = true;
                }

                if (fileSuspicious) {
                    fileName = uniqueFileName + "." + file.getOriginalFilename().split("\\.")[1];
                    byte[] bytes = file.getBytes();

                    File checkFolderPath = new File(fileLocation);
                    if (!checkFolderPath.exists()) {
                        checkFolderPath.mkdirs();
                    }

                    Path path = Paths.get(fileLocation + fileName);
                    Files.write(path, bytes);
                    returnData = fileName;
                } else {
                    returnData = null;
                    throw new MyException("One or more suspicious files were found. Unable to save data.");
                }
            }
        } catch (Exception ex) {
            throw new IOException(fileName + "COULD NOT UPLOAD IMAGE : saveUploadedMultipartFile :" + ex.getMessage());
        }
        return returnData;
    }

    /**
     * ADD CURRENT DATE TIME TO AVOID DUCUMENT NAME DUBLICACY
     */
    public static String addCurrenDateTimeToDocAndRenameItModified(String regCode, String extension)
            throws IOException {
        try {
            LocalDate dt = LocalDate.now();
            RandomString random = new RandomString(4, ThreadLocalRandom.current());
            LocalDateTime tm = LocalDateTime.now();
            Calendar cal = Calendar.getInstance();
            String meridiem = cal.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault());
            String currdt = "_" + dt.getMonth() + "_" + tm.getNano() + 1 + "_" + meridiem + "_" + random.nextString();
            String filePath = regCode + currdt + "." + extension;
            return filePath;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    public static String addCurrenDateTimeToDocAndRenameIt(String docname, String uploadPath, String filePath, String beneficiaryDoc) throws IOException {
        if (beneficiaryDoc.equals("BENEFICIARY")) {
            if (docname.contains(".")) {
                String[] data = docname.split("\\.");
                LocalDate dt = LocalDate.now();
                LocalDateTime tm = LocalDateTime.now();
                Calendar cal = Calendar.getInstance();

                String meridiem = cal.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault());
                String currdt = "_" + dt.getMonth() + "_" + tm.getNano() + 1 + "_" + meridiem;
                String filePathNew = uploadPath + data[0] + currdt + "." + data[1];

                filePath = renameFile(filePath, filePathNew);
            }
        } else {

            if (docname.contains(".")) {
                String[] data = docname.split("\\.");
                LocalDate dt = LocalDate.now();
                LocalTime tm = LocalTime.now();
                Calendar cal = Calendar.getInstance();

                String meridiem = cal.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault());
                String currdt = "_" + dt.getYear() + "_" + dt.getMonth() + "_" + dt.getDayOfMonth() + tm.getNano() + 1 + "_" + meridiem;
                String filePathNew = uploadPath + File.separator + "ouhm" + data[0] + currdt + "." + data[1];

                filePath = renameFile(filePath, filePathNew);
            }
        }

        return filePath;
    }

    public static String renameFile(String filePath, String filePathNew) throws IOException {
        File srcFile = new File(filePath);
        boolean bSucceeded = false;
        try {
            File destFile = new File(filePathNew);
            if (destFile.exists()) {
                if (!destFile.delete()) {
                    throw new IOException(filePath + " could not be renamed to " + filePathNew);
                }
            }
            if (!srcFile.renameTo(destFile)) {
                throw new IOException(filePath + " could not be renamed to " + filePathNew);
            } else {
                bSucceeded = true;
                if (bSucceeded) {
                    System.out.println(filePath + " is successfully renamed to " + filePathNew);
                    return filePathNew;
                }
            }
        } finally {
            if (bSucceeded) {
                // srcFile.delete();
            }
        }
        return null;
    }
    /**purpose : Used to fetch image from Database to HTML
     * @author Somyaranjan
     * @param image
     * @return image
     */
	public static String fetchImage(String image){
		Path file = Paths.get(image);	
		byte[] bytes;
		String data = "";
		try {
			bytes = Files.readAllBytes(file);
			data = java.util.Base64.getEncoder().encodeToString(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static String uploadHrms(MultipartFile file, String path, String module, String code) throws IOException {
		String uniqFileName = "";
		try {
			String filePath = path + File.separator + module;
			if(code.equals("") || code.isEmpty()) {
				filePath = filePath + File.separator + code;
			}
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String fname = addCurrenDateTimeToDocAndRenameItModified(code,extension);
			File checkFolderPath = new File(filePath);
			if (!checkFolderPath.exists()) {
				checkFolderPath.mkdirs();
			}
			Path uploadPath = Paths.get(filePath.concat(File.separator + fname));
			Files.write(uploadPath, file.getBytes());
			uniqFileName = fname;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return uniqFileName;
	}
	
}
