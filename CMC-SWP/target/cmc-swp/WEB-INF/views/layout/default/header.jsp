<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<!-- header -->
	<div class="main-header">
			<!-- Navbar Header -->
			<nav class="navbar navbar-header navbar-expand-lg outer-navbar" data-background-color="blue2">
				
				<div class="container-fluid">
					<div class="registerlogo">
						<a href="stateUser_dashboard.html" class="logo">
							<img src="${contextPath}/assets/img/logo.png" alt="navbar brand" class="navbar-brand">
						</a>
					</div>
					<div class="nav-item nav-text">
						<h1 >Single Window Portal</h1>
						<b >Directorate of Industries</b>
					</div>
					<ul class="navbar-nav topbar-nav ml-md-auto align-items-center">
					<!-- BEG Lang Changer -->
						<li class="nav-item">
							<select class="form-control lng-select" name="lang" id="siteLangSelector">
								<option value="en" ${lang eq 'en' ? 'selected' :'' }>English</option>
								<option value="hi_IN" ${lang eq 'hi-IN' ? 'selected' :'' }>हिंदी</option>
							</select>
						</li>
						<li class="nav-item">
							<button class="btn btn-danger btn-sm logout" style="margin-left: 20px;">Logout</button>
						</li>
					</ul>
					
				</div>
			</nav>
			<!-- End Navbar -->
	</div>
	<!-- Header End -->
	
	<script>
			$(function(){
				
				$('#siteLangSelector').change(function(){
					
					const lang = $(this).val();
					switchLanguage(lang);
				});
			});
			
			   $('.logout').on('click', function(){
			       $.confirm({
			           title: 'Logout?',
			           type: 'red',
			           content: 'Your time is out, you will be automatically logged out in 10 seconds.'+
			           '<form action="${contextPath}/logout" method="get" class="logoutForm">' +
			           '</form>',
			           autoClose: 'logoutUser|10000',
			           buttons: {
			               logoutUser: {
			                   text: 'logout myself',
			                   btnClass: 'btn-red',
			                   action: function(){
			                       $(".logoutForm").submit();
			                   }
			               },
			             
			               cancel: function(){
			                   $.alert('canceled');
			               }
			           }
			       });
			   }); 
		</script>