<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="${contextPath}/assets/appJs/system/moduleController.js" ></script>

<div class="content">
<div class="panel-header bg-primary-gradient">
		<div class="page-inner py-4">
			<div class="d-flex align-items-left align-items-md-center flex-column flex-md-row">
				<div>
					<h2 class="text-blue pb-2 fw-bold"><spring:message code="site.system.privilege.add" /></h2>
					<%@ include file="/WEB-INF/views/message.jsp"%>
				</div>
				<div class="ml-md-auto mb-4 py-2 py-md-0">
					<a href="${contextPath}" class="btn btn-sm btn-border btn-blue btn-round mr-2"><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.system.privilege"/></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.system.privilege.add"/></a>
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
	               <h4 class="card-title">Add Privilege</h4>
	            </div>
			 	 <div class="card-body">
			 	 	 <form class="form-horizontal" method="POST" action="${contextPath}/system/setup/privilege/save" id="frmModule">
			 	 	 	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			 	 	 	  <input type="hidden" name="id" value="${privilege.id}" />
			 	 	 	  
			 	 	 	 <div class="col-md-12">
			 	 	 	 	<div class="col-md-4">
	                           <div class="form-group">
	                              <label class="col-md-12 required1" for="code">Code</label>
	                              <div class="col-md-12">
	                                 <input type="text" id="code" name="code" onchange="validateCharacterOnly(this.value);" value="${privilege.code}" maxlength="100"  class="form-control form-control-sm" required>
	                              </div>
                           		</div>
                        	</div>
                        	
                        	<div class="col-md-4">
	                           <div class="form-group">
	                              <label class="col-md-12 required1" for="desc">Description</label>
	                              <div class="col-md-12">
	                                 <input type="text" id="desc" name="desc" onchange="validateCharacterOnly(this.value);" value="${privilege.desc}" maxlength="150" class="form-control form-control-sm" required>
	                              </div>
                           		</div>
                        	</div>
                        	
                        	<div class="col-md-4">
	                           <div class="form-group">
	                              <label class="col-md-12 required1" for="priviledgeType">Privilege Position</label>
	                              <div class="col-md-9">
	                              	<div class="input-group">
	                              		<select class="form-control form-control-sm" id="priviledgeType" name="priviledgeType" required>
	                              			<option value="HEADER" ${privilege.priviledgeType eq 'HEADER' ? 'selected' : ''}>Form Header</option>
	                              			<option value="ROW" ${privilege.priviledgeType eq 'ROW' ? 'selected' : ''}>Per Row</option>
	                              			<option value="FOOTER" ${privilege.priviledgeType eq 'FOOTER' ? 'selected' : ''}>Form Footer</option>
	                              		</select>
	                                 </div>
	                              </div>
                         		</div>
                        	</div>
                        	
                        	<div class="col-md-4">
	                           <div class="form-group">
	                              <label class="col-md-12 required1" for="uiLabelCode">UI Label Code</label>
	                              <div class="col-md-12">
	                                 <input type="text" id="uiLabelCode" name="uiLabelCode" onchange="validateCharacterOnly(this.value);" maxlength="150" value="${privilege.uiLabelCode}" class="form-control form-control-sm" required>
	                              </div>
                           		</div>
                        	</div>
                       	
                        	<div class="col-md-3">
                        		 <div class="form-group">
	                              <label class="col-md-12 required1" for="isActive">Active</label>
	                              <div class="col-md-12">
	                                 <input type="checkbox" id="isActive" name="isActive" ${privilege.isActive eq true ? 'checked' : ''} class="form-control form-control-sm">
	                              </div>
                           		</div>
                        	</div>
			 	 	 	 </div>
			 	 	 </form>
			 	 </div>
			 	 	<div class="card-footer">
			 	 	<div class="col-sm-12 text-center mt-2">
			 	 		<input type="button" class="btn btn-primary" value="Save" id="btnSubmit" />
			 	 		<a href="${contextPath}/system/setup/privilege/list" class="btn btn-warning">Cancel</a>
			 	 	</div>
			 	 </div>
			 </div>
		</div>
	</div>
	</div>
	</div>
<script>
	function validateCharacterOnly(d)
	{
		return true;
	}
</script>
