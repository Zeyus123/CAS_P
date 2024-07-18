<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
	.patch {
	background: #f1f1f1;
    border-bottom: 1px solid #ccc;
    /* padding-bottom: 5px; */
    display: flex;
    align-items: center;
    flex-wrap: wrap;
	
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    padding: 20px;
	background-color: white;
  	border-radius: 10px;
	border-radius: 10px;
	margin-bottom: 20px;
	}

	.disabled-field {
		pointer-events: none;
		opacity: 0.7; /* You can adjust the opacity to your preference */
	}

	


</style>
<div class="breadcrumb_conatiner">
	<%@ include file="/WEB-INF/views/stageConfig/message.jsp"%>
              <div class="col-md-6">
                <h4 class="change-color">Stage Configuration</h4>
              </div>
              <div class="col-md-6">
                <nav aria-label="breadcrumb">
                  <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                      <a href="#">Home</a>
                    </li>
                    <li class="breadcrumb-item">
                      <a href="#">/stageConfig/makeStageConfig</a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Stage Configuration</li>
                  </ol>
                </nav>
              </div>
            </div>

              <div class="row">
                <div class="col-md-12">

                  <div class="card">
                    <div class="card-header">
                      <h5 class="card-title">Stage Configuration</h5>
                    </div>
                    <div class="card-body">
					
						
						<div class="row " style="display: flex; justify-content: flex-center; flex-wrap: wrap;">
							<div class="col-md-12">
								<div class="bagGroundColor px-3">
									<div class="row">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label" style="color: #fff;">Select Activity</label>
												<select class="col-md-3 form-control" id="stageName" onchange="getStageConfigData(this)" name="modalName">
													<option value="">Select Stage</option>
													<c:forEach items="${allEntityClasses}" var="stage">
														<option value="${stage.packageName}">${stage.className}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										
									</div>
								</div>
							</div>
						</div>

						<!-- Start date and End Date picker -->
						<div class="row mt-3">
                             <div class="col-md-3">
                                <label class="control-label">Start Date</label>
                             	<input type="date" class="form-control" id="startDateId">
                             </div>

                            <div class="col-md-1">
							<label class="control-label">Present Date</label>
							<input type="Checkbox" id="presentDateId" onchange="presentDateChange(this)">
							</div>
                            <div class="col-md-3">
							<label class="control-label">End Date</label>
							<input type="date" class="form-control" id="endDateId">
							</div>
						</div>

						<!-- View Roles -->
						<div class="row mt-3">
							<div class="col-md-12">
								<div class="bagGroundColor px-3">
									<div class="row">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label" style="color: #fff;">View Roles</label>
												<select class="form-control selectpicker " id="viewRolesId" name="modalName" multiple data-live-search="true">
													<option value="">Select Role</option>
												</select>
											</div>
										</div>
										
									</div>
								</div>
							</div>
						</div>

		
						<div class="row mt-3" id="dataSection">
							<div class="table-responsiv">
								<table class="table table-bordered table-hover table-striped" id="sct">
									<thead class="text-center bagGroundColorRvr" style="color: #fff;">
										<tr>
											<th>From Role</th>
											<th>Operation</th>
											<th>To Role</th>
											<th>Write Roles</th>
											<!-- <th>Criteria Json(Only for NEXT_W_CRI)</th> -->
											<th>Is UserSpecific</th>
											<th>Can be Reopend</th>
											<th>Does Remark Required</th>
											<th>Bulk Approval</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody id="sctBody"></tbody>
								</table>
							</div>
						</div>


					<!-- Submit butoon in center -->
					<div class="row" style="display: flex; justify-content: center; flex-wrap: wrap; margin-top: 20px;">
						<div class="col-md-12 text-center">
							<button type="button" id="satgeConfigBtnId" class="btn btn-success" onclick="saveStageConfigData()">Save</button>
						</div>
					</div>
				</div>
			</section>
	</div>


	<!-- Custom beautiful Page Loader using bootstarp -->
	<div class="loader" id="loaderId" style="display: none;">
		<div class="loader-inner ball-pulse">
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>

	<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
		<div class="modal-header">
		  <h5 class="modal-title" id="staticBackdropLabel">More Criteria</h5>
		  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Criteria Column</label>
						<select class="form-control" id="criteriaColumnId">
							<option value="">Select Column</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Criteria Start Value</label>
						<input type="text" class="form-control" id="criteriaStartNameId">
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Criteria End Value</label>
						<input type="text" class="form-control" id="criteriaEndNameId">
					</div>
				</div>
			</div>
				

		</div>
		<div class="modal-footer">
		  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		  <button type="button" class="btn btn-primary" onclick="addCriteria(this)">Add</button>
		</div>
	  </div>
	</div>
  </div>

	<form action="${contextPath}/stageConfig/saveAndUpdateStageConfigObject" method="post" id="saveAndUpdateStageConfigObject" modelAttribute="saveAndUpdateStageConfigObjectStr">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="saveAndUpdateStageConfigObjectStr" id="saveAndUpdateStageConfigObjectStrId">
	</form>

</section>
<script>

	function presentDateChange(that){
		if ($(that).prop("checked")) {
			$("#endDateId").val("");
			$("#endDateId").addClass("disabled-field");
		} else {
			$("#endDateId").removeClass("disabled-field");
		}
	}


	function getStageConfigData(that){
		// clear table tbody
		$("#sctBody").empty();
		//ajax call
		if ($("#stageName").val() == "") {
			displayErrorMsg("stageName", "Please select stage");
			return;
		}
		$.ajax({
			url : "${contextPath}/stageConfig/getStageConfigData",
			type : "GET",
			data : {
				"entityClassName" : $("#stageName").val()
			},
			success : function(data) {
				fromRoleList = data.fromRoleList;
				toRoleList = data.toRoleList;
				stageDtoList = data.stageDtoList;
				actionTypeDtoList = data.actionTypeDtoList;
				$("#sctBody").empty();
				createTable();
				viewRoles(fromRoleList);
			},
			error : function(e) {
				alert("Error! " + e);
			}
		});
	}

	function viewRoles(fromRoleList){
		let viewRolesId = $("#viewRolesId");
		$(viewRolesId).empty();
		$(viewRolesId).append('<option value="">Select Role</option>');
		for (let i = 0; i < fromRoleList.length; i++) {
			$(viewRolesId).append('<option value="' + fromRoleList[i].roleId + '">' + fromRoleList[i].roleName + '</option>');
		}
		// refresh select picker
		$('.selectpicker').selectpicker('refresh');
	}


	let rowId = 0;
	let fromRoleList = [];
	let toRoleList = [];
	let stageDtoList = [];
	let actionTypeDtoList = [];
	function createTable(){
		// get table body
		let tableBody = "";
		tableBody += "<tr id='rowId"+rowId+"'>";
		// from role select
		tableBody += "<td>";
		tableBody += "<select class='form-control' id='fromRoleId"+rowId+"' onchange='fromRoleChange(this)'>";
		tableBody += "<option value=''>Select Role</option>";
		for (let i = 0; i < fromRoleList.length; i++) {
			tableBody += "<option value='" + fromRoleList[i].roleId + "'>" + fromRoleList[i].roleName + "</option>";
		}
		tableBody += "</select>";
		tableBody += "</td>";

		// action type select
		tableBody += "<td>";
		tableBody += "<select class='form-control disabled-field' id='actionTypeId"+rowId+"' onchange='operationTypeChange(this)'>";
		tableBody += "<option value=''>Select Action</option>";
		for (let i = 0; i < actionTypeDtoList.length; i++) {
			tableBody += "<option value='" + actionTypeDtoList[i].actionTypeId + "'>" + actionTypeDtoList[i].actionName + "</option>";
		}
		tableBody += "</select>";
		tableBody += "</td>";

		// to role select
		tableBody += "<td>";
		tableBody += "<select class='form-control disabled-field' id='toRoleId"+rowId+"' onchange='toRoleChange2(this)'>";
		tableBody += "<option value=''>Select Role</option>";
		for (let i = 0; i < toRoleList.length; i++) {
			tableBody += "<option value='" + toRoleList[i].roleId + "'>" + toRoleList[i].roleName + "</option>";
		}
		tableBody += "</select>";
		tableBody += "</td>";


		// can write check box
		tableBody += "<td>";
		tableBody += "<input type='checkbox' class='disabled-field' id='canWriteId"+rowId+"'>";
		tableBody += "</td>";

		// is user specific check box
		tableBody += "<td>";
		tableBody += "<input type='checkbox' class='disabled-field' id='isUserSpecificId"+rowId+"'>";
		tableBody += "</td>";

		// can be reopend check box
		tableBody += "<td>";
		tableBody += "<input type='checkbox' class='disabled-field' id='canBeReopendId"+rowId+"'>";
		tableBody += "</td>";

		// Remark Required check box
		tableBody += "<td>";
		tableBody += "<input type='checkbox' class='disabled-field' id='remarkRequiredId"+rowId+"'>";
		tableBody += "</td>";

		//bulk approval
		tableBody += "<td>";
		tableBody += "<input type='checkbox' class='' id='bulkApprovalId"+rowId+"'>";
		tableBody += "</td>";


		// read roles
		// tableBody += "<td>";
		// tableBody += "<select class='form-control selectpicker ' id='readRoleId"+rowId+"' multiple data-live-search='true'>";
		// for (let i = 0; i < toRoleList.length; i++) {
		// 	tableBody += "<option value='" + toRoleList[i].roleId + "'>" + toRoleList[i].roleName + "</option>";
		// }
		// tableBody += "</select>";
		// tableBody += "</td>";



		// criteria json 
		// tableBody += "<td>";
		// tableBody += "<textarea class='form-control ' id='criteriaJsonId"+rowId+"' rows='3'></textarea>";
		// tableBody += "</td>";

		// action
		tableBody += "<td>";
		tableBody += "<select class='form-control disabled-field' id='actionId"+rowId+"' onchange='nextRowAdd(this)'>";
		tableBody += "<option value=''>Select Action</option>";
		for (let i = 0; i < stageDtoList.length; i++) {
			tableBody += "<option value='" + stageDtoList[i].stageCode + "'>" + stageDtoList[i].stageNameEn + "</option>";
		}
		tableBody += "</select>";

		tableBody += "<button type='button' class='btn btn-primary ' onclick='removeRow(this)'>Remove</button>";


		tableBody += "</td>";

		// Display order 
		tableBody += "<input type='hidden' id='displayOrder"+rowId+"' value='"+rowId+"'>";

		tableBody += "</tr>";
		rowId++;

		$("#sctBody").append(tableBody);

		// refresh select picker
		$('.selectpicker').selectpicker('refresh');

	}


	function removeRow(that){

		// if only one row then return
		if ($("#sct tbody tr").length == 1) {
			return;
		}

		// remove row
		$(that).parent().parent().remove();

		// get last row id
		let lastRowId = $("#sct tbody tr:last").attr("id");
		let lastRowIdNum = lastRowId.substring(5);
		// set action id as ""
		$("#actionId" + lastRowIdNum).val("");
		// enable last row
		enableFullRow(lastRowIdNum);


		rowId--;
	}



	function fromRoleChange(that){
		let rowId = $(that).parent().parent().attr("id"); // rowId0 -> 0
		rowId = rowId.substring(5); // 0
		// if value "" then return
		if ($(that).val() == "") {
			$("#actionTypeId" + rowId).val("");
			$("#actionTypeId" + rowId).addClass("disabled-field");
			$("#toRoleId" + rowId).val("");
			$("#toRoleId" + rowId).addClass("disabled-field");
			$("#canWriteId" + rowId).prop("checked", false);
			$("#canWriteId" + rowId).addClass("disabled-field");
			$("#isUserSpecificId" + rowId).prop("checked", false);
			$("#isUserSpecificId" + rowId).addClass("disabled-field");
			$("#canBeReopendId" + rowId).prop("checked", false);
			$("#canBeReopendId" + rowId).addClass("disabled-field");

			$("#remarkRequiredId" + rowId).prop("checked", false);
			$("#remarkRequiredId" + rowId).addClass("disabled-field");
			$("#actionId" + rowId).val("");
			$("#actionId" + rowId).addClass("disabled-field");
			return;
		}else{
			$("#actionTypeId" + rowId).removeClass("disabled-field");
		}
	}

	function operationTypeChange(that){
		let actionTypeId = $(that).val();
		let rowId = $(that).parent().parent().attr("id"); // rowId0 -> 0
		rowId = rowId.substring(5); // 0
		let actionTypeName = $("#actionTypeId" + rowId).val();
		if (actionTypeName == "") {
			$("#toRoleId" + rowId).val("");
			$("#toRoleId" + rowId).addClass("disabled-field");
			$("#canWriteId" + rowId).prop("checked", false);
			$("#canWriteId" + rowId).addClass("disabled-field");
			$("#isUserSpecificId" + rowId).prop("checked", false);
			$("#isUserSpecificId" + rowId).addClass("disabled-field");
			$("#canBeReopendId" + rowId).prop("checked", false);
			$("#canBeReopendId" + rowId).addClass("disabled-field");
			
			$("#remarkRequiredId" + rowId).prop("checked", false);
			$("#remarkRequiredId" + rowId).addClass("disabled-field");

			$("#actionId" + rowId).val("");
			$("#actionId" + rowId).addClass("disabled-field");
			return;
		}else{
			$("#toRoleId" + rowId).removeClass("disabled-field");
		}
	}

	function toRoleChange2(that){
		let rowId = $(that).parent().parent().attr("id"); // rowId0 -> 0
		rowId = rowId.substring(5); // 0
		let toRoleId = $("#toRoleId" + rowId).val();
		if (toRoleId == "") {
			$("#canWriteId" + rowId).prop("checked", false);
			$("#canWriteId" + rowId).addClass("disabled-field");
			$("#isUserSpecificId" + rowId).prop("checked", false);
			$("#isUserSpecificId" + rowId).addClass("disabled-field");
			$("#canBeReopendId" + rowId).prop("checked", false);
			$("#canBeReopendId" + rowId).addClass("disabled-field");
			
			$("#remarkRequiredId" + rowId).prop("checked", false);
			$("#remarkRequiredId" + rowId).addClass("disabled-field");
			$("#actionId" + rowId).val("");
			$("#actionId" + rowId).addClass("disabled-field");
			return;
		}else{
			$("#canWriteId" + rowId).removeClass("disabled-field");
			$("#isUserSpecificId" + rowId).removeClass("disabled-field");
			$("#canBeReopendId" + rowId).removeClass("disabled-field");
			$("#remarkRequiredId" + rowId).addClass("disabled-field");
			$("#actionId" + rowId).removeClass("disabled-field");
		    
			//get all view roles options and remove selected to role
			let viewRolesIdHtml = $("#viewRolesId").html();
			let viewRolesId = $("#viewRolesId");
			$(viewRolesId).empty();
			$(viewRolesId).append(viewRolesIdHtml);
			$(viewRolesId).find("option[value='" + toRoleId + "']").remove();
			// refresh select picker
			$('.selectpicker').selectpicker('refresh');



		}
	}

	function actionTypeChange(that){
		let actionTypeId = $(that).val();
		let rowId = $(that).parent().parent().attr("id"); // rowId0 -> 0
		rowId = rowId.substring(5); // 0
		// if action type name is draft then make to as from role and disable it
		let actionTypeName = $("#actionTypeId" + rowId).find("option:selected").text();
		if (actionTypeName == "Draft") {
			$("#toRoleId" + rowId).val($("#fromRoleId" + rowId).val());
			addDisabled($("#toRoleId" + rowId));

			// write check box true
			$("#canWriteId" + rowId).prop("checked", true);
			addDisabled($("#canWriteId" + rowId));

			// read roles
			$("#readRoleId" + rowId).val($("#fromRoleId" + rowId).val());
			addDisabled($("#readRoleId" + rowId));

		} else if (actionTypeId == ""){
			$("#toRoleId" + rowId).val("");
			addDisabled($("#toRoleId" + rowId));

		} else {
			$("#toRoleId" + rowId).val("");
			removeDisabled($("#toRoleId" + rowId));
			// write check box false
			$("#canWriteId" + rowId).prop("checked", false);
			removeDisabled($("#canWriteId" + rowId));

			// read roles
			$("#readRoleId" + rowId).val("");
			removeDisabled($("#readRoleId" + rowId));
		}
	}


	// addClass("disabled-field");
	function addDisabled(that){
		$(that).addClass("disabled-field");
		// refresh select picker
		$('.selectpicker').selectpicker('refresh');
	}

	function removeDisabled(that){
		$(that).removeClass("disabled-field");

		// if parent div has disabled-field then remove it
		if ($(that).parent().hasClass("disabled-field")) {
			$(that).parent().removeClass("disabled-field");
		}


		// refresh select picker
		$('.selectpicker').selectpicker('refresh');
	}


	function toRoleChange(that){

		// check if to role is same as from role then make write check box true	
		let rowId = $(that).parent().parent().attr("id"); // rowId0 -> 0
		rowId = rowId.substring(5); // 0
		if ($("#fromRoleId" + rowId).val() == $("#toRoleId" + rowId).val()) {
			$("#canWriteId" + rowId).prop("checked", true);
			addDisabled($("#canWriteId" + rowId));
		} else {
			$("#canWriteId" + rowId).prop("checked", false);
			removeDisabled($("#canWriteId" + rowId));
		}

		// read roles
		$("#readRoleId" + rowId).val($("#toRoleId" + rowId).val());
		// refresh select picker
		$('.selectpicker').selectpicker('refresh');
		
	}

	function addNewRow(that){
		// get last row id
		let lastRowId = $("#sct tbody tr:last").attr("id");
		let lastRowIdNum = lastRowId.substring(5);
		// get values
		let fromRoleId = $("#fromRoleId" + lastRowIdNum).val();
		let actionTypeId = $("#actionTypeId" + lastRowIdNum).val();
		let toRoleId = $("#toRoleId" + lastRowIdNum).val();
		let criteriaJson = $("#criteriaJsonId" + lastRowIdNum).val();

		if (fromRoleId == "") {
			displayErrorMsg("fromRoleId" + lastRowIdNum, "Please select from role");
			return;
		}
		if (actionTypeId == "") {
			displayErrorMsg("actionTypeId" + lastRowIdNum, "Please select action");
			return;
		}
		if (toRoleId == "") {
			displayErrorMsg("toRoleId" + lastRowIdNum, "Please select to role");
			return;
		}

		// disable current row full row
		disableFullRow(lastRowIdNum);
		



		// add new row
		createTable();
	}


	function disableFullRow(lastRowIdNum){
		$("#fromRoleId" + lastRowIdNum).addClass("disabled-field");
		$("#actionTypeId" + lastRowIdNum).addClass("disabled-field");
		$("#toRoleId" + lastRowIdNum).addClass("disabled-field");
		$("#canWriteId" + lastRowIdNum).addClass("disabled-field");
		$("#isUserSpecificId" + lastRowIdNum).addClass("disabled-field");
		$("#canBeReopendId" + lastRowIdNum).addClass("disabled-field");
		$("#remarkRequiredId" + lastRowIdNum).addClass("disabled-field");
		$("#actionId" + lastRowIdNum).addClass("disabled-field");


		// refresh select picker
		$('.selectpicker').selectpicker('refresh');
	}

	function enableFullRow(lastRowIdNum){
		$("#fromRoleId" + lastRowIdNum).removeClass("disabled-field");
		$("#actionTypeId" + lastRowIdNum).removeClass("disabled-field");
		$("#toRoleId" + lastRowIdNum).removeClass("disabled-field");
		$("#canWriteId" + lastRowIdNum).removeClass("disabled-field");
		$("#isUserSpecificId" + lastRowIdNum).removeClass("disabled-field");
		$("#canBeReopendId" + lastRowIdNum).removeClass("disabled-field");
		$("#remarkRequiredId" + lastRowIdNum).removeClass("disabled-field");
		$("#actionId" + lastRowIdNum).removeClass("disabled-field");
		// refresh select picker
		$('.selectpicker').selectpicker('refresh');
	}



	function getAllColumnsFromModelClass(that){
		// ajax call
		$.ajax({
			url : "${contextPath}/table/getAllColumnsFromModelClass",
			type : "GET",
			data : {
				"entityClassName" : $("#stageName").val()
			},
			success : function(data) {
				let outcome = JSON.parse(data.outcome);
				if(outcome){
					let columns = JSON.parse(data.data.columns);
					let columnSelect = $("#criteriaColumnId");
					$(columnSelect).empty();
					$(columnSelect).append('<option value="">Select Column</option>');
					for (let i = 0; i < columns.length; i++) {
						$(columnSelect).append('<option value="' + columns[i] + '">' + columns[i] + '</option>');
					}
					currentRow = $(that).closest('tr');
					$("#staticBackdrop").modal('show');
				}
			},
			error : function(e) {
				alert("Error! " + e);
			}
		});
	}

	function openJsonModal(that){
		let rowId = $(that).parent().parent().attr("id"); // rowId0 -> 0
		rowId = rowId.substring(5); // 0
		// from role id
		let fromRoleId = $("#fromRoleId" + rowId).val();
		if (fromRoleId == "") {
			displayErrorMsg("fromRoleId" + rowId, "Please select from role");
			$("#actionId" + rowId).val("");
			return;
		}

		// action type id
		let actionTypeId = $("#actionTypeId" + rowId).val();
		if (actionTypeId == "") {
			displayErrorMsg("actionTypeId" + rowId, "Please select action");
			$("#actionId" + rowId).val("");
			return;
		}

		// to role id
		let toRoleId = $("#toRoleId" + rowId).val();
		if (toRoleId == "") {
			displayErrorMsg("toRoleId" + rowId, "Please select to role");
			$("#actionId" + rowId).val("");
			return;
		}

		if($("#actionId" + rowId).val() == "NEXT_W_CRI"){
			getAllColumnsFromModelClass($("#actionId" + rowId));
		}
		
	}

	function validateFROptTRReads(that){
		let isValidate = true;
		// validate from role, operation, to role, read roles has value
		let rowId = $(that).parent().parent().attr("id"); // rowId0 -> 0
		rowId = rowId.substring(5); // 0
		// from role id
		let fromRoleId = $("#fromRoleId" + rowId).val();
		if (fromRoleId == "") {
			displayErrorMsg("fromRoleId" + rowId, "Please select from role");
			$("#actionId" + rowId).val("");
			isValidate = false;
		}

		// action type id
		let actionTypeId = $("#actionTypeId" + rowId).val();
		if (actionTypeId == "") {
			displayErrorMsg("actionTypeId" + rowId, "Please select action");
			$("#actionId" + rowId).val("");
			isValidate = false;
		}

		// to role id
		let toRoleId = $("#toRoleId" + rowId).val();
		if (toRoleId == "") {
			displayErrorMsg("toRoleId" + rowId, "Please select to role");
			$("#actionId" + rowId).val("");
			isValidate = false;
		}

		// read roles
		let readRoleId = $("#readRoleId" + rowId).val();
		if (readRoleId == "") {
			displayErrorMsg("readRoleId" + rowId, "Please select read role");
			$("#actionId" + rowId).val("");
			isValidate = false;
		}

		return isValidate;
	}

	function nextRowAdd(that){
		
		let isValidate = validateFROptTRReads(that);
		if(!isValidate){
			return;
		}

		// get last row id
		let lastRowId = $("#sct tbody tr:last").attr("id");
		let lastRowIdNum = lastRowId.substring(5);
		let actionId = $("#actionId" + lastRowIdNum).val();
		// if action type is next then add new row
		if (actionId == "NEXT") {
			addNewRow(that);
		}
	}

	function saveStageConfigData(){

		let finalJsonObj = {};

		// get all data start date, present date(true/false), end date;
		let startDate = $("#startDateId").val();
		if (startDate == "") {
			displayErrorMsg("startDateId", "Please select start date");
			return;
		}
		let presentDate = $("#presentDateId").prop("checked");
		let endDate = $("#endDateId").val();
		if (presentDate == false && endDate == "") {
			displayErrorMsg("endDateId", "Please select end date");
			return;
		}

		let selectActive = $("#stageName").val();

		let fstJsonObj = {};
		fstJsonObj["startDate"] = startDate;
		fstJsonObj["presentDate"] = presentDate;
		fstJsonObj["endDate"] = endDate;
		fstJsonObj["selectActive"] = selectActive;
		let readRoleId = $("#viewRolesId").val();
		fstJsonObj["readRoleIds"] = readRoleId;

		// ittionate over table and get all data fromRole, operation, toRole, writeRoles, readRoles, criteriaJson, action
		let tableData = [];
		$("#sct tbody tr").each(function(){
			let rowId = $(this).attr("id"); // rowId0 -> 0
			rowId = rowId.substring(5); // 0
			let fromRoleId = $("#fromRoleId" + rowId).val();
			let actionTypeId = $("#actionTypeId" + rowId).val();
			let toRoleId = $("#toRoleId" + rowId).val();
			let canWrite = $("#canWriteId" + rowId).prop("checked");
			let isUserSpecific = $("#isUserSpecificId" + rowId).prop("checked");
			let canBeReopened = $("#canBeReopendId" + rowId).prop("checked");
			let remarkRequired = $("#remarkRequiredId" + rowId).prop("checked");
			let isBulkApproval = $("#bulkApprovalId" + rowId).prop("checked");
			
			let actionId = $("#actionId" + rowId).val();
			let displayOrder = $("#displayOrder" + rowId).val();


		
			let selectActiveText = $("#stageName").find("option:selected").text();
			// selectActivity(Operation)##fromRoleText->toRoleText
			let routeType = selectActiveText + '(' + $("#actionTypeId" + rowId).find("option:selected").text() + ')' + "##" + $("#fromRoleId" + rowId).find("option:selected").text() + "->" + $("#toRoleId" + rowId).find("option:selected").text();

			// (Operation)##fromRoleText->toRoleText
			let frwCode = '(' +$("#actionTypeId" + rowId).find("option:selected").text() + ')' + "##" + $("#fromRoleId" + rowId).find("option:selected").text() + "->" + $("#toRoleId" + rowId).find("option:selected").text();
			
			let tableRowData = {};
			tableRowData["fromRoleId"] = fromRoleId;
			tableRowData["actionTypeId"] = actionTypeId;
			tableRowData["toRoleId"] = toRoleId;
			tableRowData["canWrite"] = canWrite;
			tableRowData["actionCode"] = actionId;
			tableRowData["displayOrder"] = displayOrder;
			tableRowData["routeType"] = routeType;
			tableRowData["frwCode"] = frwCode;
			tableRowData["isUserSpecific"] = isUserSpecific;
			tableRowData["canBeReopened"] = canBeReopened;
			tableRowData["remarkRequired"] = remarkRequired;
			tableRowData["isBulkApproval"] = isBulkApproval;

			tableData.push(tableRowData);
		});

		finalJsonObj["fstJsonObj"] = fstJsonObj;
		finalJsonObj["tableData"] = tableData;

		// encode json
		let finalJsonStr = btoa(JSON.stringify(finalJsonObj));
		$("#saveAndUpdateStageConfigObjectStrId").val(finalJsonStr);

		bootbox.confirm({
			message: "Are you sure you want to save?",
			buttons: {
				confirm: {
					label: 'Yes',
					className: 'btn-success'
				},
				cancel: {
					label: 'No',
					className: 'btn-danger'
				}
			},
			callback: function (result) {
				if(result){
					$("#saveAndUpdateStageConfigObject").submit();
				}
			}
		});
	}



	function displayErrorMsg(id, message) {
	clearError(id);
	var element = document.getElementById(id);
	if (element) {
		var errorSpan = document.createElement('span');
		errorSpan.className = 'error-message';
		errorSpan.textContent = message;
		errorSpan.id = id + '-error';
		element.parentNode.insertBefore(errorSpan, element.nextSibling);
	} else {
		console.error('Element with id ' + id + ' not found.');
	}
}

function clearError(id) {
	var existingError = document.getElementById(id + '-error');
	if (existingError) {
		existingError.parentNode.removeChild(existingError);
	}
}

function displayErrorMsgByField(field, message) {
	clearErrorByField(field);
	if (field) {
		var errorSpan = document.createElement('span');
		errorSpan.className = 'error-message';
		errorSpan.textContent = message;
		field.parentNode.insertBefore(errorSpan, field.nextSibling);
	} else {
		console.error('Element with id ' + id + ' not found.');
	}
}

function clearErrorByField(field) {
	var existingError = field.nextElementSibling;
	if (existingError) {
		existingError.parentNode.removeChild(existingError);
	}
}

	


</script>