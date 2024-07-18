package com.aashdit.prod.cmc.controller;

import java.net.InetAddress;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AppErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/unauthorizedError")
	public String unauthorizedError(HttpServletRequest request, Model model) {

		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			String localHostName = localAddress.getHostName();
			String localHostAddress = localAddress.getHostAddress();

			log.info("localHostName : " + localHostName);
			log.info("localHostAddress : " + localHostAddress);
			model.addAttribute("localHostName", localHostName);
			model.addAttribute("localHostAddress", localHostAddress);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "site.unauthorizedError";
	}

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.OK.value()) {
			}
			if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
				return "site.400";
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				return "site.400";
			} else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
				return "site.401";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "site.accessdenied";
			} else if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "site.404";
			} else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
				return "site.405";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "site.500";
			} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				return "site.503";
			} else {
				return "site.500";
			}
		} else {
			return "site.500";
		}

	}

}