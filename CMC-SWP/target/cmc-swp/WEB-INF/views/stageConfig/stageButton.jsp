<style>
	.frezzFormBycanTakeAction{
		pointer-events: none;
	}
	.error-message {
		color: red;
		font-weight: 400;
		margin-top: 5px;
  	}
</style>

<c:choose>
	<c:when test="${not empty buttonLabel}">
		<div class="col-lg-12 text-center btnholder">
			<input type="hidden" name="specificUserParamRemarks" id="specificUserIds" value="">
			<c:forEach items="${buttonLabel}" var="button">
				<button type="button" onclick="buttonAction(this, ${button.stageId}, ${button.isUserSpecificRule}, ${button.isRemarksRequired}, '${button.buttonCode}')" class="${button.buttonColor} no-print unfreezeField">${button.buttonName}</button>
			</c:forEach>
			<!-- Back Button -->
			<button type="button" class="btn btn-danger no-print unfreezeField" onclick="goBack();" >Back</button>
		</div>
	</c:when>
	<c:otherwise>
		<div class="col-lg-12 text-center btnholder">
			<!-- Back Button -->
			<button type="button" class="btn btn-danger no-print unfreezeField" onclick="goBack();">Back</button>
		</div>
	</c:otherwise>
</c:choose>


<!-- create a modal having a table with slno, user name, roleName, check box -->
<div class="modal fade" id="userSpecificRuleModal" tabindex="-1" role="dialog" aria-labelledby="userSpecificRuleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="userSpecificRuleModalLabel">User Specific Rule</h5>
					<button type="button" class="close unfreezeField" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" id="userSpecificRuleModalBody">
					<table class="table table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th>Sl No</th>
								<th>User Name</th>
								<th>Role Name</th>
								<th>Check</th>
							</tr>
						</thead>
						<tbody id="userSpecificRuleModalBodyTbody">
							
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" onclick="buttonActionWithUser(this)" class="btn btn-primary unfreezeField">Submit</button>
					<button type="button" class="btn btn-secondary unfreezeField" data-dismiss="modal">Close</button>
				</div>
		</div>
	</div>
</div>


<script>

var canTakeAction='${canTakeAction}';
var dynamicFromId = '${dynamicFromId}';
var dynamicFromOfWF;
var selectedIds = [];


	$(document).ready(function(){
		canTakeAction = canTakeAction == 'true' ? true : false;
		if(!canTakeAction){
			frezzFormBycanTakeAction(dynamicFromId);
		}else{
			unfrezzFormBycanTakeAction(dynamicFromId);
		}
	});


	function frezzFormBycanTakeAction(formId){
		$('#'+formId).find('input, textarea, button, select').addClass('frezzFormBycanTakeAction');
		$('#'+formId).find('.unfreezeField').removeClass('frezzFormBycanTakeAction');
	}

	function unfrezzFormBycanTakeAction(formId){
		$('#'+formId).find('input, textarea, button, select').removeClass('frezzFormBycanTakeAction');
	}

	function goBack() {
		if(window.history.length > 1){
			window.history.back();
		}else{
			window.close();
		}
	}
	

	function validateRequiredFields(className) {
		let isValid = true;
		let requiredFields = dynamicFromOfWF.find("." + className);
		requiredFields.each(function(){
			let value = getFieldValue(this);
			if(value == ""){
				isValid = false;
				this.classList.add("is-invalid");
				displayErrorMsgByField(this, "This field is required");
			}else{
				this.classList.remove("is-invalid");
				// remove the error message
				clearErrorByField(this);
			}
		});
		return isValid;
	}

	function validationForTable(className) {
		let isValid = true;
		let tableValidation = dynamicFromOfWF.find("." + className);
		tableValidation.each(function(){
			let table = this;
			let tableRows = $(table).find("tbody tr");
			if(tableRows.length == 0){
				isValid = false;
				displayErrorMsgByField(this, "At least one row is required");
			}else{
				clearErrorByField(this);
			}
		});
		return isValid;
	}

	function validationForGreaterThanZero(className) {
		let isValid = true;
		let greaterThanZero = dynamicFromOfWF.find("." + className);
		greaterThanZero.each(function(){
			let value = getFieldValue(this);
			parseFloat(value);
			if(value <= 0){
				isValid = false;
				this.classList.add("is-invalid");
				displayErrorMsgByField(this, "This field must be greater than zero");
			}else{
				this.classList.remove("is-invalid");
				clearErrorByField(this);
			}
		});
		return isValid;
	}

	function validationForAtleastOneGreaterThanZero(className) {
		let isValid = true;
		let altleastOneGreaterThanZero = dynamicFromOfWF.find("." + className);
		let isAtleastOneGreaterThanZero = false;
		if(altleastOneGreaterThanZero.length == 0){
			isAtleastOneGreaterThanZero = true;
		}else{
			altleastOneGreaterThanZero.each(function(){
				let value = getFieldValue(this);
				parseFloat(value);
				if(value > 0){
					isAtleastOneGreaterThanZero = true;
				}
			});
		}
		if(!isAtleastOneGreaterThanZero){
			isValid = false;
			altleastOneGreaterThanZero.each(function(){
				this.classList.add("is-invalid");
				displayErrorMsgByField(this, "At least one field must be greater than zero");
			});
		}else{
			altleastOneGreaterThanZero.each(function(){
				this.classList.remove("is-invalid");
				clearErrorByField(this);
			});
		}
		return isValid;
	}


	
function buttonAction(that, stageId, isUserSpecificRule, isRemarksRequired, buttonCode){
	
	if(dynamicFromId != null && dynamicFromId != undefined && dynamicFromId != ""){
		dynamicFromOfWF = $("#"+dynamicFromId);
	}else{
		dynamicFromOfWF = that.closest("form");
	}


	var requreFieldValidation = validateRequiredFields("requiredField");
	if(!requreFieldValidation){
		return;
	}

	let tableValidation = validationForTable("tableValidation");
	if(!tableValidation){
		return;
	}

	// greater than zero
	let greaterThanZero = validationForGreaterThanZero("greaterThanZero");
	if(!greaterThanZero){
		return;
	}


	// check if at least one field is greater than zero altleastOneGreaterThanZero
	let altleastOneGreaterThanZero = validationForAtleastOneGreaterThanZero("altleastOneGreaterThanZero");
	if(!altleastOneGreaterThanZero){
		return;
	}

	let isValid = true;
	// validation for at first approve or reject alter item at the stage of first approve
	let itmAprvBtn = dynamicFromOfWF.find(".altBtnapp");
	if(itmAprvBtn.length>0){
		isValid = false;
		bootbox.alert("At first approve or reject alternative item.");
		return;
	}
	
	// validation for atleast one item mandatory on vendor quote time
	let quoteAtleastOneRowMandatory = dynamicFromOfWF.find(".quoteAtleastOneRowMandatory");
	let isquoteAtleastOneRowMandatory = false;
	if(quoteAtleastOneRowMandatory.length != 0){
		quoteAtleastOneRowMandatory.each(function(){
			var id=this.id;
			if(!$("#"+id).hasClass('disable-row')){
				isquoteAtleastOneRowMandatory = true;
			}
		});
	}
	
	if(quoteAtleastOneRowMandatory.length > 0 && !isquoteAtleastOneRowMandatory){
		bootbox.alert("atleast one item is mandatory.");
		isValid = false;
	}
	
	if(isValid){
		if(isUserSpecificRule && selectedIds.length < 1){
			getAllUsersOnPerticularStage(stageId);
			$("#userSpecificRuleModal").modal("show");
		}else {
			if(isRemarksRequired){
				bootbox.prompt({
								title: 'Give a remark for Action '+ that.innerText,
								inputType: 'textarea',
								callback: function (result) {
									if(result != null){
										// create a hidden field and set the value of current stage id in this form
										submitFormWithRemarks(dynamicFromOfWF, result, stageId, selectedIds);
									}
								}
							});
			}else{
				bootbox.confirm({
					message: "Are you sure you want to "+ that.innerText +" ?",
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
					callback: function(result) {
						if (result) {
							submitFormWithRemarks(dynamicFromOfWF, "", stageId, selectedIds);
						}
					}
				});
				}
			}
	}

}

	function addHiddenField(name, value) {
    	return "<input type='hidden' name='" + name + "' value='" + value + "'>";
	}

	function submitFormWithRemarks(thatForm, remarks, stageId, specificUserIds){
		let currentStageRemarkV2Html = addHiddenField("currentWorkflowStageParamRemarks", remarks);
		let currentStageIdV2Html = addHiddenField("currentWorkflowStageParamName", stageId);
		if(specificUserIds.length > 0){
			let spUserIds = specificUserIds.join(",");
			let specificUserIdsHtml = addHiddenField("specificUserParamRemarks", spUserIds);
			thatForm.append(specificUserIdsHtml);
		}
		thatForm.append(currentStageRemarkV2Html);
		thatForm.append(currentStageIdV2Html);
		thatForm.submit();
	}


function getFieldValue(field){
	let value = "";
	if(field.tagName == "INPUT"){
		value = field.value;
	}else if(field.tagName == "SELECT"){
		value = field.value;
	}else if(field.tagName == "TEXTAREA"){
		value = field.value;
	}
	value = value.trim();
	return value;
}

function getAllUsersOnPerticularStage(stageId){
	$.ajax({
		url: "${pageContext.request.contextPath}/stageConfig/getAllUsersOnParticularStage",
		type: "GET",
		data: {
			stageId: stageId
		},
		success: function(response){
			if(response.length > 0){
				let userSpecificRuleModalBodyTbody = $("#userSpecificRuleModalBodyTbody");
				userSpecificRuleModalBodyTbody.empty();
				let slNo = 1;
				for(let i=0; i<response.length; i++){
					let user = response[i];
					let tr = $("<tr></tr>");
					let td1 = $("<td></td>").text(slNo);
					let td2 = $("<td></td>").text(user.userFirstName + " " + user.userLastName);
					let td3 = $("<td></td>").text(user.userDesignation);
					let td4 = $("<td></td>");
					let input = $("<input type='checkbox' name='userSpecificRule' value='"+user.userId+"'>");
					td4.append(input);
					tr.append(td1, td2, td3, td4);
					userSpecificRuleModalBodyTbody.append(tr);
					slNo++;
				}
				// set the stage id in the modal
				$("#currentStageIdV2").val(stageId);
			}
		},
		error: function(response){
			bootbox.alert("Something went wrong");
		}
	});
}

function buttonActionWithUser(that){
	// get the selected user id
	
	$("#userSpecificRuleModalBodyTbody input[type='checkbox']:checked").each(function(){
		selectedIds.push(this.value);
	});
	if(selectedIds.length < 1){
		bootbox.alert("Please select at least one user");
	}else{
		bootbox.prompt({
						title: 'Give a remark for this action',
						inputType: 'textarea',
						callback: function (result) {
							if(result != null){
								// create a hidden field and set the value of current stage id in this form
								submitFormWithRemarks(dynamicFromOfWF, result, $("#currentStageIdV2").val(), selectedIds);
							}
						}
					});

	}
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