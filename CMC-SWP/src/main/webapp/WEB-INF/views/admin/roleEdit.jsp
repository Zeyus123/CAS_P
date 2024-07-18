<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> 
<c:set var="userDetails" value="${serviceOutcome.data}"/> 
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5><spring:message code="site.admin.mst.role.edit"></spring:message></h5>
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
	<h6 class="headingbg mb-4"><spring:message code="site.admin.mst.role.details"></spring:message></h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>
	<form class="form-horizontal" action="${contextPath}/admin/role/addNupdate" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="roleId" value="${userDetails.roleId}"/>
		<div class="row">
		<%-- <div class="form-group col-md-2">
				<label class="col-md-12 required" for="complaintName"><spring:message code="site.admin.mst.role.name"></spring:message></label>
				<div class="col-md-12">
					<input type="text" id="roleName" name="roleName" value="${userDetails.roleName}" class="form-control form-control-sm" required="required">
				</div>
			</div> --%>
			<div class="form-group col-md-3">
				<label class="required"><spring:message code="site.admin.mst.role.code"></spring:message></label>
				<div class="col-md-12">
					<input type="text" id="roleCode" name="roleCode" value="${userDetails.roleCode}" class="form-control form-control-sm" maxlength="10" required="required" readonly="readonly">
				</div>
			</div>
			<div class="form-group col-md-2">
				<label class="required"><spring:message code="site.admin.mst.role.display.name"></spring:message></label>
				<div class="col-md-12">
					<input type="text" id="displayName" name="displayName" value="${userDetails.displayName}"  maxlength="40" class="form-control form-control-sm" required="required">
				</div>
			</div>
			<div class="form-group col-md-2">
				<label class="required"><spring:message code="site.admin.mst.role.description"></spring:message></label>
				<div class="col-md-12">
					<input type="text" name="description" id="description" class="form-control form-control-sm"   maxlength="40" value="${userDetails.description}" required="required">  
				</div>
			</div>
			<div class="form-group col-md-2">
				<label class="required"><spring:message code="site.admin.mst.role.maxassignments"></spring:message></label>
				<div class="col-md-12">
					<input type="number" min="-1" name="maxAssignments" id="maxAssignments" class="form-control form-control-sm"  maxlength="20" value="${userDetails.maxAssignments}" required="required">  
				</div>
			</div>
		</div>
		<div class="form-group col-md-12 text-center mt-2">
			<input type="submit" value="<spring:message code="site.common.submit"></spring:message>" class="btn btn-success btn-sm">
			<a href="${contextPath}/admin/role/list" type="button" class="btn btn-warning btn-sm"><spring:message code="site.common.back"></spring:message></a>
		</div>
	</form>
</div>



			
<script>
	$(document).ready(function() {
		$('#basic-datatables').DataTable({
		});
	});
</script>
	
