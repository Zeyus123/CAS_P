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
					<h2 class="text-blue pb-2 fw-bold"><spring:message code="site.admin.acl.configure" /></h2>
					<%@ include file="/WEB-INF/views/message.jsp"%>
				</div>
				<div class="ml-md-auto mb-4 py-2 py-md-0">
					<a href="${contextPath}" class="btn btn-sm btn-border btn-blue btn-round mr-2"><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.system.menu"/></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.system.menu.list"/></a>
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
							<a href="#" class="fa fa-caret-down"></a>
						</div>
		               <h4 class="card-title">List Menus</h4>
		            </div>
			 	 <div class="card-body">
			 	 	 <table class="table table-bordered table-sm">
			 	 	 	<thead>
			 	 	 		<tr>
			 	 	 			<th>Sl #</th>
			 	 	 			<th>URL</th>
			 	 	 			<th>English Title</th>
			 	 	 			<th>Hindi Title</th>
			 	 	 			<th>Menu Icon</th>
			 	 	 			<th>Is Dsiplay</th>
			 	 	 			<th>Is Active</th>
			 	 	 			<th>Actions</th>
			 	 	 		</tr>
			 	 	 	</thead>
			 	 	 	<tbody>
			 	 	 		<c:forEach items="${lstMenu}" var="menu" varStatus="cnt">
			 	 	 			<tr>
			 	 	 				<td>${cnt.count}</td>
			 	 	 				<td>${menu.menuURL}</td>
			 	 	 				<td>${menu.menuTextEN}</td>
			 	 	 				<td>${menu.menuTextHI}</td>
			 	 	 				<td><i class="${menu.menuIcon} fa-2x"></i></td>
			 	 	 				<td>${menu.isDisplay eq 'true' ? 'YES' : 'NO'}</td>
			 	 	 				<td>${menu.isActive eq 'true' ? 'YES' : 'NO'}</td>
			 	 	 				<td>
			 	 	 					<a class="btn btn-xs btn-primary" data-toggle="tooltip" title="Edit" href="${contextPath}/system/setup/menu/edit/${menu.menuId}"><i class="fas fa-edit"></i></a>
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
<script type="text/javascript">
	$().ready(function(){
		$('.table').DataTable();
	})
</script>