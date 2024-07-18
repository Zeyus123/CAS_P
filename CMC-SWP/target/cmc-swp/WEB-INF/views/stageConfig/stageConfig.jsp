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
<section class="main_container">
	<div class="row">
		<!-- DropDwon -->
		<div class="col-md-12">
			<section class="panel">
				<header class="panel-heading">
					<h2 class="panel-title">Stage Configuration</h2>
				</header>
				<div class="panel-body">
					
						
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
		
						<div class="row mt-3" id="dataSection">
							<div class="table-responsiv">
								<table class="table table-bordered table-hover table-striped" id="sct">
									<thead class="text-center bagGroundColorRvr" style="color: #fff;">
										<tr>
											<th>From Role</th>
											<th>Operation</th>
											<th>Check</th>
											<th>To Role</th>
											<th style="width: 40%; max-width: 40%;">Action Code</th>
											<th>Stage</th>
											<th style="max-width: 5%;">Stage Action</th>
										</tr>
									</thead>
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

	<form action="${contextPath}/stageConfig/saveAndUpdateStageConfigList" method="post" id="stageConfigFormList" modelAttribute="stageConfigFormList">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="stageConfigDtoListStr" id="stageConfigDtoListStrId">
	</form>





</section>
<script>
	function getStageConfigData(that){
		// clear table tbody
		$("#sct tbody").remove();
		//ajax call
		if ($("#stageName").val() == "") {
			errorDisplay("Please select stage", $("#stageName"));
			return;
		}
		$.ajax({
			url : "${contextPath}/stageConfig/getStageConfigData",
			type : "GET",
			data : {
				"entityClassName" : $("#stageName").val()
			},
			success : function(data) {
				let stageConfigDtoList = JSON.parse(data.stageConfigDtoList);
				console.log(stageConfigDtoList);
				let actionTypeDtoList = data.actionTypeDtoList;
				let fromRoleList = data.fromRoleList;
				let toRoleList = data.toRoleList;
				let stageDtoList = data.stageDtoList;
				let trHtml = '';
				if(stageConfigDtoList == undefined || stageConfigDtoList == null || stageConfigDtoList.length == 0){
					// create a new tbody and append to table
					generateTbody(actionTypeDtoList, fromRoleList, toRoleList, stageDtoList, [], [], [], [], []);
					return;
				}else{
					// create tbody which has data stageConfigDtoList so checked, seleted and many more
					createAndAppendTbody(stageConfigDtoList, actionTypeDtoList, fromRoleList, toRoleList, stageDtoList);
				}

				
			},
			error : function(e) {
				alert("Error! " + e);
			}
		});
	}

	
	let sctFromRole = 'sctFromRole';
	let sctOperation = 'sctOperation';
	let sctCheck = 'sctCheck';
	let sctToRole = 'sctToRole';
	let sctAction = 'sctAction';
	let sctStage = 'sctStage';


	function generateTbody(actionTypeDtoList, fromRoleList, toRoleList, stageDtoList, selectedFromRoleIds, selectedToRoleIds, selectedActionTypeIds, selectedStageIds, selectedActionCode){
		let tbodyCount = $("#sct tbody").length;
		let rowSpanLength = actionTypeDtoList.length;
		let trHtml = '';
		for (let i = 0; i < actionTypeDtoList.length; i++) {
			trHtml += '<tr>';
			// First column with rowspan
			if (i === 0) {
				trHtml += '<td rowspan="' + rowSpanLength + '">';
				trHtml += '<select class="form-control" id="sctFromRole' + i + '_' + tbodyCount + '" onchange="checkDuplicateFromRole(this)">';
				trHtml += '<option value="">Select Role</option>';
				for (let j = 0; j < fromRoleList.length; j++) {
					trHtml += '<option value="' + fromRoleList[j].roleId + '"' + (selectedFromRoleIds.includes(fromRoleList[j].roleId) ? 'selected' : '') + '>' + fromRoleList[j].roleName + '</option>';
				}
				trHtml += '</select>';
				trHtml += '</td>';
			}

			

			// Operation column
			trHtml += '<td>';
			trHtml += '<p id="sctOperation' + i + '_' + tbodyCount + '">' + actionTypeDtoList[i].actionName ;
			// Add more criteria button
			//trHtml += '<span class="badge badge-success" style="font-size: 15px; margin-left: 5px; cursor: pointer; color:#007268" onclick="addAnotherFlowWithSameOpertion(this)"><i class="bx bxl-meta" style="font-size: 15px;"></i></span>';
			trHtml += '</p>';
			trHtml += '<input type="hidden" id="sctOperationId' + i + '_' + tbodyCount + '" value="' + actionTypeDtoList[i].actionTypeId + '">';


			// Checkbox column
			trHtml += '<td>';
			trHtml += '<input type="checkbox" class="form-group" id="sctCheck' + i + '_' + tbodyCount + '" value="1" onclick="checkboxClick(this)" ' + (selectedActionTypeIds.includes(actionTypeDtoList[i].actionTypeId) ? 'checked' : '') + '>';
			trHtml += '</td>';

			// ToRole column
			trHtml += '<td>';
			trHtml += '<select class="form-control disabled-field" id="sctToRole' + i + '_' + tbodyCount + '"onchange="makeActionCode(this)">';
			trHtml += '<option value="">Select Role</option>';
			for (let j = 0; j < toRoleList.length; j++) {
				// also check checkbox is checked
				let tr = toRoleList[j].roleId;
				let tf = selectedToRoleIds.includes(toRoleList[j].roleId);
				trHtml += '<option value="' + toRoleList[j].roleId + '" ' + (selectedToRoleIds.includes(toRoleList[j].roleId) && selectedActionTypeIds.includes(actionTypeDtoList[i].actionTypeId) ? 'selected' : '') + '>' + toRoleList[j].roleName + '</option>';
			}
			trHtml += '</select>';
			trHtml += '</td>';

			// Action code column
			trHtml += '<td>';
			trHtml += '<p id="sctAction' + i + '_' + tbodyCount + '">' + (selectedActionCode[i] == undefined ? 'NA' : selectedActionCode[i].replace(/_/g, " ")) + '</p>';
			trHtml += '</td>';

			if(i==0){
				// Stage column
				trHtml += '<td rowspan="' + rowSpanLength + '">';
				trHtml += '<select class="form-control" id="sctStage' + i + '_' + tbodyCount + '" onchange="createNewTbody(this)">';
				trHtml += '<option value="">Select Stage</option>';
				for (let j = 0; j < stageDtoList.length; j++) {
					trHtml += '<option value="' + stageDtoList[j].stageId + '" ' + (selectedStageIds.includes(stageDtoList[j].stageId) ? 'selected' : '') + '>' + stageDtoList[j].stageNameEn + '</option>';
				}
				trHtml += '</select>';
				trHtml += '</td>';

				// Stage Action column Delete
				trHtml += '<td rowspan="' + rowSpanLength + '">';
				trHtml += '<button type="button" id="sctStageAction' + i + '_' + tbodyCount + '" class="btn btn-danger btn-sm disabled-field" onclick="deleteRow(this)"><i class="bx bx-trash"></i></button>';
				trHtml += '</td>';
			}
			trHtml += '</tr>';
		}
		trHtml += hr();
		let tbody = '<tbody class="text-center" id="tbody' + tbodyCount + '">' + trHtml + '</tbody>';
		$("#sct").append(tbody);
		return tbody;
	}

	function createAndAppendTbody(stageConfigDtoList, actionTypeDtoList, fromRoleList, toRoleList, stageDtoList){
		for (let i = 0; i < stageConfigDtoList.length; i++) {
			let stageForwardedRuleList = stageConfigDtoList[i].stageForwardedRuleList;
			let selectedFromRoleIds = [];
			let selectedToRoleIds = [];
			let selectedActionTypeIds = [];
			let selectedStageIds = [];
			let selectedActionCode = [];
			for (let j = 0; j < stageForwardedRuleList.length; j++) {
				selectedFromRoleIds.push(stageForwardedRuleList[j].fromRoleId);
				selectedToRoleIds.push(stageForwardedRuleList[j].toRoleId);
				selectedActionTypeIds.push(stageForwardedRuleList[j].actionTypeId);
				selectedStageIds.push(stageForwardedRuleList[j].stageId);
				selectedActionCode.push(stageForwardedRuleList[j].actionCode);
			}
			generateTbody(actionTypeDtoList, fromRoleList, toRoleList, stageDtoList, selectedFromRoleIds, selectedToRoleIds, selectedActionTypeIds, selectedStageIds, selectedActionCode);
			
		}


	}
		













	function hr(){
		let hr = '<tr><td colspan="7"><hr></td></tr>';
		return hr;
	}

	function createNewTbody(that){
		let txt = $(that).find("option:selected").text();
		if(txt.toLowerCase() === 'next'){
			// from role column, and atleast one checkbox should be checked and his corresponding to role should be selected
			let tbody = $(that).closest('tbody');
			let fromRole = tbody.find('select[id^="sctFromRole"]').val();
			if (fromRole === "") {
				errorDisplay("Please select from role", tbody.find('select[id^="sctFromRole"]'));
				// make stage column value to nothing
				$(that).val("");
				return;
			}
			// get all checkbox from tbody
			let checkboxList = tbody.find('input[type="checkbox"]');
			let isAnyCheckboxChecked = false;
			for (let i = 0; i < checkboxList.length; i++) {
				if ($(checkboxList[i]).is(':checked')) {
					let closestTr = $(checkboxList[i]).closest('tr');
					let toRole = closestTr.find('select[id^="sctToRole"]').val();
					if (toRole === "") {
						errorDisplay("Please select to role", closestTr.find('select[id^="sctToRole"]'));
						// make stage column value to nothing
						$(that).val("");
						return;
					}
					isAnyCheckboxChecked = true;
				}
			}
			if (!isAnyCheckboxChecked) {
				// error display please select any checkbox on current tbody first tr
				errorDisplay("Please select any checkbox", $(checkboxList[0]));
				// make stage column value to nothing
				$(that).val("");
				return;
			}
			enableDisableTbody(that, true);
			// make a copy of current tbody and append to table
			makeCopyOfTbody(that);
		}
	}

	function makeCopyOfTbody(that){
		let tbody = $(that).closest('tbody');
		let tbodyCount = $("#sct tbody").length;
		let tbodyHtml = tbody.html();
		let newTbody = '<tbody class="text-center" id="tbody' + tbodyCount + '">' + tbodyHtml + '</tbody>';
		$("#sct").append(newTbody);
		// remove disabled-field class from new tbody
		let newTbodyId = "#tbody" + tbodyCount;
		enableDisableTbody(newTbodyId, false);
		// make action code 'NA' in new tbody
		$(newTbodyId).find('p[id^="sctAction"]').text('NA');
		// disable to role column in new tbody
		$(newTbodyId).find('select[id^="sctToRole"]').addClass('disabled-field');
	}

	function enableDisableTbody(that, enOrDis){
		let tbody = $(that).closest('tbody');
		// get all input, select, button, check box inside tbody 
		let inputs = tbody.find('input, select, button, textarea', 'checkbox');
		if (!enOrDis){
			// remove disabled-field class from all inputes
			inputs.each(function(){
				$(this).removeClass('disabled-field');
			});
		}else{
			// add disabled-field class to all inputes
			inputs.each(function(){
				$(this).addClass('disabled-field');
			});
		}


	}

	// if any checkbox is checked in a perticular tbody then disable from role column of that tbody use document object
	$(document).on('click', 'input[id^="sctCheck"]', function(){
		let tbody = $(this).closest('tbody');
		let fromRoleSelect = tbody.find('select[id^="sctFromRole"]');
		if ($(this).is(':checked')) {
			$(fromRoleSelect).addClass('disabled-field');
		}else{
			// no checkbox is checked in tbody then enable from role column
			let tbodyCheckboxList = tbody.find('input[id^="sctCheck"]');
			let isAnyCheckboxChecked = false;
			for (let i = 0; i < tbodyCheckboxList.length; i++) {
				if ($(tbodyCheckboxList[i]).is(':checked')) {
					isAnyCheckboxChecked = true;
					break;
				}
			}
			if (!isAnyCheckboxChecked) {
				$(fromRoleSelect).removeClass('disabled-field');
			}
		}
	});

	function deleteRow(that){
		// if tbody length is 1 then return
		let tbodyList = $("#sct tbody");
		if (tbodyList.length === 1) {
			errorDisplay("Atleast one row is required", $(that).closest('table'));
			return;
		}
		$(that).closest('tbody').remove();
		// remove disabled-field class for upper tbody
		let tbody = $(that).closest('tbody');
		let tbodyId = tbody.attr('id');
		let tbodyCount = tbodyId.replace('tbody', '');
		let upperTbodyId = "#tbody" + (tbodyCount - 1);
		// set stage column value to nothing
		$(upperTbodyId).find('select[id^="sctStage"]').val("");

		// enable checkbox column and stage column and delete button
		$(upperTbodyId).find('input[id^="sctCheck"]').removeClass('disabled-field');
		$(upperTbodyId).find('select[id^="sctStage"]').removeClass('disabled-field');
		$(upperTbodyId).find('button[id^="sctStageAction"]').removeClass('disabled-field');
		
		
	}

	function checkboxClick(that) {
		let tbody = $(that).closest('tbody');
		let fromRole = tbody.find('select[id^="sctFromRole"]').val();
		if (fromRole === "") {
			errorDisplay("Please select from role", tbody.find('select[id^="sctFromRole"]'));
			// Operation column error display please choose any operation current row
			$(that).prop('checked', false);
			return;
		}else{
		}

		// check if checkbox and operation in same row is and operation value is Draft then choose to role as same as from role and disable to role
		const tr = $(that).closest('tr');
		const toRoleSelect = tr.find('select[id^="sctToRole"]');
		const actionText = tr.find('p[id^="sctAction"]');
		const operationName = tr.find('p[id^="sctOperation"]').text().toLowerCase();

		if ((operationName === 'draft' || operationName === 'approve') && $(that).is(':checked')) {
			toRoleSelect.val(fromRole).addClass('disabled-field');
			makeActionCode(that);
		} else {
			toRoleSelect.val("");
			actionText.text("NA");

			if (operationName !== 'draft' && operationName !== 'approve') {
				toRoleSelect.toggleClass('disabled-field', !$(that).is(':checked'));
			}
		}


	}

	function makeActionCode(that){
		// get current tbody
		let selectedModelText = $("#stageName").find("option:selected").text();
		let fromRoleTxt = $(that).closest('tbody').find('select[id^="sctFromRole"]').find("option:selected").text();
		let frmTxt = $(that).closest('tr').find('select[id^="sctFromRole"]').val();
		let operationTxt = $(that).closest('tr').find('p[id^="sctOperation"]').text();
		let toRoleTxt = $(that).closest('tr').find('select[id^="sctToRole"]').find("option:selected").text();
		// add space in between words operationTxt in red color
		let code = selectedModelText + " (" + operationTxt +") " + fromRoleTxt + "->" + toRoleTxt;		
		$(that).closest('tr').find('p[id^="sctAction"]').text(code);
		// create a plus sign after action code
		// of operation draft then no plus sign
		if (operationTxt.toLowerCase() === 'draft') {
			return;
		}
		let plusSign = '<span class="badge badge-success" style="font-size: 15px; margin-left: 5px; cursor: pointer; color:#007268" onclick="getAllColumnsFromModelClass(this)">+</span>';
		//$(that).closest('tr').find('p[id^="sctAction"]').append(plusSign);


	}

	function errorDisplay(errorMsg, uiId) {
		// create error div and append to given uiId parent
		let errorDiv = '<div class="alert alert-danger alert-dismissible fade show" role="alert" style="z-index: 9999;">';
		errorDiv += '<strong>Error!</strong> ' + errorMsg;
		errorDiv += '</div>';
		$(uiId).parent().append(errorDiv);
		// add css position relative to parent
		$(uiId).parent().css('position', 'relative');
		// add css position absolute to error div
		$(uiId).parent().find('div[class="alert alert-danger alert-dismissible fade show"]').css('position', 'absolute');
		removeErrorDiv();
	}

	function removeErrorDiv() {
		setTimeout(function() {
			// remove position absolute from error div by class name and remove error div
			$('div[class="alert alert-danger alert-dismissible fade show"]').parent().css('position', '');
			$('.alert').remove();
		}, 3000);
		
	}

	function checkDuplicateFromRole(that){
		// if any tbody has same from role then error display and make from role value to nothing
		// get all from role select from all tbody except current tbody
		let fromRoleSelectList = $("#sct tbody").not($(that).closest('tbody')).find('select[id^="sctFromRole"]');
		for (let i = 0; i < fromRoleSelectList.length; i++) {
			if ($(fromRoleSelectList[i]).val() === $(that).val()) {
				errorDisplay("Duplicate From Role", $(that));
				$(that).val("");
				return;
			}
		}

	}



	function saveStageConfigData(){
		// get all tbody from table
		let tbodyList = $("#sct tbody");
		if (tbodyList.length === 0) {
			errorDisplay("Please select stage", $("#stageName"));
			return;
		}
		let stageConfigDtoList = [];
		for (let i = 0; i < tbodyList.length; i++) {
			// get all checkbox from tbody
			let checkboxList = $(tbodyList[i]).find('input[type="checkbox"]');
			// get current tbody count
			let tbodyCount = $(tbodyList[i]).attr('id').replace('tbody', '');
			for (let j = 0; j < checkboxList.length; j++) {
				if ($(checkboxList[j]).is(':checked')) {
					let stgDto = {};
					let closestTr = $(checkboxList[j]).closest('tr');
					stgDto.fromRoleId = $(checkboxList[j]).closest('tbody').find('select[id^="sctFromRole"]').val();
					stgDto.toRoleId = closestTr.find('select[id^="sctToRole"]').val();
					stgDto.actionTypeId = closestTr.find('input[id^="sctOperationId"]').val();
					stgDto.stageId = $(checkboxList[j]).closest('tbody').find('select[id^="sctStage"]').val();
					// action code
					let actionCode = closestTr.find('p[id^="sctAction"]').text().trim();
					// remove space from action code with _
					actionCode = actionCode.replace(/ /g, "_");
					stgDto.actionCode = actionCode;
					stgDto.modelClassName = $("#stageName").val();
					stgDto.displayCount = (parseInt(tbodyCount) + 1);
					stageConfigDtoList.push(stgDto);
				}
			}
		}
		// check last tbody at least one checkbox is checked or not and his corresponding to role is selected or not
		let lastTbody = tbodyList[tbodyList.length - 1];
		let lastTbodyCheckboxList = $(lastTbody).find('input[type="checkbox"]');
		let isAnyCheckboxChecked = false;
		for (let i = 0; i < lastTbodyCheckboxList.length; i++) {
			if ($(lastTbodyCheckboxList[i]).is(':checked')) {
				let closestTr = $(lastTbodyCheckboxList[i]).closest('tr');
				let toRole = closestTr.find('select[id^="sctToRole"]').val();
				if (toRole === "") {
					errorDisplay("Please select to role", closestTr.find('select[id^="sctToRole"]'));
					return;
				}
				isAnyCheckboxChecked = true;
			}
		}
		if (!isAnyCheckboxChecked) {
			// error display please select any checkbox on current tbody first tr
			errorDisplay("Please select any checkbox", $(lastTbodyCheckboxList[0]));
			return;
		}


		// check last tbody stage is selected or not as final stage
		let lastTbodyStage = $(lastTbody).find('select[id^="sctStage"]');
		if ($(lastTbodyStage).val() === "") {
			errorDisplay("Please select stage", $(lastTbodyStage));
			return;
		}
		// group by from role
		let groupByFromRole = groupBy(stageConfigDtoList, 'displayCount');

		console.log(groupByFromRole);
		
		// base64 encode
		let stageConfigDtoListStr = btoa(JSON.stringify(stageConfigDtoList));
		$("#stageConfigDtoListStrId").val(stageConfigDtoListStr);

		// show confirmation box
		// confirmBox();

		let crm = confirm("Are you sure you want to submit?");
		if (crm) {
			// submit form
			$("#stageConfigFormList").submit();
		}
	}

	function toSave(yesOrNo){
		if (yesOrNo === 'yes') {
			// show loader
			$("#loaderId").show();
			// submit form
			$("#stageConfigFormList").submit();
		}else{
			// hide confirm box
			$("#confirmBoxId").modal('hide');
			// clear stageConfigDtoListStrId
			$("#stageConfigDtoListStrId").val("");
			// hide loader
			$("#loaderId").hide();
		}
	}

	

	function groupBy(objectArray, property) {
		return objectArray.reduce(function (acc, obj) {
			let key = obj[property]
			if (!acc[key]) {
				acc[key] = []
			}
			acc[key].push(obj)
			return acc
		}, {})
	}


	// create a confirm box with yes and no button
	function confirmBox(){
		let confirmBox = '<div class="modal fade" id="confirmBoxId" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">';
		confirmBox += '<div class="modal-dialog" role="document">';
		confirmBox += '<div class="modal-content">';
		confirmBox += '<div class="modal-header">';
		confirmBox += '<h5 class="modal-title" id="exampleModalLabel">Confirm Box</h5>';
		confirmBox += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
		confirmBox += '<span aria-hidden="true">&times;</span>';
		confirmBox += '</button>';
		confirmBox += '</div>';
		confirmBox += '<div class="modal-body">';
		confirmBox += 'Are you sure you want to save?';
		confirmBox += '</div>';
		confirmBox += '<div class="modal-footer">';
		confirmBox += '<button type="button" class="btn btn-secondary" onclick="toSave(no)">No</button>';
		confirmBox += '<button type="button" class="btn btn-primary" onclick="toSave(yes)">Yes</button>';
		confirmBox += '</div>';
		confirmBox += '</div>';
		confirmBox += '</div>';
		confirmBox += '</div>';
		$("body").append(confirmBox);
		$("#confirmBoxId").modal('show');
	}

	var currentRow = null;
	// get all columns from model class
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

	function addCriteria(that){
		let criteriaColumn = $("#criteriaColumnId").val();
		let criteriaStartValue = $("#criteriaStartNameId").val();
		let criteriaEndValue = $("#criteriaEndNameId").val();
		// check criteria column is selected or not
		if (criteriaColumn === "") {
			errorDisplay("Please select criteria column", $("#criteriaColumnId"));
			return;
		}
		// check criteria start value is entered or not
		if (criteriaStartValue === "") {
			errorDisplay("Please enter criteria start value", $("#criteriaStartNameId"));
			return;
		}
		// check criteria end value is entered or not
		if (criteriaEndValue === "") {
			errorDisplay("Please enter criteria end value", $("#criteriaEndNameId"));
			return;
		}
		// check criteria start value is less than criteria end value
		if (criteriaStartValue > criteriaEndValue) {
			errorDisplay("Criteria start value should be less than criteria end value", $("#criteriaStartNameId"));
			return;
		}
		// check criteria start value is less than criteria end value
		if (criteriaStartValue === criteriaEndValue) {
			errorDisplay("Criteria start value and criteria end value should not be same", $("#criteriaStartNameId"));
			return;
		}
		
		// add criteria column, criteria start value, criteria end value in action code
		let actionCode = $(currentRow).find('p[id^="sctAction"]').text().trim();
		// add criteria column, criteria start value, criteria end value in action code
		actionCode += "##" + criteriaColumn + "->" + criteriaStartValue + "->" + criteriaEndValue;
		$(currentRow).find('p[id^="sctAction"]').text(actionCode);
		// close modal
		$("#staticBackdrop").modal('hide');

	}

	function addAnotherFlowWithSameOpertion(that){
		
	}





</script>