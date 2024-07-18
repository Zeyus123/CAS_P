<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Support</title>
	<style type="text/css" media="screen">
	body {
		overflow: hidden;
	}
	</style>
	<link rel="shortcut icon" href="${contextPath}/assets/img/favicon.png">
	<link rel="stylesheet" href="${contextPath}/assets/css/bootstrap.min.css">
	<link rel="shortcut icon" href="${contextPath}/assets/img/favicon.png">
	<script src="${contextPath}/assets/js/core/jquery-3.5.1.min.js"></script>
	<script src="${contextPath}/assets/js/core/popper.min.js"></script>
	<script src="${contextPath}/assets/js/core/bootstrap.min.js"></script>
	<script src="${contextPath}/assets/vendor/bootbox/bootbox.min.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/stageConfig/stageRawPage.jsp"%>
	<tiles:insertAttribute name="body" />
	
	<script type="text/javascript">
	</script>
	
</body>

</html>
