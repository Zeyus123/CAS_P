function appendHiddenFieldToFormForWorkFlow(formId, stageId, remarks, userIds, bulkStageIds, entityObjectId){
	debugger;
	if(formId == null || formId == undefined || formId == ""){
		bootbox.alert("Form id is required");
		return;
	}
    
	if(stageId != null && stageId != undefined && stageId != "" && parseInt(stageId) > 0){
		let currentStageIdV2Html = "<input type='hidden' name='currentWorkflowStageParamName' value='"+stageId+"'>";
		$("#"+formId).append(currentStageIdV2Html);
	}
	if(remarks != null && remarks != undefined && remarks != "" && remarks.trim() != ""){
		remarks = remarks.trim();
		let currentStageRemarkV2Html = "<input type='hidden' name='currentWorkflowStageParamRemarks' value='"+remarks+"'>";
		$("#"+formId).append(currentStageRemarkV2Html);
	}

	if(entityObjectId != null && entityObjectId != undefined && entityObjectId != "" && parseInt(entityObjectId) > 0){
		let entityObjectIdHtml = "<input type='hidden' name='dynamicDataParamEntityObjectId'  value='"+entityObjectId+"'>";
		$("#"+formId).append(entityObjectIdHtml);
	}

	let specificUserIds = "";
	if(userIds.length > 0){
		specificUserIds = userIds.join(",");
		let specificUserIdsHtml = "<input type='hidden' name='specificUserParamRemarks' value='"+specificUserIds+"'>";
		$("#"+formId).append(specificUserIdsHtml);
	}
	let bulkStageIdsStr = "";
	if(bulkStageIds.length > 0){
		bulkStageIdsStr = bulkStageIds.join(",");
		let bulkStageIdsHtml = "<input type='hidden' name='currentWorkflowStageParamBulkName' value='"+bulkStageIdsStr+"'>";
		$("#"+formId).append(bulkStageIdsHtml);
	}


}

function appendHiddenFieldToFormForWorkFlowForView(formId, entityObjectId){
	if(entityObjectId != null && entityObjectId != undefined && entityObjectId != "" && parseInt(entityObjectId) > 0){
		let entityObjectIdHtml = "<input type='hidden' name='dynamicDataParamEntityObjectId'  value='"+entityObjectId+"'>";
		$("#"+formId).append(entityObjectIdHtml);
	}
}
