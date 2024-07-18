<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authentication var="principal" property="principal" />
<c:set var="roleCode" value="${principal.primaryRole.roleCode}"/>


<div class="card">
	<div class="card-header">
		<h6 class="card-title">Staff Workflow Process List</h6>
	</div>
	<div class="card-body">
		<div class="table-rrsponsive">
			<table class="datatable table table-striped table-bordered exportbtn" id="tableId1">
				<thead>
					<tr>
						<th style="width: 05%;">Sl No</th>
							  <c:choose>
					    <c:when test="${principal.dbUser.userLevel eq 'COLG'}">
					        <th style="width: 20%;">College Name</th>
					    </c:when>
					     <c:when test="${principal.dbUser.userLevel eq 'DEPT'}">
					        <th style="width: 20%;">Hed Dept. Name</th>
					    </c:when>
					      <c:when test="${principal.dbUser.userLevel eq 'UNV'}">
					        <th style="width: 20%;">Unv Name</th>
					    </c:when>
					    <c:otherwise> <th style="width: 20%;">College Name</th></c:otherwise>
					</c:choose>
					
					
						
						<th style="width: 20%;">Staff Name</th>
						<th style="width: 48%;">Actions for which Workflow Defined</th>
						<th style="width: 07%; text-align: center;">Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${empty staffWorkflowFetchData ? staffWorkflowData.staffWorkFlowMasterList : staffWorkflowFetchData.staffWorkFlowMasterList}" var="stfWrkFlwDtls" varStatus="count">
						<tr>
							<td>${count.count}</td>
							<td>${stfWrkFlwDtls.objTypeName}</td>
							<td>${stfWrkFlwDtls.staffName}</td>
							<td>${stfWrkFlwDtls.moduleNames}</td>
							<td class="text-center">
								<a href="#" onclick="fetchFormDtls('${stfWrkFlwDtls.staffWorkFlowMasterId}','EDIT')" class="btn btn-warning btn-sm" title="Edit"><i class="fa fa-edit"></i></a>
								<a href="#" onclick="fetchFormDtls('${stfWrkFlwDtls.staffWorkFlowMasterId}','VIEW')" class="btn btn-primary btn-sm" title="View"><i class="fa fa-eye"></i></a>
								<%-- <a href="#" onclick="statusFormDetails('${stfWrkFlwDtls.staffWorkFlowMasterId}',${stfWrkFlwDtls.isActive})" class="btn ${stfWrkFlwDtls.isActive eq true ? 'btn-danger' : 'btn-success'} btn-xs"><i class="${stfWrkFlwDtls.isActive eq true ? 'fa fa-lock' : 'fa fa-unlock'}" title="${stfWrkFlwDtls.isActive eq true ? 'InActive' : 'Active'}"></i></a> --%>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<form action = "${contextPath}/staff/workFlow/fetchWorkFlowDetails" method="get" id="fetchForm">
	<input type="hidden" id="cipherText" name="cipherText">
    <input type="hidden" name="wrkFlowMstId" id="staffWorkFlowMasterIdFetch"/>
    <input type="hidden" name="statusCall" id="statusCallFetch"/>
</form>

<%-- <form action = "${contextPath}/staff/workFlow/processDetails" method="post" id="fetchForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="wrkFlowMstId" id="staffWorkFlowMasterIdFetch"/>
    <input type="hidden" name="statusCall" id="statusCallFetch"/>
</form> --%>


<form action = "${contextPath}/staff/workFlow/processDetails" method="post" id="statusForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="staffWorkFlowMaster.staffWorkFlowMasterId" id="staffWorkFlowMasterIdStatus"/>
    <input type="hidden" name="status"  id="status"/>
    <input type="hidden" name="statusCall" value="STATUS"/>
</form>

<script>
function submitForm(){
	if($("#moduleCode").val() == "0"){
		bootbox.alert("Please select module");
		return false;
	}else{
		showLoader();
		$("#saveFormId").submit();
	}
}

function fetchFormDtls(id,status){
	$("#statusCallFetch").val(status);
	$("#staffWorkFlowMasterIdFetch").val(id);
    var bizContent = $("#fetchForm").serializeArray();
    $("#cipherText").val(enc_password(JSON.stringify(convertFormToJSONArray(bizContent))));
	showLoader();
	$("#fetchForm").submit();
}

function statusFormDetails(id,status){
	if(status){
		$("#status").val(false);
	}else{
		$("#status").val(true);
	}
	$("#staffWorkFlowMasterIdStatus").val(id);
	showLoader();
	$("#statusForm").submit();
}
</script>
