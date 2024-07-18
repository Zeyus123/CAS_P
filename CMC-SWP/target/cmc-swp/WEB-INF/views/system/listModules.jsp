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
					<h2 class="text-blue pb-2 fw-bold"><spring:message code="site.admin.module.configure" /></h2>
					<%@ include file="/WEB-INF/views/message.jsp"%>
				</div>
				<div class="ml-md-auto mb-4 py-2 py-md-0">
					<a href="${contextPath}" class="btn btn-sm btn-border btn-blue btn-round mr-2"><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.system.module"/></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.admin.module.configure"/></a>
				</div>
			</div>
		</div>
	</div>
		<%@ include file="/WEB-INF/views/message.jsp"%>
		<div class="page-inner mt-5 pb-0">
		<div class="row mt-2">
			<div class="col-md-12">
			 <div class="card full-height">
			 		<div class="card-header">
		            	<div class="panel-actions">
		            		<a href="${contextPath}/system/setup/module/add" class="btn btn-primary" style="color: #fff;"><i class="fa fa-plus"></i>&nbsp;Add Module</a>
							<a href="#" class="fa fa-caret-down"></a>
						</div>
		               <h4 class="card-title">List Modules</h4>
		            </div>
			 	 <div class="card-body">
			 	 	 <div class="row">
					<%@ include file="/WEB-INF/views/message.jsp"%>
						<div class="col-md-12">
						<table id="basic-datatables" class="DataTable display table table-bordered table-hover table-striped" >
			 	 	 	<thead>
			 	 	 		<tr>
			 	 	 			<th>Sl #</th>
			 	 	 			<th>English Title</th>
			 	 	 			<th>Hindi Title</th>
			 	 	 			<th>Menu Icon</th>
			 	 	 			<th>Is Active</th>
			 	 	 			<th>Actions</th>
			 	 	 		</tr>
			 	 	 	</thead>
			 	 	 	<tbody>
			 	 	 		<c:forEach items="${lstModule}" var="module" varStatus="cnt">
			 	 	 			<tr>
			 	 	 				<td>${cnt.count}</td>
			 	 	 				<td>${module.menuTextEN}</td>
			 	 	 				<td>${module.menuTextHI}</td>
			 	 	 				<td><i class="${module.menuIcon} fa-2x"></i></td>
			 	 	 				<td>${module.isActive eq 'true' ? 'YES' : 'NO'}</td>
			 	 	 				<td>
			 	 	 					<a class="btn btn-primary btn-xs" href="${contextPath}/system/setup/module/edit/${module.menuId}" data-toggle="tooltip" title="Edit"><i class="fas fa-edit"></i></a>
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