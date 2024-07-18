<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link href="${contextPath}/assets/js/plugin/jquery-fancytree/skin-xp/ui.fancytree.css" rel="stylesheet" />
<!-- Fancy Tree needs this version of jquery-ui -->
<script src="${contextPath}/assets/js/plugin/jquery-fancytree/jquery-ui.min.js"></script>
<script src="${contextPath}/assets/js/plugin/jquery-fancytree/jquery.fancytree-all.min.js"></script>
<script src="${contextPath}/assets/js/plugin/jquery-fancytree/modules/jquery.fancytree.dnd5.js"></script>
<script src="${contextPath}/assets/js/plugin/jquery-fancytree/jquery-migrate-3.0.0.min.js"></script>
<script src="${contextPath}/assets/appJs/admin/menu/reorderMenuController.js"></script>
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5>Reorder Menu</h5>
		</div>
		<div class="col-md-6">
		    <nav aria-label="breadcrumb" style="float: right;">
	            <ol class="breadcrumb">
	                <li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
	                <li class="breadcrumb-item active" aria-current="page">User Management</li>
	            </ol>
	        </nav>
	    </div>
	</div>
	<hr style="margin-top: 5px;" />
	<h6 class="headingbg mb-4">Reorder Menu</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>

	<div class="row">
		<div class="col-md-12">
			<table class="table table-bordered table-striped table-sm" id="treegrid">
		 	  	<thead>
			     	<tr> 
				      	<th>Title</th> 
				      	<th>URL</th> 
			      	</tr>
				</thead>
	    <!-- Optionally define a row that serves as template, when new nodes are created: -->
			    <tbody>
			      <tr>
			        <td></td>
			        <td></td>
			      </tr>
			    </tbody>
		 	</table>
		</div> 
	</div>
</div>
 
	<form id="frmMoveMenu" action="" method="post">
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		<input type="hidden" name="menuId"  id="menuId"/>
		<input type="hidden" name="parentMenuId" id="parentMenuId" />
		<input type="hidden" name="order" id="order" />
	</form>