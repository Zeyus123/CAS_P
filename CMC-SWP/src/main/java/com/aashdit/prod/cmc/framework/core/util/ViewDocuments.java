package com.aashdit.prod.cmc.framework.core.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

public class ViewDocuments {

	private static final Logger log = Logger.getLogger(ViewDocuments.class);
	
	public static void viewUploadedDocument(String path, HttpServletRequest request, HttpServletResponse response) {

		try {
			Path file = Paths.get(path);
			String mime = Files.probeContentType((java.nio.file.Path) file);
			response.setContentType(mime);
			response.addHeader("Content-Disposition", "inline; filename=");
			
			Files.copy(file, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception ioe) {
			//ioe.printStackTrace();
			log.error("Could not locate file " + path);
			return;
		}

	}

}
	