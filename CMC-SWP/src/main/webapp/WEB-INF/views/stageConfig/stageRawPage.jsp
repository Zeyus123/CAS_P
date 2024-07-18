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
						<input type="hidden" id="stageInUserSpecificRuleModal" value="">
						<input type="hidden" id="formIdInUserSpecificRuleModal" value="">
						<input type="hidden" id="remarksInUserSpecificRuleModal" value="">
						<input type="hidden" id="isBulkInUserSpecificRuleModal" value="">
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


<script type="text/javascript">

	function addHiddenField(name, value) {
    	return "<input type='hidden' name='" + name + "' value='" + value + "'>";
	}

	function buttonActionWithUser(){
	   let selectedUserIds = [];
	   $("input[name='userSpecificRule']:checked").each(function(){
		   selectedUserIds.push($(this).val());
	   });
	   if(selectedUserIds.length == 0){
		   bootbox.alert("Please select at least one user");
		   return;
	   }
	   
	   let stageId = $("#stageInUserSpecificRuleModal").val();
	   let formId = $("#formIdInUserSpecificRuleModal").val();
	   let remarks = $("#remarksInUserSpecificRuleModal").val();
	   let isBulk = $("#isBulkInUserSpecificRuleModal").val();
	   $("#userSpecificRuleModal").modal("hide");
	   if(isBulk == "true"){
		   bulkUpdateWorkFlowStageOnSpecificUsers(formId, stageId, remarks, selectedUserIds, true);
	   }else{
			saveWorkFlowStageOnSpecificUsers(formId, stageId, remarks, selectedUserIds, true);
	   }
   }

	function workFlowForViewOfSpecificData(formId, entityObjectId, formSubmit = false){
		if(formId == null || formId == undefined || formId == ""){
			bootbox.alert("Please provide form id where you want to view the data");
			return;
		}
		if(entityObjectId != null && entityObjectId != undefined && entityObjectId != "" && parseInt(entityObjectId) > 0){
			let entityObjectIdHtml = addHiddenField("dynamicDataParamEntityObjectId", entityObjectId);
			$("#"+formId).append(entityObjectIdHtml);
		}
		if(formSubmit){
			$("#"+formId).submit();
		}
	}


	function saveWorkFlowStage(formId, stageId, remarks, formSubmit = false){
		if(formId == null || formId == undefined || formId == ""){
			bootbox.alert("Please provide form id where you want to submit the data");
			return;
		}
		if(stageId != null && stageId != undefined && stageId != "" && parseInt(stageId) > 0){
			let stageIdHtml = addHiddenField("currentWorkflowStageParamName", stageId);
			$("#"+formId).append(stageIdHtml);
		}
		if(remarks != null && remarks != undefined && remarks != ""){
			let remarksHtml = addHiddenField("currentWorkflowStageParamRemarks", remarks);
			$("#"+formId).append(remarksHtml);
		}
		if(formSubmit){
			$("#"+formId).submit();
		}
	}

	function updateWorkFlowStage(formId, stageId, remarks, formSubmit = false, entityObjectId){
		saveWorkFlowStage(formId, stageId, remarks, false);
	   if(entityObjectId != null && entityObjectId != undefined && entityObjectId != "" && parseInt(entityObjectId) > 0){
		   let entityObjectIdHtml = "<input type='hidden' name='dynamicDataParamEntityObjectId'  value='"+entityObjectId+"'>";
		   $("#"+formId).append(entityObjectIdHtml);
	   }
	   if(formSubmit){
		   $("#"+formId).submit();
	   }
   }

	function userSpecificWorkFlow(formId, stageId, remarks, isBulk = false){
	   if(formId == null || formId == undefined || formId == ""){
		   bootbox.alert("Form id is required");
		   return;
	   }
	   if(stageId == null || stageId == undefined || stageId == ""){
		   bootbox.alert("Stage id is required");
		   return;
	   }
	   if(remarks == null || remarks == undefined || remarks == ""){
		   remarks = "";
	   }
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
					$("#stageInUserSpecificRuleModal").val(stageId);
					$("#formIdInUserSpecificRuleModal").val(formId);
					$("#remarksInUserSpecificRuleModal").val(remarks);
					$("#isBulkInUserSpecificRuleModal").val(isBulk);
					$("#userSpecificRuleModal").modal("show");
				}
			},
		error: function(response){
				bootbox.alert("Something went wrong");
			}
		});
	}

	function saveWorkFlowStageOnSpecificUsers(formId, stageId, remarks, userIds = [], formSubmit = false){
		if(formId == null || formId == undefined || formId == ""){
			bootbox.alert("Please provide form id where you want to submit the data");
			return;
		}
		if(stageId != null && stageId != undefined && stageId != "" && parseInt(stageId) > 0){
			let stageIdHtml = addHiddenField("currentWorkflowStageParamName", stageId);
			$("#"+formId).append(stageIdHtml);
		}
		if(remarks != null && remarks != undefined && remarks != ""){
			let remarksHtml = addHiddenField("currentWorkflowStageParamRemarks", remarks);
			$("#"+formId).append(remarksHtml);
		}
		if(userIds != null && userIds != undefined && userIds.length > 0){
			let specificUserIds = userIds.join(",");
			let userIdsHtml = addHiddenField("specificUserParamRemarks", specificUserIds);
			$("#"+formId).append(userIdsHtml);
		}
		if(formSubmit){
			$("#"+formId).submit();
		}
	}


	function bulkUpdateWorkFlowStage(formId, stageId, remarks, size, formSubmit = false){
		if(formId == null || formId == undefined || formId == ""){
			bootbox.alert("Please provide form id where you want to submit the data");
			return;
		}
		if(remarks != null && remarks != undefined && remarks != ""){
			let remarksHtml = addHiddenField("currentWorkflowStageParamRemarks", remarks);
			$("#"+formId).append(remarksHtml);
		}

		if(size != null && size != undefined && size != "" && parseInt(size) > 0){
			let bulkStgaeIdStr = "";
			for(let i=1; i<=size; i++){
				let stageId = $("#bulkStageId"+i).val();
				if(stageId != null && stageId != undefined && stageId != "" && parseInt(stageId) > 0){
					bulkStgaeIdStr += stageId + ",";
				}
			}
			if(bulkStgaeIdStr != null && bulkStgaeIdStr != undefined && bulkStgaeIdStr != ""){
				bulkStgaeIdStr = bulkStgaeIdStr.substring(0, bulkStgaeIdStr.length-1);
				let bulkStgaeIdHtml = addHiddenField("currentWorkflowStageParamBulkName", bulkStgaeIdStr);
				$("#"+formId).append(bulkStgaeIdHtml);
			}
		}
		if(formSubmit){
			$("#"+formId).submit();
		}
	}

	function bulkUpdateWorkFlowStageOnSpecificUsers(formId, stageId, remarks, size, userIds = [], formSubmit = false){
		if(formId == null || formId == undefined || formId == ""){
			bootbox.alert("Please provide form id where you want to submit the data");
			return;
		}
		if(remarks != null && remarks != undefined && remarks != ""){
			let remarksHtml = addHiddenField("currentWorkflowStageParamRemarks", remarks);
			$("#"+formId).append(remarksHtml);
		}

		if(size != null && size != undefined && size != "" && parseInt(size) > 0){
			let bulkStgaeIdStr = "";
			for(let i=1; i<=size; i++){
				let stageId = $("#bulkStageId"+i).val();
				if(stageId != null && stageId != undefined && stageId != "" && parseInt(stageId) > 0){
					bulkStgaeIdStr += stageId + ",";
				}
			}
			if(bulkStgaeIdStr != null && bulkStgaeIdStr != undefined && bulkStgaeIdStr != ""){
				bulkStgaeIdStr = bulkStgaeIdStr.substring(0, bulkStgaeIdStr.length-1);
				let bulkStgaeIdHtml = addHiddenField("currentWorkflowStageParamBulkName", bulkStgaeIdStr);
				$("#"+formId).append(bulkStgaeIdHtml);
			}
		}

		if(userIds != null && userIds != undefined && userIds.length > 0){
			let specificUserIds = userIds.join(",");
			let userIdsHtml = addHiddenField("specificUserParamRemarks", specificUserIds);
			$("#"+formId).append(userIdsHtml);
		}
		if(formSubmit){
			$("#"+formId).submit();
		}
	}

	function bulkUserSpecificWorkFlow(formId, size, remarks){
		// use same function for bulk update and user specific work flow
		userSpecificWorkFlow(formId, size, remarks, true);
	}

</script>