<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> 
<c:set var="userDetails" value="${serviceOutcome.data}"/>
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5>View Role</h5>
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
	<h6 class="headingbg mb-4">Role Details</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>

	<form class="form-horizontal" action="${contextPath}/core/role/addNupdate" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="roleId" value="${userDetails.roleId}"/>
		<div class="row">
		<%-- <div class="form-group col-md-2">
				<label class="col-md-12 required" for="complaintName">Role Name</label>
				<div class="col-md-12">
					<input type="text" id="roleName" name="roleName" value="${userDetails.roleName}" class="form-control form-control-sm" required="required" readonly="readonly">
				</div>
			</div> --%>
				<div class="form-group col-md-2">
					<label class="required"><spring:message code="site.admin.mst.role.code"></spring:message></label>
					<div class="col-md-12">
						<input type="text" id="roleCode" name="roleCode" value="${userDetails.roleCode}" class="form-control form-control-sm" maxlength="3" required="required" readonly="readonly">
					</div>
				</div>
				<div class="form-group col-md-2">
					<label class="required"><spring:message code="site.admin.mst.role.display.name"></spring:message></label>
					<div class="col-md-12">
						<input type="text" id="displayName" name="displayName" value="${userDetails.displayName}" class="form-control form-control-sm" required="required" readonly="readonly">
					</div>
				</div>
				<div class="form-group col-md-2">
					<label class="required"><spring:message code="site.admin.mst.role.description"></spring:message></label>
					<div class="col-md-12">
						<input type="text" name="description" id="description" class="form-control form-control-sm"  value="${userDetails.description}" required="required" readonly="readonly">  
					</div>
				</div>
				<div class="form-group col-md-2">
					<label class="required"><spring:message code="site.admin.mst.role.maxassignments"></spring:message></label>
					<div class="col-md-12">
						<input type="number" name="description" id="description" class="form-control form-control-sm"  value="${userDetails.maxAssignments}" required="required" readonly="readonly">  
					</div>
				</div>
			</div>
		<div class="form-group col-md-12 text-center mt-2">
			<a href="${contextPath}/admin/role/list" class="btn btn-warning btn-sm">Back</a>
		</div>
	</form>
</div>


<script>
	$(document).ready(function() {
		$('#basic-datatables').DataTable({
		});
	});
</script>
	
