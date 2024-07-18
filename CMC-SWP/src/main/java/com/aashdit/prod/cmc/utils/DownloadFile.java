 package com.aashdit.prod.cmc.utils;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFile {

	public static void viewUploadedDocument(Path file, HttpServletRequest request, HttpServletResponse response) {

		try {
			String mime = Files.probeContentType((java.nio.file.Path) file);
			if (mime.equals("application/pdf")) {
				response.setContentType("application/pdf");
			} else if (mime.equals("application/vnd.ms-excel")) { // .xls
				response.setContentType("application/vnd.ms-excel");
			} else if (mime.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) { // .xlsx
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			} else if (mime.equals("application/vnd.ms-powerpoint")) { // .ppt
				response.setContentType("application/vnd.ms-powerpoint");
			} else if (mime.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) { // .pptx
				response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
			} else if (mime.equals("application/msword")) { // .doc
				response.setContentType("application/msword");
			} else if (mime.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) { // .docx
				response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			} else if (mime.equals("application/plain")) {
				response.setContentType("application/plain");
			} else if (mime.equals("image/jpg")) {
				response.setContentType("image/jpg");
			} else if (mime.equals("image/JPG")) {
				response.setContentType("image/JPG");
			} else if (mime.equals("image/jpeg")) {
				response.setContentType("image/jpeg");
			} else if (mime.equals("image/png")) {
				response.setContentType("image/png");
			} else if (mime.equals("image/PNG")) {
				response.setContentType("image/PNG");
			} else if (mime.equals("image/gif")) {
				response.setContentType("image/gif");
			} else if (mime.equals("image/GIF")) {
				response.setContentType("image/GIF");
			} else if (mime.equals("image/bmp")) {
				response.setContentType("image/bmp");
			} else if (mime.equals("application/octet-stream")) {
				response.setContentType("application/octet-stream");
			} else {
				response.setContentType("application/octet-stream");
			}
			response.addHeader("Content-Disposition", "inline; filename=");
			Files.copy(file, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return;
		}

	}
}
