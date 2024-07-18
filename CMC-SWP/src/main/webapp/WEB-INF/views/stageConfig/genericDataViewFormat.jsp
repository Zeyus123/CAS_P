<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
.modal-content {
    position: relative;
    display: flex;
    flex-direction: column;
    width: 100%;
    pointer-events: auto;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid rgba(0,0,0,.2);
    border-radius: 0.3rem;
    outline: 0;
    margin-top: 80px;
    max-height: 450px !important;
    }
    
    .modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    z-index: -1;
    width: 100vw;
    height: 100vh;
    background-color: #000;

}

</style>

<%@ include file="/WEB-INF/views/message.jsp"%>

			<div class="breadcrumb_conatiner">
              <div class="col-md-6">
                <h4 class="change-color">${title}</h4>
              </div>
              <div class="col-md-6">
                <nav aria-label="breadcrumb">
                  <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                      <a href="#">Home</a>
                    </li>
                    <li class="breadcrumb-item">
                      <a href="#">Procurement</a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">${title}</li>
                  </ol>
                </nav>
              </div>
            </div>



			
			<div class="row">
                <div class="col-md-12">

                  <div class="card">
                    <div class="card-header">
                      <h5 class="card-title">${title}</h5>
                    </div>
                    <div class="card-body">
        <form method="get" action="${actionUrl}" id="formId">
            <input type="hidden" name="status" value="" id="statusId" />

				<div class="row">
					<div class="col-md-12">
						<div class="row align-items-end">
							<nav style="margin-top: 10px;">
								<div class="nav nav-tabs tabnav" id="navTabs" role="tablist">
									<c:forEach items="${genericDataViewDto.viewStatusList}" var="sts">
										<button class="nav-link ${sts.statusCode eq genericDataViewDto.status ? 'active' : ''}" id="${sts.statusCode}TabId" data-bs-toggle="tab" type="button" onclick="getTableHeaderWithData('${sts.viewStatusId}')">${sts.displayName}</button>
									</c:forEach>
								</div>
							</nav>
							
							<div class="tab-pane fade active show" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
								<div class="row" id="dataSection">
									<div class="table-responsiv">
										<table class="datatable table table-striped table-bordered mt-3" id="tableId">
											<thead class="text-center bagGroundColorRvr" style="color: var(--black);" id="tableHeaderId">
												<c:forEach items="${genericDataViewDto.theadList}" var="hd">
													<th style="color: var(--white);">${hd.thName}</th>
												</c:forEach>
											</thead>
											<tbody class="text-center" id="tableBodyId">
												<c:forEach items="${genericDataViewDto.dataValueList}" var="data">
													<tr>
														<c:forEach items="${data}" var="value">
															<td>${value}</td>
														</c:forEach>
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
		
        </form>
        </div>
        </div>
        </div>
	</div>


	<!-- Create a modal havibg table slno, action tacken by, remark, date -->
	<div class="modal fade" id="historyTableRemarksModal" tabindex="-1" aria-labelledby="actionTakenModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="actionTakenModalLabel">Data Flow History</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				
				<div class="modal-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered">
							<thead class="bagGroundColor">
								<tr>
									<th>Sl No</th>
									<th>Stage</th>
									<th>Action Taken By</th>
									<th>Action Taken Role</th>
									<th>Remark</th>
									<th>Date</th>
								</tr>
							</thead>
							<tbody id="historyTableBodyId">
								
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				</div>
				
			</div>
		</div>
	</div>


<script>
	function getTableHeaderWithData(status) {
		$("#statusId").val(status);
		// submit form
		$("#formId").submit();
	}

	function callJavaScriptFunction(modelClassWithId){
		// split the string by ## to get model class and id
		var modelClassWithIdArray = modelClassWithId.split("##");
		var modelClass = modelClassWithIdArray[0];
		var id = modelClassWithIdArray[1];

		// ajax call to get the history table data
		$.ajax({
			url: "${pageContext.request.contextPath}/stageConfig/get-workFlow-history",
			type: "GET",
			data: {
				"entityClassName": modelClass,
				"entityId": parseInt(id)
			},
			success: function(response){
				console.log(response);
				var historyTableBody = $("#historyTableBodyId");
				historyTableBody.empty();
				if(response != null && response != undefined && response != "" && response.length > 0){
					let html = "";
					$.each(response, function(index, value){
						html += "<tr>";
						html += "<td>"+(index+1)+"</td>";
						html += "<td>"+value.stageName+"</td>";
						html += "<td>"+value.actionTakenByName+"</td>";
						html += "<td>"+value.actionTakenOnRoleName+"</td>";
						html += "<td>"+value.remark+"</td>";
						html += "<td>"+value.actionTakenDate+"</td>";
						html += "</tr>";
					});
					historyTableBody.append(html);
				}
				// show the modal
				$("#historyTableRemarksModal").modal("show");
			},
			error: function(response){
				console.log(response);
			}
		});
	}


</script>