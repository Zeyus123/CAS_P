<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="content">
		<div class="panel-header bg-primary-gradient">
		<div class="page-inner py-4">
			<div class="d-flex align-items-left align-items-md-center flex-column flex-md-row">
				<div>
					<h2 class="text-blue pb-2 fw-bold"><spring:message code="site.system.privilege.configure" /></h2>
				</div>
				<div class="ml-md-auto mb-4 py-2 py-md-0">
					<a href="${contextPath}" class="btn btn-sm btn-border btn-blue btn-round mr-2"><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.system.privilege"/></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.system.privilege.configure"/></a>
				</div>
			</div>
		</div>
	</div>
		<div class="page-inner mt-5 pb-0">
		<div class="row mt-2">
			<div class="col-md-12">
			 <div class="card full-height">
			 		<div class="card-header">
		            	<div class="panel-actions">
		            		<a href="${contextPath}/system/setup/privilege/add" class="btn btn-primary" style="color:#fff;"><i class="fa fa-plus"></i>&nbsp;Add Privilege</a>
							<a href="#" class="fa fa-caret-down"></a>
						</div>
		               <h4 class="card-title">List Privilege</h4>
		            </div>
			 	 <div class="card-body">
			 	 	 <div class="row">
					<%@ include file="/WEB-INF/views/message.jsp"%>
						<div class="col-md-12">
							 <table class="table table-bordered table-striped table-sm">
			 	 	 	<thead>
			 	 	 		<tr>
			 	 	 			<th>Sl #</th>
			 	 	 			<th>Code</th>
			 	 	 			<th>Description</th>
			 	 	 			<th>Privilege Type</th>
			 	 	 			<th>Active</th>
			 	 	 			<th>UI Label Code</th>
			 	 	 			<th>Actions</th>
			 	 	 		</tr>
			 	 	 	</thead>
			 	 	 	<tbody>
			 	 	 		<c:forEach items="${lstPrivileges}" var="prv" varStatus="cnt">
			 	 	 			<tr>
			 	 	 				<td>${cnt.count}</td>
			 	 	 				<td>${prv.code}</td>
			 	 	 				<td>${prv.desc}</td>
			 	 	 				<td>${prv.priviledgeType}</td>
			 	 	 				<td>${prv.isActive eq 'true' ? 'YES' : 'NO'}</td>
			 	 	 				<td>${prv.uiLabelCode}</td>
			 	 	 				<td>
			 	 	 					<a class="btn btn-primary btn-xs" href="${contextPath}/system/setup/privilege/edit/${prv.id}">Edit</a>
			 	 	 					<%-- <c:choose>
			 	 	 						<c:when test="${prv.isActive eq true}">
			 	 	 							<a class="btn btn-danger btn-xs" href="#" id="btnInactivate">Deactivate</a>
			 	 	 						</c:when>
			 	 	 						<c:when test="${prv.isActive eq false}">
			 	 	 							<a class="btn btn-secondary btn-xs" href="#" id="btnActivate">Activate</a>
			 	 	 						</c:when>
			 	 	 					</c:choose> --%>
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
</div>
</div>
<script type="text/javascript">
	$().ready(function(){
		$('.table').DataTable();
	})
</script>