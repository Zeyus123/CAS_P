package com.aashdit.prod.cmc.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aashdit.prod.cmc.utils.DownloadFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/document")
public class ImageViewController {

	ResourceBundle rb = ResourceBundle.getBundle("application-dev"); 
	
	@GetMapping(value="/viewDocuments", name="Download Document")
	public void downloadDocuments(Model model, String moduleName,String filePath,HttpServletResponse response,HttpServletRequest request) {
		try{
			String path = rb.getString("UPLOAD.FILE.PATH")+File.separator+moduleName+File.separator+filePath;
			Path file = Paths.get(path);
			DownloadFile.viewUploadedDocument(file, request, response);
		 }catch(Exception e){
			 log.error("Exception occured in viewDocuments method in ImageViewController where moduleName is ->"+moduleName+" and filePath is->"+filePath+" and exception message is -->"+e.getMessage());
		 }
	}
	
	@ResponseBody
	@GetMapping(value="/api/allowAll/image/viewDocuments", name="Download Document")
	public void apiDownloadDocuments(Model model, String moduleName,String filePath,HttpServletResponse response,HttpServletRequest request) {
		try{
			String path = rb.getString("DIUP.FILE.PATH")+File.separator+moduleName+File.separator+filePath;
			Path file = Paths.get(path);
			DownloadFile.viewUploadedDocument(file, request, response);
		 }catch(Exception e){
			 log.error("Exception occured in apiDownloadDocuments method in ImageViewController where moduleName is ->"+moduleName+" and filePath is->"+filePath+" and exception message is -->"+e.getMessage());
		 }
	}
	
	@GetMapping(value="/nodalViewDocuments", name="Download Document")
	public void downloadNADocuments(Model model, String moduleName,String filePath,HttpServletResponse response,HttpServletRequest request) {
		try{
			String path = rb.getString("UPLOAD.FILE.PATH")+File.separator+moduleName+File.separator+filePath;
			Path file = Paths.get(path);
			DownloadFile.viewUploadedDocument(file, request, response);
		 }catch(Exception e){
			 log.error("Exception occured in viewDocuments method in ImageViewController where moduleName is ->"+moduleName+" and filePath is->"+filePath+" and exception message is -->"+e.getMessage());
		 }
	}
	
}
