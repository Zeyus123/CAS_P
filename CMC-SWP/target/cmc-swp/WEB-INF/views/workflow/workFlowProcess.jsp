<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authentication var="principal" property="principal" />
<c:set var="roleCode" value="${principal.primaryRole.roleCode}"/>
<script src="${contextPath}/assets/appJs/validation/commonMaster.js"></script>

<section class="main-panel">
	<div class="content">
		<div class="breadcrumb_conatiner">
			<div class="col-md-6">
				<h4 class="change-color">Staff Workflow Configuration</h4>
			</div>
			<div class="col-md-6">
				<nav aria-label="breadcrumb">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
						<li class="breadcrumb-item"><a href="#">Staff Workflow Configuration</a></li>
						<li class="breadcrumb-item active" aria-current="page">Staff Workflow Configuration</li>
					</ol>
				</nav>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
						<h6 class="card-title">Staff Workflow Configuration</h6>
						<%@ include file="/WEB-INF/views/message.jsp"%>
					</div>
					<div class="card-body">
						<form action="${contextPath}/staff/workFlow/saveNdUpdateWflowDetails" method="post" id="saveFormId" >
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="staffWorkFlowMaster.staffWorkFlowMasterId" value="${staffWorkflowFetchData.staffWorkFlowMaster.staffWorkFlowMasterId}" id="staffWorkFlowMasterId"/>
			
							<hr style="margin-top: 5px;" />
							<!-- <h6 class="headingbg mb-4">Staff Workflow Process</h6> -->
			
							<div class="row ${not empty staffWorkflowFetchData ? 'frezee' : ''}">
								<div class="form-group col-md-4">
									<label class="required">Select a Staff :</label>
									<select class="selectpicker" data-live-search="true" id="staffId" name="staffWorkFlowMaster.staffId" onchange="getStaffDetails('staffId','staffDtlsDiv');findStaffListByStaffId('staffId',0);">
										<option value="0">- Select -</option>
										<c:forEach items="${empty staffWorkflowFetchData ? staffWorkflowData.staffMasterList : staffWorkflowFetchData.staffMasterList}" var="stf">
											<option value="${stf.staffId}" ${stf.staffId eq staffWorkflowFetchData.staffWorkFlowMaster.staffId ? 'selected' : ''}>${stf.staffName} &nbsp;(${stf.staffCode})</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<%-- <div class="hidden" id ="staffDtlsDiv">
								<%@ include file="/WEB-INF/views/hrms-parent/staffMgmt/commonStaffDetails.jsp"%>
							</div> --%>
							<c:if test="${(not empty staffWorkflowFetchData && staffWorkflowFetchData.statusCall eq 'EDIT') || (not empty staffWorkflowFetchData && staffWorkflowFetchData.statusCall eq 'VIEW')}">
								<div class="row">
									<div class="form-group col-md-3">
										<label class="required">Select a Module to add/edit the Workflow :</label>
										<select class="form-select" id="moduleId" name="moduleIds" onchange="getReportingAuthDetails()">
											<option value="0">- Select -</option>
											<c:forEach items="${empty staffWorkflowFetchData ? staffWorkflowData.moduleMstList : staffWorkflowFetchData.moduleMstList}" var="module">
												<option value="${module.moduleMstId}">${module.moduleMstName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
							
							<div class="col-md-12 mt-3 ${staffWorkflowFetchData.statusCall eq 'EDIT' || staffWorkflowFetchData.statusCall eq 'VIEW' ? 'hidden' : ''}" id="repAuthTableDivId">
								<hr style="margin-top: 5px;" />
								<div class="card-header" style="margin-left:-10px; margin-right:-10px"><h5 class="card-title">Reporting Authority Hierarchy</h5></div>
								<table class="table table-bordered mt-2" id="tableId1">
									<thead>
										<tr>
											<th style="width: 28%;"><label class="required">Reporting Authority</label></th>
											<th style="width: 28%;"><label>Designation</label></th>
											<th style="width: 28%;"><label>Department</label></th>
											<th style="width: 13%;"><label class="required">Level</label></th>
											<c:if test="${staffWorkflowFetchData.statusCall ne 'VIEW'}">
												<th style="width: 03%;" class="addRemoveBtnTh text-center"><i class="fa fa-plus" id="apnd-btn-plus" ></i></th>
											</c:if>
										</tr>
									</thead>
									<tbody class="append-box-tbody ${staffWorkflowFetchData.statusCall eq 'VIEW' ? 'frezee' : ''}" id="tableTbodyId">
										<c:choose>
											<c:when test="${empty staffWorkflowFetchData.staffWorkFlowLevelsList}">
												<tr class="append-box-tr">
													<td>
														<input type="hidden" class="validateDtlsId" name="staffWorkFlowLevelsList[0].staffWorkFlowLvlsId" id="staffWorkFlowDtlsId0" >
														<select class="form-select validateWorkFlowList validateRepAuthId" id="reportingAuthStaffId0" name="staffWorkFlowLevelsList[0].reportingAuthStaffId" onchange="getDuplicateCheckReportingAuthority('0');resetLevel('0')">
															<option value="0">- Select -</option>
														</select>
													</td>
													<td><input type="text" class="form-control form-control-sm validateDesignation" id="designationId0" readonly></td>
													<td><input type="text" class="form-control form-control-sm validateDept" id="departmentId0" readonly></td>
													<td>
														<select class="form-select validateWorkFlowList validateLevel" id="repAuthLevel0" name="staffWorkFlowLevelsList[0].repAuthLevel" >
															<option value="0">- Select -</option>
															<option value="1">1</option>
															<option value="2">2</option>
															<option value="3">3</option>
															<option value="4">4</option>
															<option value="5">5</option>
														</select>
													</td>
													<c:if test="${staffWorkflowFetchData.statusCall ne 'VIEW'}">
														<td class="addRemoveBtnTd text-center"><i class="fa fa-minus remove-occ frezee" ></i></td>
													</c:if>
												</tr>
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
			
							<c:if test="${empty staffWorkflowFetchData}">
								<div class="row form-group col-md-12">
									<label style="font-weight: 600;">Select Module(s) below to which the above workflow configuration is applicable :</label>
								</div>
								<div class="row form-group col-md-12 ${staffWorkflowFetchData.statusCall eq 'VIEW' ? 'frezee' : ''}" style="margin-left: 0;">
									<c:forEach items="${staffWorkflowData.moduleMstList}" var="module" varStatus="count">
										<div class="col-md-1" style="width: 14%; padding: 0; margin-bottom:5px">
											<input type="checkbox" name="moduleIds" id="moduleCheckBoxId${count.count-1}" onclick="getDuplicateCheck('${empty staffWorkflowFetchData ? 'SAVE' : 'UPDATE'}','${count.count-1}')" value="${module.moduleMstId}" style="float: left; margin: 1px 2px 0 0px;">
											<label>${module.moduleMstName}</label>
										</div>
									</c:forEach>
								</div>
							</c:if>
			
			
							<hr style="margin-top: 25px; margin-bottom:0" />
							<div class="col-md-12 text-center" style="margin-top:0">
								<c:if test="${staffWorkflowFetchData.statusCall ne 'VIEW'}">
									<button type="button" onclick="submitForm('${empty staffWorkflowFetchData ? 'SAVE' : 'UPDATE'}')" class="btn-submit btn-sm">${empty staffWorkflowFetchData ? 'Save' : 'Update'}</button>
								</c:if>
								<button type="button" onclick="history.back()" class="btn-cancel btn-sm ">Cancel</button>
							</div>
					    </form>
			    	</div>
				</div>
				<%@ include file="/WEB-INF/views/workflow/workFlowProcessList.jsp"%>
			</div>
		</div>
	</div>
</section>



<script>
$(document).ready(function()
{
	getStaffDetails('staffId','staffDtlsDiv');
	renumberRows();
	$("body").on("click", ".remove-occ", function () {
		$(this).closest(".append-box-tr").remove();
		renumberRows();
	});
});


function appendWflowLevelTd(count){
	if($("#staffId").val() != '0'){
		findStaffListByStaffId('staffId',count)
		$(".append-box-tbody").append('<tr class="append-box-tr"><td><input type="hidden" class="validateDtlsId" name="staffWorkFlowLevelsList['+count+'].staffWorkFlowLvlsId" id="staffWorkFlowLvlsId'+count+'" ><select class="form-select validateWorkFlowList validateRepAuthId" id="reportingAuthStaffId'+count+'" name="staffWorkFlowLevelsList['+count+'].reportingAuthStaffId" onchange="getDuplicateCheckReportingAuthority('+count+')"><option value="0">- Select -</option></select></td><td><input type="text" class="form-control form-control-sm validateDesignation" id="designationId'+count+'" readonly></td><td><input type="text" class="form-control form-control-sm validateDept" id="departmentId'+count+'" readonly></td><td><select class="form-select validateWorkFlowList validateLevel" id="repAuthLevel'+count+'" name="staffWorkFlowLevelsList['+count+'].repAuthLevel" ><option value="0">- Select -</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select></td><td class="addRemoveBtnTd text-center"><i class="fa fa-minus remove-occ"></i></td></tr>');
		$.getScript("${contextPath}/assets/appJs/validation/common-utils.js");
		count++;
		renumberRows();
	}else{
		bootbox.alert("Please select staff first to proceed");
		return false;
	}
}


function getRepAuthTable(){
	if(${staffWorkflowFetchData.statusCall eq 'EDIT'}){
		$("#repAuthTableDivId").addClass("hidden");
		$("#tableTbodyId").empty().append('');
	}else{
		$("#repAuthTableDivId").removeClass("hidden");
	}
}

function resetLevel(count){
	$('#repAuthLevel'+count).val("0");
}

function getDesignationNDeptDtls(count){
	var repAuthId = $("#reportingAuthStaffId"+count).val();
	if(repAuthId != "0"){
		showLoader();
		$.ajax({
			url : '${contextPath}/staff/workFlow/getDesignationNDepartment',
			type : 'GET',
			data: {
				"staffId" : repAuthId,
			},
			success : function(ajaxResp){
				hideLoader();
				if(ajaxResp.outcome){
					var data = ajaxResp.data;
					if(data != null && data != ""){
						$("#designationId"+count).val(data.designationName);
						$("#departmentId"+count).val(data.departmentName);
					}
				}else{
					bootbox.alert("Something went wrong");
					return false;
				}
			},
		});
	}else{
		$("#designationId"+count).val("");
		$("#departmentId"+count).val("");
	}
}


function renumberRows() {
	var listSize = $(".validateRepAuthId").length;
    for (var i = 0; i < listSize; i++) {
        var input = $(".validateDtlsId")[i];
        $(input).attr("id", $(input).attr("id").replace(/\d+$/, i));
        $(input).attr("name", "staffWorkFlowLevelsList["+i+"].staffWorkFlowLvlsId");
        
        var input2 = $(".validateRepAuthId")[i];
        $(input2).attr("id", $(input2).attr("id").replace(/\d+$/, i));
        $(input2).attr("name", "staffWorkFlowLevelsList["+i+"].reportingAuthStaffId");
        
        var input3 = $(".validateDesignation")[i];
        $(input3).attr("id", $(input3).attr("id").replace(/\d+$/, i));
        
        var input4 = $(".validateDept")[i];
        $(input4).attr("id", $(input4).attr("id").replace(/\d+$/, i));
        
        var input5 = $(".validateLevel")[i];
        $(input5).attr("id", $(input5).attr("id").replace(/\d+$/, i));
        $(input5).attr("name", "staffWorkFlowLevelsList["+i+"].repAuthLevel");
        
        $("#reportingAuthStaffId"+i).attr("onchange", "getDuplicateCheckReportingAuthority("+i+");resetLevel("+i+")");
        
    }
    var count=listSize;
    $("#apnd-btn-plus").attr("onclick", "appendWflowLevelTd("+count+")");
}

function submitForm(status){
	renumberRows();
	if(status == 'SAVE'){
		if($("#staffId").val() == "0"){
			bootbox.alert("Please select staff");
			return false;
		}else if(validateAuthorityDtls() && validateModules()){
			bootbox.confirm("Do you want to submit ?",
				function(result){
					if(result){
						showLoader();
						$("#saveFormId").submit();
					}
			});
		}
	}else if(status == 'UPDATE'){
		if($("#staffId").val() == "0"){
			bootbox.alert("Please select staff");
			return false;
		}else if($("#moduleId").val() == "0"){
			bootbox.alert("Please select module");
			return false;
		}
		else if(validateAuthorityDtls()){
			bootbox.confirm("Do you want to submit ?",
				function(result){
					if(result){
						showLoader();
						$("#saveFormId").submit();
					}
			});
		}
	}
}

function validateModules(){
	var data = true;
	var moduleListSize = "${staffWorkflowData.moduleMstList.size()}";
	var count = 0;
	for(var x = 0; x < moduleListSize; x++){
		if(document.getElementById('moduleCheckBoxId'+x).checked == true){
			count++;
		}
	}

	if(count == 0){
		bootbox.alert("At least one module name must be selected to proceed");
		data = false;
	}
	return data;
}

function validateAuthorityDtls(){
	var data = true;
	$('.validateWorkFlowList ').each(function(index, obj){
	    if(obj.nodeName == "INPUT"){
	        if(obj.value.trim() == "" || obj.value==null){
	            bootbox.alert("Please provide all the authority details");
	            var i = 0;
	    		<c:forEach items="${staffWorkflowData.moduleMstList}">
	    			document.getElementById("moduleCheckBoxId"+i).checked = false;
	    			i++;
	    		</c:forEach>
	            data = false;
	            return data;
	        }
	    }
	    else if(obj.nodeName == "SELECT"){
	        if(obj.value == "0" || obj.value =="" || obj.value == null){
	            bootbox.alert("Please select all reporting authority details");
	            var i = 0;
	    		<c:forEach items="${staffWorkflowData.moduleMstList}">
	    			document.getElementById("moduleCheckBoxId"+i).checked = false;
	    			i++;
	    		</c:forEach>
	            data = false;
	            return data;
	        }
	    }
	});
	return data;
}


function getDuplicateCheckReportingAuthority(count){
	var authority = $("#reportingAuthStaffId"+count).val();
	var retCheck=true;
    if(authority != '0') {
      var length=$(".validateRepAuthId").length;
      for ( var i = 0; Math.abs(i)<length; i=(i<=0?Math.abs(i)+1:-i)) {
       	if($('#reportingAuthStaffId'+count).val()==$('#reportingAuthStaffId'+i).val()){
			if(count != i){
				bootbox.alert("Please choose another reporting authority");
				$('#reportingAuthStaffId'+count).val("0");
				getDesignationNDeptDtls(count);
				retCheck = false;
				return retCheck;
			}
          }
       }
    }
    getDesignationNDeptDtls(count);
	return retCheck;
}

function getReportingAuthDetails(){
	var moduleId = $("#moduleId").val();
	var wrkFlowMstId = $("#staffWorkFlowMasterId").val();
	
	if(moduleId != "0" && wrkFlowMstId != ""){
		showLoader();
		$.ajax({
			url : '${contextPath}/staff/workFlow/getReportingAuthorityForUpdate',
			type : 'GET',
			data: {
				"moduleId" : moduleId,
				"wrkFlowMstId" : wrkFlowMstId,
			},
			success : function(ajaxResp){
				hideLoader();
				if(ajaxResp.outcome){
					var trHTML = "";
					var data = ajaxResp.data;
					if(data != null){
						var count = 0;
						var selData = '${staffWorkflowFetchData.statusCall}' === 'VIEW' ? 'frezee' : '';
						if(data.staffWorkFlowLevelsList.length > 0){
							$.each(data.staffWorkFlowLevelsList, function (index, value){
								trHTML = trHTML + '<tr class="append-box-tr '+selData+'"><td><input type="hidden" class="validateDtlsId" name="staffWorkFlowLevelsList['+count+'].staffWorkFlowLvlsId" id="staffWorkFlowLvlsId'+count+'" value="'+(value.staffWorkFlowLvlsId)+'"><select class="form-select validateWorkFlowList validateRepAuthId" id="reportingAuthStaffId'+count+'" name="staffWorkFlowLevelsList['+count+'].reportingAuthStaffId" onchange="getDuplicateCheckReportingAuthority('+count+');resetLevel('+count+')"><option value="0">-Select-</option><c:forEach items="${staffWorkflowFetchData.staffCommonDetailsList}" var="stf"><option value="${stf.staffId}" '+(value.reportingAuthStaffId == ${stf.staffId} ? "selected" : "")+'>${stf.staffName}</option></c:forEach></select></td><td><input type="text" class="form-control form-control-sm validateDesignation" id="designationId'+count+'" readonly></td><td><input type="text" class="form-control form-control-sm validateDept" id="departmentId'+count+'" readonly></td><td><select class="form-select validateWorkFlowList validateLevel" id="repAuthLevel'+count+'" name="staffWorkFlowLevelsList['+count+'].repAuthLevel" ><option value="0">-- Select --</option><option value="1" '+(value.repAuthLevel == 1 ? "selected" : "")+'>1</option><option value="2" '+(value.repAuthLevel == 2 ? "selected" : "")+'>2</option><option value="3" '+(value.repAuthLevel == 3 ? "selected" : "")+'>3</option><option value="4" '+(value.repAuthLevel == 4 ? "selected" : "")+'>4</option><option value="5" '+(value.repAuthLevel == 5 ? "selected" : "")+'>5</option></select></td><c:if test="${staffWorkflowFetchData.statusCall eq 'EDIT'}"><td class="addRemoveBtnTd text-center"><i class="fa fa-minus remove-occ" ></i></td></c:if></tr>';
								count++;
							});
						}else{
							trHTML = '<tr class="append-box-tr '+selData+'"><td><input type="hidden" class="validateDtlsId" name="staffWorkFlowLevelsList['+count+'].staffWorkFlowLvlsId" id="staffWorkFlowLvlsId'+count+'" ><select class="form-select validateWorkFlowList validateRepAuthId" id="reportingAuthStaffId'+count+'" name="staffWorkFlowLevelsList['+count+'].reportingAuthStaffId" onchange="getDuplicateCheckReportingAuthority('+count+');resetLevel('+count+')"><option value="0">-Select-</option><c:forEach items="${staffWorkflowFetchData.staffCommonDetailsList}" var="stf"><option value="${stf.staffId}">${stf.staffName}</option></c:forEach></select></td><td><input type="text" class="form-control form-control-sm validateDesignation" id="designationId'+count+'" readonly></td><td><input type="text" class="form-control form-control-sm validateDept" id="departmentId'+count+'" readonly></td><td><select class="form-select validateWorkFlowList validateLevel" id="repAuthLevel'+count+'" name="staffWorkFlowLevelsList['+count+'].repAuthLevel"><option value="0">-- Select --</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select></td><c:if test="${staffWorkflowFetchData.statusCall eq 'EDIT'}"><td class="addRemoveBtnTd text-center"><i class="fa fa-minus remove-occ" ></i></td></c:if></tr>';
						}
					}
					$("#repAuthTableDivId").removeClass("hidden");
					$("#tableTbodyId").empty().append(trHTML);
					if(data.staffWorkFlowLevelsList.length > 0){
						$.each(data.staffWorkFlowLevelsList, function (index, value){
							getDesignationNDeptDtls(index);
						});
					}
					renumberRows();
				}else{
					bootbox.alert("Something went wrong");
					return false;
				}
			},
		});
	}else{
		$("#repAuthTableDivId").addClass("hidden");
		$("#tableTbodyId").empty().append('');
	}
}
</script>
<script>
function getDuplicateCheck(status,count){
	if(status == 'SAVE'){
		if($("#staffId").val() == "0"){
			bootbox.alert("Please select staff");
			selectNDeselect(count,false);
			return false;
		}else if(validateAuthorityDtls()){
			selectNDeselect(count,true);
		}

	}else if(status == 'UPDATE'){
		if($("#staffId").val() == "0"){
			bootbox.alert("Please select staff");
			selectNDeselect(count,false);
			return false;
		}else if($("#moduleId").val() == "0"){
			bootbox.alert("Please select module");
			selectNDeselect(count,false);
			return false;
		}else if(validateAuthorityDtls()){
			selectNDeselect(count,true);
		}
	}
}

function selectNDeselect(count,check){
	if($('#moduleCheckBoxId'+count).prop('checked')) {
		document.getElementById("moduleCheckBoxId"+count).checked = check;
	}
	else {
		document.getElementById("moduleCheckBoxId"+count).checked = false;
	}
}


function findStaffListByStaffId(staffId,count) {
	var staffId = $("#"+staffId).val();
	if(staffId != '0'){
		showLoader();
		$.ajax({
			type : "GET",
			url : '${contextPath}/core/findStaffListByStaffId',
			dataType : "json",
			data : {
				"staffId" : staffId,
			},
			success : function(response) {
				hideLoader();
				var html = "<option value='0' selected>--Select--</option>";
				var data = response;
				if (data != "" && data != null && data.length > 0) {
					$.each(data, function(index, value) {
						html = html + "<option value="+value.staffId+">" + value.staffName + "</option>";
					});
				}
				$('#reportingAuthStaffId'+count).empty().append(html);
				
//	 			$("#staffId").css("pointer-events","none");
			},
			error : function(error) {
				bootbox.alert("somthing went wrong");
			}
		});
	}else{
	 	var tableTd = '<tr class="append-box-tr"><td><input type="hidden" class="validateDtlsId" name="staffWorkFlowLevelsList[0].staffWorkFlowLvlsId" id="staffWorkFlowLvlsId0" ><select class="form-select validateWorkFlowList validateRepAuthId" id="reportingAuthStaffId0" name="staffWorkFlowLevelsList[0].reportingAuthStaffId" onchange="getDuplicateCheckReportingAuthority(0);"><option value="0">- Select -</option></select></td><td><input type="text" class="form-control form-control-sm validateDesignation" id="designationId0" readonly></td><td><input type="text" class="form-control form-control-sm validateDept" id="departmentId0" readonly></td><td><select class="form-select validateWorkFlowList validateLevel" id="repAuthLevel'+count+'" name="staffWorkFlowLevelsList['+count+'].repAuthLevel" ><option value="0">- Select -</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select></td><td class="addRemoveBtnTd text-center"><i class="fa fa-minus remove-occ"></i></td></tr>';
	 	$(".append-box-tbody").empty().append(tableTd);
	}
}

</script>