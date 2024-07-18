<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="col-md-12">
	<h6 style="margin-bottom: 5px !important;">Current Workflow Details</h6>
	<div class="table-responsive">
		<table class="datatable table table-striped table-bordered">
			<thead>
				<tr>
					<th width="06%">Sl.#</th>
					<th width="30%">Staff Name</th>
					<th width="28%">Designation</th>
					<th width="28%">Department</th>
					<th width="08%" class="text-center">Level</th>
				</tr>
			</thead>
			<tbody id="authorityTableBody">
				<c:choose>
					<c:when test="${not empty authorityList}">
						<c:forEach items="${authorityList}" var="levAuth" varStatus="count">
							<input type="hidden" name="authorityList[${count.count-1}].workFlowId" id="workFlowId" value="${levAuth.workFlowId}" />
							<tr>
								<td>${count.count}</td>
								<td>${levAuth.repAuthStaffName}</td>
								<td>${levAuth.designation}</td>
								<td>${levAuth.department}</td>
								<td class="text-center">${levAuth.repAuthLevel}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr id="" style="background: #cfd9e3;">
							<td colspan="5" class="noRecordsRowAndText">No current workflow defined for the staff.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>
