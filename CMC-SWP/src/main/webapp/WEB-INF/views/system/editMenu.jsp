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
					<h2 class="text-blue pb-2 fw-bold"><spring:message code="site.admin.acl.configure" /></h2>
					<%@ include file="/WEB-INF/views/message.jsp"%>
				</div>
				<div class="ml-md-auto mb-4 py-2 py-md-0">
					<a href="${contextPath}" class="btn btn-sm btn-border btn-blue btn-round mr-2"><i class="fa fa-home"></i></a>
					<a href="#" class="btn btn-sm btn-border btn-blue btn-round mr-2">/ <spring:message code="site.administration"/></a>
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
						<a href="#" class="fa fa-caret-down"></a>
					</div>
	               <h4 class="card-title">Edit Menu</h4>
	            </div>
			 	 <div class="card-body">
			 	 	 <div class="row">
				<%@ include file="/WEB-INF/views/message.jsp"%>
					<div class="col-md-12">
						<form class="form-horizontal" method="POST" action="${contextPath}/system/setup/menu/save" id="frmModule">
			 	 	 	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			 	 	 	 <input type="hidden" name="menuId" value="${menu.menuId}" />
			 	 	 	 
			 	 	 	 <div class="col-md-12">
			 	 	 	 	<div class="col-md-3">
	                           <div class="form-group">
	                              <label class="col-md-12 required" for="menuTextEN">Menu Name (English)</label>
	                              <div class="col-md-12">
	                                 <input type="text" id="menuTextEN" name="menuTextEN" onchange="validateCharacterOnly(this.value);" maxlength="70" value="${menu.menuTextEN}" class="form-control form-control-sm" required>
	                              </div>
                           		</div>
                        	</div>
                        	
                        	<div class="col-md-3">
	                           <div class="form-group">
	                              <label class="col-md-12 required" for="menuTextHI">Menu Name (Hindi)</label>
	                              <div class="col-md-12">
	                                 <input type="text" id="menuTextHI" name="menuTextHI" onchange="validateCharacterOnly(this.value);" maxlength="70"  value="${menu.menuTextHI}" class="form-control form-control-sm" required>
	                              </div>
                           		</div>
                        	</div>
                        	
                        	<div class="col-md-5">
	                           <div class="form-group">
	                              <label class="col-md-12 required1" for="menuIcon">Menu Icon</label>
	                              <div class="col-md-9">
	                              	<div class="input-group">
	                                 	<input type="text" id="menuIcon" name="menuIcon" readonly value="${menu.menuIcon}" class="form-control form-control-sm" style="height:32px;">
	                                 	<div class="input-group-append" style="display: flex;font-size: 20px;">
	                                 		<span class="input-group-text x-icon" id="btnFindIcon"><i class="fa fa-search"></i></span>
	                                 	</div>
	                                 </div>
	                              </div>
                                 <c:if test="${menu.menuIcon ne ''}"> 
                                 	 <div class="col-md-3">
                              			<i id="viewIcon" class="${menu.menuIcon} fa-2x"></i>
                              		</div>
                                 </c:if>
                         		</div>
                        	</div>
                        	<div class="col-md-2">
                        		<div class="form-group">
	                              <label class="col-md-12 required" for="displayOrder">Display Order</label>
	                              <div class="col-md-12">
	                                 <input type="number" id="displayOrder" name="displayOrder" min="1" maxlength="10"  value="${menu.displayOrder}" class="form-control form-control-sm" required>
	                              </div>
                           		</div>
                        	</div>
                        	
                        	<div class="col-md-2">
                        		 <div class="form-group">
	                              <label class="col-md-12 text-center" for="isDisplay">Is Display</label>
	                              <div class="col-md-12">
	                                 <input type="checkbox" id="isDisplay" name="isDisplay" ${menu.isDisplay eq true ? 'checked' : ''} class="form-control form-control-sm">
	                              </div>
                           		</div>
                        	</div>
                        	
                        	<div class="col-md-2">
                        		 <div class="form-group">
	                              <label class="col-md-12 text-center" for="isActive">Active Status</label>
	                              <div class="col-md-12">
	                                 <input type="checkbox" id="isActive" name="isActive" ${menu.isActive eq true ? 'checked' : ''} class="form-control form-control-sm">
	                              </div>
                           		</div>
                        	</div>
                        	
                        	<div class="col-md-4">
                        		 <div class="form-group">
	                              <label class="col-md-12 required1" for="menuURL">Menu URL</label>
	                              <div class="col-md-12">
	                                <input type="text" id="menuURL" name="menuURL"  value="${menu.menuURL}" maxlength="150"  class="form-control form-control-sm" ${menu.isParent eq true ? '' : 'readonly'}>
	                              </div>
                           		</div>
                        	</div>
                        	 <div class="card-footer">
			 	          	      <div class="col-sm-12 text-center mt-2">
			 	 		           <input type="submit" class="btn btn-primary btn-sm" value="Save" id="btnSubmit1" />
			 	 		                 <a href="${contextPath}/system/setup/menu/list" class="btn btn-warning btn-sm">Cancel</a>
										</div>
							 </div>
			 	 	 	 </div>
			 	 	 </form>
					</div> 
				</div>
			</div>
			 	
			 </div>
		</div>
		<!-- Modal to Select Icon -->
		<div class="modal" tabindex="-1" role="dialog" id="pnlChooseIcon">
			 <div class="modal-dialog  modal-lg" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title">Select Icon</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body" style="height:300px;overflow-y:scroll;">
			        <div class="col-md-12" id="glp">
                        	
                     </div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
