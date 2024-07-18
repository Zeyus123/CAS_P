<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> 
<c:set var="userDetails" value="${serviceOutcome.data}"/>
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5>Role Access to Levels</h5>
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
	<h6 class="headingbg mb-4">Role Access to Levels</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>

	<form class="form-horizontal" action="${contextPath}/admin/role/roleLevelMap/addNupdate" method="POST" id="userForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="hidden" name="roleId" id="roleId" value="${roleData.roleId}"/>
		<div class="col-md-12" style="padding-top: 15px;margin-bottom:15px;">
		<label style=" font-size: 15px !important;">
			<b>Role :-</b>
			<span style="font-style: oblique; color: #712808;font-weight: bold;">${roleData.displayName}</span> 
	    </label>
			<div class="col-sm-12">
				<div>
					<table class="table table-bordered table-striped mb-none"  id="tableId1">
						<thead>
							<tr>
								<th width="65px;"><label><b>Sl No.</b></label></th>
								<th><label><b>Level Code</b></label></th>
								<th><label><b>Source Entity</b></label></th>
								<th><label><b>Display Entity</b></label></th>
								<th><label><b>Description</b></label></th>
								<th><label><b>Max Allocations</b></label></th>
								<th><label><b>Select / Unselect.</b></label></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${roleLevelList}" var="roleLevel" varStatus="index"> 
							<tr class="x-row">
								<td align="center">
									${index.count}
								</td>
								<td>
									${roleLevel.levelCode}
								</td>
								<td>
									${roleLevel.levelEntityName}
								</td>
								<td>
									${roleLevel.displayViewName}
								</td>
								<td>
									${roleLevel.displayName}
								</td>
								<td>
									<c:set var="hasAlloc" value="false"/> 
									<c:forEach items="${roleMapList}" var="mapEntry">
										<c:if test="${roleLevel.roleRightLevelId eq mapEntry.levelId}">
											<input type="number" class="form-control max-alloc" min="-1" name="maxAllocations" id="maxAllocations" value="${mapEntry.maxAllocations}" /> 
											<c:set var="hasAlloc" value="true"/> 
										</c:if>
									</c:forEach>
									<c:if test="${hasAlloc eq false }">
										<input type="number" class="form-control max-alloc" min="-1" name="maxAllocations" id="maxAllocations" value="-1" /> 
									</c:if>
								</td>
								<td>
									<c:set var="hasIt" value="false"/> 
									<c:forEach items="${roleMapList}" var="mapEntry">
										<c:if test="${roleLevel.roleRightLevelId eq mapEntry.levelId && mapEntry.isActive eq true }">
											<input type="checkbox" checked name="roleLevelId" id="roleLevelId" value="${roleLevel.roleRightLevelId}" /> 
											<c:set var="hasIt" value="true"/> 
										</c:if>
									</c:forEach>
									<c:if test="${hasIt eq false }">
										<input type="checkbox" name="roleLevelId" id="roleLevelId" value="${roleLevel.roleRightLevelId}" /> 
									</c:if>
								</td>
							</tr> 
							<c:set var="countRow" value="${index.count}"></c:set>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
		<div class="card-footer text-center mt-2">
			<input type="button" class="btn btn-success btn-sm" value="Submit" onclick="submitForm()">
			<a href="${contextPath}/admin/role/list" type="button" class="btn btn-warning btn-sm">Back</a>
		</div>
	</form>
	<h5>Max Allocations value of -1 means there is no limit to how many Entities can be assigned to this Role - level combination</h5>
</div>


<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/vendor/data_tables/datatables.min.css"/>
<script type="text/javascript" src="${contextPath}/resources/assets/vendor/data_tables/datatables.min.js"></script>

<script>
	$(document).ready(function(){
		var check = "${sentUserRoleData}";
		if(check =='departmentalUser'){
			$('#departmentalUserDiv').show();
		}
		
	});
	

	function submitForm(){
		
		var allRows = $('.x-row');
		var selectCheck=true;
		var fd = '';
		$.each(allRows, function(idx, elem){
			const elemMaxAllocations = $(elem).find('#maxAllocations')[0];
			const elemRoleLevelId = $(elem).find('#roleLevelId')[0];
			
			fd += 'maxAllocations=' + elemMaxAllocations.value + '&';
			fd += 'roleLevelId=' + elemRoleLevelId.value + '&';
			fd += 'status=' + (elemRoleLevelId.checked ? true : false) + '&';
			if(elemMaxAllocations.value != "-1"){
				if(elemRoleLevelId.checked == false){
					 bootbox.alert("Please Check Select Box Against Assigned Value");
					 selectCheck=false;
					 return false;
				}
			}

		});
		if(selectCheck == true){
			fd += 'roleId=' + $('#roleId').val() + '&';
			fd += '${_csrf.parameterName}' + '=' + '${_csrf.token}' ;

			var url = $('#userForm').attr('action');
			url = url + "?" +  fd;
			fetch(url, {
				method: 'POST'
			}).then(r => r.json())
			  .then(data => {
				  bootbox.alert(data.message);
			  });
		}
	}
	
	

	$(document).ready(function() {
		$('#ssodepid').DataTable();
	})
</script>
<style>
	.max-alloc{
		width: 120px !important;
	}
</style>

	
