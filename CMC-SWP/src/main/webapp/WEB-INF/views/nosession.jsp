<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html class="fixed sidebar-left-collapsed">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>CMC-SWP</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
	<meta name="keywords" content="CMC-SWP" />
	<meta name="description" content="CMC-SWP">
	<meta name="author" content="Aashdit Technologies">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<link rel="shortcut icon" href="${contextPath}/loginPage/images/favicon.png">
		

<link rel="stylesheet" href="${contextPath}/loginPage/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/loginPage/css/login.css">
</head>
<body>
	<section class="login_content">
		<div id="video"></div>
		<div class="container">
			<div class="TextContainer lc_left">
				<div class="app_tittle">
					<img src="${contextPath}/loginPage/images/logo.png">
					<p><i>CMC-SWP</i> <b></b>
					</p>
				</div>
			</div>
			<div class="lc_right">
				<div class="lc_right_con">
					<h2>Your session has expired</h2>
				</div>
			</div>
		</div>
		<div class="col-md-12 text-center" style="margin-top: 20px;"></div>
	</section>
	<script src="${contextPath}/loginPage/js/jquery.js"></script>
	<script src="${contextPath}/loginPage/js/bootstrap.min.js"></script>
</body>
</html>