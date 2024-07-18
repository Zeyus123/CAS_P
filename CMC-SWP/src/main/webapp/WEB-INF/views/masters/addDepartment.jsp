<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> 
<c:set var="userDetails" value="${serviceOutcome.data}"/> 
<script type="text/javascript" src="${contextPath}/assets/appJs/validation/common-utils.js"></script>
<%-- <script src="${contextPath}/assets/appJs/validation/commonMaster.js"></script> --%>
<style>

</style>

<style>

</style>
<div class="cardcontainer">
<div class="row">
	<div class="col-md-6">
    	<h5>Add Department</h5>
	</div>
	<div class="col-md-6">
	    <nav aria-label="breadcrumb" style="float: right;">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">Department</li>
            </ol>
        </nav>
    </div>
</div>
<%@ include file="/WEB-INF/views/message.jsp"%>
<hr style="margin-top: 5px;" />
<h6 class="headingbg mb-4">Department Details</h6>
<form class="form-horizontal" action="${contextPath}/config/saveDepartment" method="POST" id="userForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="hidden" name="departmentId" value="${departmentData.departmentId }" id="departmentId">
	<div class="row">
		<div class="form-group col-md-3">
			<label class="required">Department Name</label>
			<div id="hideUserId"> 
				<div class="col-md-12">
					<input type="text" id="departmentName" name="departmentName" value="${departmentData.departmentName }" onchange="getDuplicateCheckData('departmentName','DEPT_NAME','departmentId')"  class="form-control form-control-sm" required  maxlength="100" placeholder="Max 100 Characters" >
				</div>
			</div>
		</div>
		<div class="form-group col-md-2">
			<label class="required">Department Code</label>
			<div class="col-md-12">
				<input type="text" id="departmentCode" name="departmentCode" value="${departmentData.departmentCode }" onchange="getDuplicateCheckData('departmentCode','DEPT_CODE','departmentId')" class="form-control form-control-sm" required maxlength="20" placeholder="Max 20 Characters">
			</div>
		</div>
		
		<div class="col-md-12 mt-2 text-center">
			<input type="button" class="btn btn-success btn-sm" value="${departmentData.departmentId eq null ?'SUBMIT' :'UPDATE'}" onclick="validateForm()" id="checkButton">
			<a href="${contextPath}/home" type="button" class="btn btn-warning btn-sm">Back</a>
		</div>
	</div>
</form>	
</div>
<div class="cardcontainer cardContainerNotFirst">
	<div class="row mt-2"> 
	
		<div class="col-md-12">
		
			<div class="card full-height">
			
				<div class="card-header">
					<h4 class="card-title"> Department List</h4>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="display table table-bordered table-hover exportbtn" style="width: 100%;table-layout: fixed;" id="tableId1">
								<thead>
									<tr>
										<th>Sl.No</th>
										<th>Department Name</th>
										<th>Department Code</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${deptData}" var="dept" varStatus="count">    
										<tr> 
										  	<td>${count.count}</td>
											<td>${dept.departmentName}</td>
										  	<td>${dept.departmentCode}</td>
										 	<td>
										 		<button type="button" class="btn btn-xs btn-warning" onclick="editDeptMaster(${dept.departmentId},${dept.isActive})" title="Edit"><i class="fa fa-edit"></i></button>
				           						<button type="button" class="btn ${dept.isActive eq true ? 'btn-danger' : 'btn-success'} btn-xs" onclick="statusDeptMaster(${dept.departmentId},${dept.isActive})" title="${dept.isActive eq true ? 'InActive' : 'Active'}"><i class="${dept.isActive eq true ? 'fa fa-lock' : 'fa fa-unlock'}"></i></button>
											</td> 
										</tr>
								  	</c:forEach> 
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
</div>




<form action="${contextPath}/config/updateDepartment" method="post" id="editForm">
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
   <input type="hidden" name="deptId" id="departmentIdEdit" />
</form>
<form action="${contextPath}/config/updateStatusDept" method="post" id="statusForm">
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
   <input type="hidden" name="deptId" id="departmentIdStatus" />
   <input type="hidden" name="status" id="isActiveStatus" />
</form>
<script>
function editDeptMaster(id,status) {
    $("#departmentIdEdit").val(id);
    if(status){
  	 bootbox.confirm("Do you want to update this Department ?",
  				function(result){
  					if(result){
  						showLoader();
  						$("#editForm").submit();
  					}
  				});
    }else{
    	 bootbox.confirm("This Department is Inactive !! Do you want to update this Department ?",
   				function(result){
   					if(result){
   						showLoader();
   						$("#editForm").submit();
   					}
   				});
    }
}
   
function statusDeptMaster(id, status) {
	 document.getElementById("departmentIdStatus").value=id;
	 var msg="";
	 if(status){
	   		$("#isActiveStatus").val(false);
	   		msg="Deactivate this Department"
	  	 } else {
	   		$("#isActiveStatus").val(true);
	   		msg="Activate this Department"
	  	 }
	 bootbox.confirm("Do you want to "+msg+" ?",
				function(result){
					if(result){
						showLoader();
						 $("#statusForm").submit();
					}
				});
}

function validateForm(){
	debugger;
	var dCode = $('#departmentCode').val();
	var dName = $('#departmentName').val();
	var btn = $('#checkButton').val().trim();
	
	debugger
	if (dName.trim() == "")
	{
		bootbox.alert("Please provide Department Name.");
		return false;
	}

	else if (dCode.trim() == "")
	{
		bootbox.alert("Please provide Department Code.");
		return false;
	}
	else{
		bootbox.confirm("Do you want to "+btn+" ?",
				
			function(result){
				if(result){
					
					$('#userForm').submit();
				}
		});
	}

	 
}

function serializeKeyForDuplicateCheck(validData,validCall,formId){
	const dataMap = new Map();
	dataMap.set('validData', validData);
	dataMap.set('validCall', validCall);
	dataMap.set('formId', formId);
	
	// Convert Map to Object and Serialize to JSON
	const serializedDataMap = JSON.stringify(Object.fromEntries(dataMap));
	return serializedDataMap;
}

function getDuplicateCheckData(id,validCall,formId){

	var data = $("#"+id).val().trim();
	var formData = $("#"+formId).val();
	
	if(data != ""){
		var bizContent = serializeKeyForDuplicateCheck(data,validCall,formData);
		var cipherText = enc_password(bizContent);
		showLoader();
	 	$.ajax({
			url:'${contextPath}/ajax/validateDuplicateData',
			data: {
				"cipherText" : cipherText,
			},
			success: function(response) {
				hideLoader();
				var responseData = response;
				if (responseData.outcome) {
					if(responseData.data == "YES"){
						bootbox.alert(responseData.message);
						$("#"+id).val("");
						return false;
					}
					else{
						var dcode=$('#departmentCode').val()
						var dName = $('#departmentName').val();
						if( dcode!="" && dName!=""){
							validateForm()
							return true;
						}
						
					}
				} else {
					bootbox.alert("Something went wrong");
					$("#"+id).val("");
					return false;
				}
			},
		});
	}

}
	
</script>


	
	<!--   Core JS Files   -->
<script src="${contextPath}/assets/js/core/jquery-3.5.1.min.js"></script>
<script src="${contextPath}/assets/js/core/popper.min.js"></script>
<%-- 	<script src="${contextPath}/assets/js/core/bootstrap.min.js"></script> --%>

<!-- jQuery UI -->
<script src="${contextPath}/assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script src="${contextPath}/assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>
<script src="${contextPath}/assets/js/plugin/datatables/datatables.min.js"></script>


<!-- jQuery Scrollbar -->
<script src="${contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>


<!-- Atlantis JS -->
<script src="${contextPath}/assets/js/atlantis.min.js"></script>
<script >
$(document).ready(function() {
	$('#basic-datatables').DataTable({
	});
});
</script>

