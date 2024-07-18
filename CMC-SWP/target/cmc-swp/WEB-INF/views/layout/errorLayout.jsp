<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html class="fixed">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>CMC</title>
	<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
	<meta name="keywords" content="CMC-SWP" />
	<meta name="description" content="CMC-SWP">
	<meta name="author" content="Aashdit Technologies">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.5.1.min.js"></script>
	<style type="text/css">
		*{
			padding: 0px;
			margin: 0px;
		}
		body{
			background: #e8f1fa;
		}
		main{
			width: 100%;
			padding: 0px;
			margin: 0px;
		}
		.main .content{
			margin: 100px auto;
    		width: 60%;
    		display: block;
		}
		.main .content .content-left{
			width: 50%;
			float: left;
			text-align: center;
			font-size: 20px;
			margin-top: 10%;
		}
		.main .content .content-right{
			width: 50%;
			float: left;
			margin-top: 10%;
		}
	</style>
	<script type="text/javascript">
			$(function(){
				//Nav-Active
				var url=window.location.href;$(".nav-main a").each(function(){url==this.href&&($(this).parent().addClass("nav-active"),$(this).parent().parent().addClass("nav-expanded nav-active"),$(this).parent().parent().parent().addClass("nav-expanded nav-active"),$(this).parent().parent().parent().parent().addClass("nav-active"),$(this).parent().parent().parent().parent().parent().addClass("nav-active"),$(this).parent().parent().parent().parent().parent().parent().addClass("nav-active"),$(this).parent().parent().parent().parent().parent().parent().parent().addClass("nav-active")),$(".nav-main").removeClass("nav-expanded")});
			
				$(this).bind("contextmenu", function(e) {
			    	e.preventDefault();
			    	}); 
			    $(this).bind('cut copy paste', function (e) {
			        e.preventDefault();
			    	});
			    	
			    	$('form').attr("autocomplete", "off");
			});
	</script>
</head>
<body>
	<sec:authentication var="principal" property="principal" />
		<div class="main">
			<div class="content">
				<div class="content-left">
					<div>
						<tiles:insertAttribute name="body" />
					</div>
					<div style="margin-top: 50px;"><a href="${pageContext.request.contextPath}/home" class="btn btn-sm btn-primary">Back to Home</a></div>
				</div>
				<div class="content-right">
					<img src="${pageContext.request.contextPath}/assets/img/servererorr.png" style="width: 50%;float: right;">
				</div>
			</div>
		</div>
</body>
</html>