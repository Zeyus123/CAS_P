<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5>Login History</h5>
		</div>
		<div class="col-md-6">
		    <nav aria-label="breadcrumb" style="float: right;">
	            <ol class="breadcrumb">
	                <li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
	                <li class="breadcrumb-item active" aria-current="page">User Management</li>
	            </ol>
	        </nav>
	    </div>
	</div>
	<hr style="margin-top: 5px;" />
	<h6 class="headingbg mb-4">Login History</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>
	<div class="table-responsive">
		<table id="basic-datatables" class="display table table-bordered table-hover DataTable" >
			<thead>
				<tr>
				    <th>Sl.No</th>
				    <th>User Name</th>
					<th>Email Id</th>
					<th>Login Type</th>
					<th>IP Address</th>
					<th>Operating System</th>
					<th>Browses Details</th>
					<th>Login Status</th>
					<th>Date</th>
				</tr>
			</thead>
			<tbody id="tbd">
		 	   <c:forEach items="${userHistoryList}" var="userHistory" varStatus="count"> 
					<tr>
					    <td> ${count.count} </td>
					    <td>${userHistory.userName}</td>
						<td>${userHistory.email}</td>
						<td>${userHistory.loginType}</td>
						<td>${userHistory.ipAddress}</td>
					    <td>${userHistory.osDetails}</td>
						<td>${userHistory.browserDetails}</td>
						<td>${userHistory.loginStatus}</td>
						<td><fmt:formatDate pattern = "dd-MM-yyyy hh:mm:ss" value = "${userHistory.lastUpdatedOn}" /></td>
					</tr>
		     </c:forEach> 
			</tbody>
		</table>
	</div>
</div>


<!-- Atlantis JS -->
<script src="../assets/js/atlantis.min.js"></script>
