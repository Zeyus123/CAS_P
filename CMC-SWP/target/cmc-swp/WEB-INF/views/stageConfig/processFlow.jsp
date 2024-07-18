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

	.fromRoleStl{
		background-color: #004f57;
    	color: #fff;
    	text-align: center;
	}

	


</style>
<section class="main_container">
	<div class="row">
		<!-- DropDwon -->
		<div class="col-md-12">
			<section class="panel">
				<header class="panel-heading">
					<h2 class="panel-title">Process Flow Configuration</h2>
				</header>
				<div class="panel-body">
						<div class="row " style="display: flex; justify-content: flex-center; flex-wrap: wrap;">
							<div class="col-md-12">
								<div class="bagGroundColor px-3">
									<div class="row">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label" style="color: var(--white);">Select Operation Table</label>
												<select class="col-md-3 form-control" id="stageName" onchange="getStageConfigData()" name="modalName">
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
						<hr>
						<div id="dynamicDivId" class="row">
						</div>
					<!-- Submit butoon in center -->
					<div class="row" style="display: none; justify-content: center; flex-wrap: wrap; margin-top: 20px;">
						<div class="col-md-12 text-center">
							<button type="button" id="satgeConfigBtnId" class="btn btn-success" onclick="saveStageConfigData()">Save</button>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</section>


	<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog modal-xl">
	  <div class="modal-content">
		<div class="modal-header">
		  <h5 class="modal-title" id="staticBackdropLabel">More Criteria</h5>
		  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-2">
					<div class="form-group">
						<label class="control-label">Criteria Column</label>
						<select class="form-control" id="criteriaColumnId">
							<option value="">Select Column</option>
						</select>
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

	function getStageConfigData(){
		var stageName = $("#stageName").val();
		if(stageName == ""){
			errorDisplay("Please select stage name", $("#stageName"));
			$("#dynamicDivId").html("");
			return false;
		}
		$.ajax({
			url: "${contextPath}/stageConfig/processFlow/getStageFlowData",
			type: "GET",
			data: {
				entityClassName: stageName
			},
			success: function(response){
				let data = JSON.parse(response);
				console.log(data);
				let stageList = data.stageList;
				makeDynamicDiv(stageList);
			},
			error: function(response){
				console.log(response);
			}
		});
	}

	function makeDynamicDiv(stageList){
		let dynamicDivId = $("#dynamicDivId");
		dynamicDivId.html("");
		let html = "";
		// stageList = [{}, {}, {}];
		for(let i in stageList){
			let stageObj = stageList[i];

			let fromRoleId = stageObj.fromRoleId;
			let fromRoleName = stageObj.fromRoleName;
			let actionList = stageObj.actionList;
			

			html += '<div class="col-lg-3">';
			html += '<h4 class="inr-head fromRoleStl">'+fromRoleName+'</h4>';
			html += '<hr>';
			for (let j in actionList){
				let actionObj = actionList[j];
				let toRoleId = actionObj.toRoleId;
				let toRoleName = actionObj.toRoleName;
				let actionTypeName = actionObj.actionTypeName;
				let actionTypeId = actionObj.actionTypeId;
				let stageId = actionObj.stageId;
				html += '<div class="box-sml">';
				html += '<a href="#"><i class="bx bx-layer-plus" onclick="addCriteriaModal(this, '+stageId+', '+"'INSIDE'"+')"></i></a>';
				html += '<h5 class="inr-head">'+actionTypeName+'</h5>';
				html += '<a href="#"><i class="bx bx-chevrons-right" onclick="addCriteriaModal(this, '+stageId+', '+"'OUTSIDE'"+')"></i></a>';
				html += '</div>';
			}
			html += '<div class="line"></div>';
			html += '</div>';
		}
		dynamicDivId.html(html);
	}



	function addCriteriaModal(that, stageId, criteriaType){
		if(criteriaType.toUpperCase() == "INSIDE"){
			getAllColumnsFromModelClass();
		}

	}


	function getAllColumnsFromModelClass(){
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
					let columns = data.data;
					let columnSelect = $("#criteriaColumnId");
					$(columnSelect).empty();
					$(columnSelect).append('<option value="">Select Column</option>');
					for (let i = 0; i < columns.length; i++) {
						$(columnSelect).append('<option value="' + columns[i].columnName + '">' + columns[i].columnName + '</option>');
					}
					$("#staticBackdrop").modal('show');
				}
			},
			error : function(e) {
				alert("Error! " + e);
			}
		});
	}


</script>