<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>   
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html class="fixed">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<title>CMC-SWP</title>
		<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
		<meta name="keywords" content="" />
		<meta name="description" content="">
		<meta name="author" content="Aashdit Technologies">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
		
		<meta http-equiv="cache-control" content="max-age=0" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="cache-control" content="no-store" />
		<meta http-equiv="cache-control" content="pre-check=0" />
		<meta http-equiv="cache-control" content="post-check=0" />
		<meta http-equiv="cache-control" content="must-revalidate" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="pragma" content="no-cache" />
	<link rel="shortcut icon" href="${contextPath}/assets/img/favicon.png">
	
	<!-- Fonts and icons -->
	<script src="${contextPath}/assets/js/plugin/webfont/webfont.min.js"></script>
	<script>
		WebFont.load({
			google: {"families":["Lato:300,400,700,900"]},
			custom: {"families":["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands", "simple-line-icons"], urls: ['${contextPath}/assets/css/fonts.min.css']},
			active: function() {
				sessionStorage.fonts = true;
			}
		});
	</script>
	
	<!-- New CSS Files -->
	<link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="${contextPath}/assets/css/atlantis.css">
	<link rel="stylesheet" href="${contextPath}/assets/css/theme_custom.css">
	<link rel="stylesheet" href="${contextPath}/assets/css/default.css">
	<link href="${contextPath}/assets/jquery_datepicker/jquery.datepick.css" rel="stylesheet">
	<!-- end New CSS -->
	
	<link rel="shortcut icon" href="${contextPath}/assets/img/favicon.png">
	<script src="${contextPath}/assets/js/core/jquery-3.5.1.min.js"></script>
	<!-- Fonts and icons -->
	<script src="${contextPath}/assets/js/plugin/webfont/webfont.min.js"></script>
	<script>
		WebFont.load({
			google: {"families":["Lato:300,400,700,900"]},
			custom: {"families":["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands", "simple-line-icons"], urls: ['${contextPath}/assets/css/fonts.min.css']},
			active: function() {
				sessionStorage.fonts = true;
			}
		});
	</script>
    
	<!-- CSS Files -->
	<link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
	<link rel="stylesheet" href="${contextPath}/assets/select2/select2.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/assets/logout/jquery-confirm.css"/>
	</head>
	<body class="defaultbase_body">
		<section class="body">
			<tiles:insertAttribute name="header" />
			<div class="inner-wrapper">
				<%-- <tiles:insertAttribute name="menu" /> --%>
				<section role="main" class="main-panel">
				<tiles:insertAttribute name="body" >	
				 <%@ include file="/WEB-INF/views/message.jsp"%>
                </tiles:insertAttribute>
					<tiles:insertAttribute name="footer" />
				</section>
			</div>
		</section>
	</body>
	
	<!--   Core JS Files   -->

	<script src="${contextPath}/assets/js/core/popper.min.js"></script>
	<script src="${contextPath}/assets/js/core/bootstrap.min.js"></script>
	<script src="${contextPath}/assets/select2/select2.js"></script>

	<!-- jQuery UI -->
	<script src="${contextPath}/assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script src="${contextPath}/assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>
	<script src="${contextPath}/assets/js/plugin/datatables/datatables.min.js"></script>

	<!-- jQuery Scrollbar -->
	<script src="${contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
	<script src="${contextPath}/assets/jquery_datepicker/jquery.plugin.js"></script>
	<script src="${contextPath}/assets/jquery_datepicker/jquery.datepick.js"></script>
	 <script>
   $(function() {
      $('.datepicker_con>input').datepick({onShow: $.datepick.monthOnly, dateFormat: 'dd/mm/yyyy',yearRange: 'c-100:c+5', showOnFocus: true,
    showTrigger: '<button type="button" class="trigger">' +
    '<i class="fa fa-calendar"></i></button>'});
    }); 
  </script>
  <script src="${contextPath}/assets/appJs/validation/common-utils.js"></script>  
	<!-- Atlantis JS -->
	<script src="${contextPath}/assets/js/atlantis.min.js"></script>  
	<script src="${contextPath}/assets/bootbox/bootbox.min.js"></script>
	<script src="${contextPath}/assets/appJs/framework/languageSwitcher.js"></script>
	<script src="${contextPath}/assets/js/required-message.js"></script>
	<script src="${contextPath}/assets/logout/jquery-confirm.js"></script>

<script type="text/javascript">
  var inputRequiredMessage="<spring:message code="site.common.input.required"></spring:message>";
  var selectRequiredMessage="<spring:message code="site.common.select.required"></spring:message>";
  var invalidMobileMessage="<spring:message code="site.common.msg.invalid.mobile"></spring:message>";
  var invalidEmailMessage="<spring:message code="site.common.msg.invalid.email"></spring:message>";
  localStorage.setItem("selectRequiredMsg", selectRequiredMessage); 
  localStorage.setItem("inputRequiredMsg", inputRequiredMessage);
  localStorage.setItem("invalidMobileMsg", invalidMobileMessage); 
  localStorage.setItem("invalidEmailMsg", invalidEmailMessage); 
</script>
</html>
	