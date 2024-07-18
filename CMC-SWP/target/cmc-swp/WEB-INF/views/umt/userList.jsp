<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> 
<script src="${contextPath}/assets/appJs/framework/data-table.js" type="text/javascript"></script>
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5>User List</h5>
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
	<h6 class="headingbg mb-4">User Details List</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>
	<div class="col-md-12">
		<!--  BEG Custom Dt Header -->
		<div class="dt-header">
			<div class="dt-header-left">
				Show <select class="dt-control" id="pageSize">
					<option value="10" ${size eq 10 ? 'selected' : '' }>10</option>
					<option value="25" ${size eq 25 ? 'selected' : '' }>25</option>
					<option value="50" ${size eq 50 ? 'selected' : '' }>50</option>
					<option value="100" ${size eq 100 ? 'selected' : '' }>100</option>
				</select> entries
			</div>
			<div class="dt-header-right">
				Search :<input type="text" class="dt-control" id="searchTerm"
					name="searchTerm" value="${searchTerm}" />
			</div>
		</div>
		<!-- END Custom Dt Header -->
		<table id="tblUser"
			class="display table table-bordered table-hover">
			<thead>
				<tr>
					<th>Sl no.</th>
					<th><spring:message code="site.admin.user.name"></spring:message></th>
					<th><spring:message code="site.admin.user.id"></spring:message></th>
					<th><spring:message code="site.common.mobile.number"></spring:message></th>
					<th><spring:message code="site.common.email"></spring:message></th>
					<th>Created On</th>
					<th style="width: 165px;"><spring:message code="site.common.action"></spring:message></th>
				</tr>
			</thead>
			<tbody id="tbd">
				<c:forEach items="${userList.content}" var="user"
					varStatus="userCount">
					<tr>
						<td>${userCount.count + size * userList.number }</td>
						<td>${user.firstName} ${user.lastName}</td>
						<td>${user.userName}</td>
						<td>${user.mobile}</td>
						<td>${user.email}</td>
						<td><fmt:formatDate value="${user.createdOn}" pattern="dd/MMM/yyyy"/></td>
						<td><c:choose>
								<c:when test="${user.primaryRole.roleCode eq 'PUB'}">
									<button class="btn btn-warning btn-sm"
										onclick="editApplicationById('${user.userId}')"
										disabled="disabled" title="Edit User">
										<i class="fa fa-edit"></i>
									</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-warning btn-sm"
										onclick="editApplicationById('${user.userId}')"
										title="Edit User">
										<i class="fa fa-edit"></i>
									</button>
								</c:otherwise>
							</c:choose>
							<button class="btn btn-primary btn-sm"
								onclick="viewApplicationById('${user.userId}')"
								title="View User">
								<i class="fa fa-eye"></i>
							</button> <c:if test="${user.isActive eq true}">
								<button class="btn btn-danger btn-sm"
									onclick="lockUser('${user.userId}')" title="Lock User">
									<i class="fa fa-lock" aria-hidden="true"></i>
								</button>
							</c:if> <c:if test="${user.isActive eq false}">
								<button class="btn btn-success btn-sm"
									onclick="unlockUser('${user.userId}')" title="Unlock User">
									<i class="fa fa-unlock-alt" aria-hidden="true"></i>
								</button>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!--  BEG Custom Dt Footer -->
		<div class="dt-footer">
			<div class="dt-footer-left">
				Showing page <span id="dt-counter">${userList.number + 1}
					of ${userList.totalPages}</span> pages
			</div>
			<div class="dt-footer-right">
				<a href="#" id="dt-start" class="dt-navigate">First</a> <a
					href="#" id="dt-previous" class="dt-navigate">Previous</a> <a
					href="#" id="dt-next" class="dt-navigate">Next</a> <a href="#"
					id="dt-end" class="dt-navigate">Last</a>
			</div>
		</div>
		<!-- END Custom Dt Header -->
	</div>
</div>
		  <!-- Modal End -->
<form  method="GET" id="formId">
</form>
<form action="${contextPath}/umt/user/isActive" method="POST" id="lockNunlockForm">
	<input type="hidden" name="userId" id="userId" />
	<input type="hidden" name="roleId" id="roleId" />
	<input type="hidden" name="isActive" id="isActive" /> 
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

	<!-- Atlantis JS -->
<!-- 	<script src="../assets/js/atlantis.min.js"></script>
 -->	<script >
function editApplicationById(id){
		    $("#formId").attr('action','${contextPath}/umt/user/edit/'+ id +'');
		    $("#formId").submit();
}

function viewApplicationById(userId){
	         $("#formId").attr('action','${contextPath}/umt/user/view/'+ userId +'');
			    $("#formId").submit();
 }

function unlockUser(userId){
	 $("#userId").val(userId);
	 $("#isActive").val(true);
	 //$("#roleId").val($("#role").val());
	    bootbox.confirm("Do you want to unlock this user?",
	            function(result) {
	                    if (result == true) {
	                            $("#lockNunlockForm").submit();
	                    }
	            });
		 }
function lockUser(userId){
	 $("#userId").val(userId);
	 $("#isActive").val(false);
	 //$("#roleId").val($("#role").val());
	    bootbox.confirm("Do you want to lock this user?",
	            function(result) {
	                    if (result == true) {
	                            $("#lockNunlockForm").submit();
	                    }
	            });
		 }	

 $().ready(function(){
	 const dtTable = new svrDatatable('${contextPath}/umt/user/list', {
		 pageSize : $("#pageSize").val(),
		 csrf : '${_csrf.token}',
		 currentPage : ${userList.number},
		 totalPages : ${userList.totalPages},
	 });
 });
</script>

