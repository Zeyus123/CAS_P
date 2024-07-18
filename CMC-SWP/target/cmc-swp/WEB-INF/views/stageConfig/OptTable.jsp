<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
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
					<h2 class="panel-title">View Data From Table</h2>
				</header>
				<div class="panel-body">

						<div class="row " style="display: flex; justify-content: flex-center; flex-wrap: wrap;">
							<div class="col-md-12">
								<div class="bagGroundColor px-3">
									<div class="row">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label" style="color: var(--white);">Operation Table</label>
												<select class="col-md-3 form-control bgClrChangeOpt" id="stageNameId" onchange="getTableHeaderWithData('draft')">
													<option value="">Select table</option>
													<c:forEach items="${allEntityClasses}" var="stage">
														<option value="${stage.packageName}">${stage.className}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-md-2">
											<div class="form-group">
												<label class="control-label" style="color: var(--white);">Find</label>
												<input type="button" class="form-control bgClrChangeOpt" value="Find" onclick="getTableHeaderWithData('draft')">
											</div>
										</div>
										
									</div>
								</div>
							</div>
						</div>








						<nav style="margin-top: 10px;">
							<div class="nav nav-tabs tabnav" id="navTabs" role="tablist">
							</div>
						</nav>
						<div class="tab-content border bg-light" id="nav-tabContent">
						<div class="tab-pane fade active show" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
							<div class="row" id="dataSection">
								<div class="table-responsiv">
									<table class="table" id="tableId">
										<thead class="text-center bagGroundColorRvr" style="color: var(--white);" id="tableHeaderId">
											
										</thead>
										<tbody class="text-center" id="tableBodyId">
											
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</section>

<script>	

	function createAlertMessage(message, alertType, divId) {
		var alertMessage = '<div class="alert ' + alertType + ' alert-dismissible fade show" role="alert">' + message + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button></div>';
		$("#" + divId).html(alertMessage);
	}

	// get table header by modal name
	function getTableHeaderWithData(status) {
		var modalName = $("#stageNameId").val();
		if(modalName == "" || modalName == null || modalName == undefined){
			// please select table
			alert("Please select table");
			$("#tableHeaderId").empty();
			$("#tableBodyId").empty();
			// No Data Found Bro
			$("#tableBodyId").html("<tr><td colspan='100%' class='text-center'>No Data Found</td></tr>");
			return false;
		}
		//showLottie();
		$.ajax({
			url: "${contextPath}/stageConfig/get-table-headerAndData",
			type: "GET",
			data: {entityClassName: modalName, contextPath: "${contextPath}", status: status},
			success: function (data) {
				if(data == ""){
					$("#tableHeaderId").empty();
					// No Data Found Bro
					$("#tableBodyId").html("<tr><td colspan='100%' class='text-center'>No Data Found</td></tr>");
					hideLottie();	
					return false;
				}
				let res = JSON.parse(data);

				let isMappedWithWorkFlow = res.isMappedWithWorkFlow;
				let headers = res.headers;
				let dataArr = res.data;

				if(headers.length == 0){
					$("#tableHeaderId").empty();
					// No Data Found Bro
					$("#tableBodyId").html("<tr><td colspan='100%' class='text-center'>No Data Found</td></tr>");
					hideLottie();	
					return false;
				}

				if(!isMappedWithWorkFlow){
					$("#navTabs").empty();
					createTableHeader(headers);
					createTableBody(dataArr);
				}else{
					$("#tableHeaderId").empty();
					let statusList = res.statusList;
					let status = res.status;
					statusList = JSON.parse(statusList);
					let statusListHtml = "";
					$.each(statusList, function (index, value) {
						// <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button" >
						statusListHtml += "<button class='nav-link "+(status == value.toLowerCase() ? "active" : "")+"' ' id='nav-home-tab' data-bs-toggle='tab' data-bs-target='#nav-home' type='button' onclick='getTableHeaderWithData(\""+value.toLowerCase()+"\")'>"+value+"</button>";

					});
					$("#navTabs").html(statusListHtml);
					createTableHeader(headers);
					createTableBody(dataArr);
				}

				hideLottie();

			},
			error: function (jqXHR, textStatus, errorThrown) {
				hideLottie();
				alert("Error: " + errorThrown);
			}
		});
	}

	function createTableHeader(headers){
		let tableHeader = "<tr>";
		$.each(headers, function (index, value) {
			let thName = value.thName;
			let thStyle = value.thStyle;
			tableHeader += "<th style='"+thStyle+"'>"+thName+"</th>";
		});
		tableHeader += "</tr>";
		$("#tableHeaderId").empty().append(tableHeader);
	}

	function createTableBody(dataArr){
		let tableBody = "";
		$.each(dataArr, function (index, value) {
			tableBody += "<tr>";
			$.each(value, function (index, value) {
				tableBody += "<td>"+value+"</td>";
			});
			tableBody += "</tr>";
		});
		$("#tableBodyId").html(tableBody);
	}

</script>