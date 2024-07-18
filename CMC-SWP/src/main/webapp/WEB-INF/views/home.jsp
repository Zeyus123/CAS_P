<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%-- <script src="${contextPath}/assets/applicationSpecific/heads/sso/home.js"></script> --%>
<sec:authentication var="principal" property="principal" />

 
<div class="breadcrumb_conatiner">
	<div class="col-md-6"><h4 class="change-color">Dashboard</h4></div>
	<div class="col-md-6">
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
				<li class="breadcrumb-item active" aria-current="page">Dashboard</li>
				<li class="breadcrumb-item active" aria-current="page"><a href="${contextPath}/distListReport?reportFormat=PDF">Report</a></li>
			</ol>
		</nav>
	</div>
</div>
          
<div class="row">
	<div class="col-md-12">
		<div class="card welcome_card">
			<div class="card-body">
				<div class="row">
					<div class="col-md-5">
						<div class="img_con"><img src="${contextPath}/assets/img/user2.png"></div>
						<div class="text_content">
							<label><span>${principal.dbUser.firstName}</span></label>
							<label>${principal.dbUser.designation}</label>
						</div>
					</div>

					<c:if test="${principal.dbUser.primaryRole.roleCode ne 'ROLE_ADMIN'}">
					<c:if test="${not empty principal.dbUser.userType or principal.dbUser.userType ne null}">
						<div class="col-md-5" style="padding-left: 0;">
							<div class="col-lg-12" style="width: 60%;">
								<div class="form-group">
									<label style="margin-bottom: 5px;">Select a Role :</label>
									<select class="form-control form-control-sm" id="userRoleMap" name="userRoleMap" required onchange="submitForm();" style="color: #000;">
										<c:if test="${accessList.data.size() gt 1}">
											<option value="">- Select -</option>
										</c:if>
										<c:forEach items="${accessList.data}" var="access">
											<option value="${access.id}" ${access.id eq userRoleMapId ? 'selected':'' }>${access.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						</c:if>
					</c:if>
					<div class="col-md-3 card_link">
						<a href="#"><i class="fa fa-dashboard"></i> Executive Dashboard</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
            
<div class="row mini_cards row-cols-1 row-cols-md-6 g-3" id="applicationRow" style="margin-top: 10px;"></div>

<form action="${contextPath}/redirectToApplication" id="redirectToApp" method="POST">
	<input type="hidden" id="encValue" name="encValue">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${contextPath}/home" id="home" method="GET">
	<input type="hidden" id="homeEncValue" name="encValue">
</form>

<script>
$(function(){
	getApplicationList();
});

function getApplicationList(){
	var userRoleMap = $("#userRoleMap").val();
	if(userRoleMap=='' || userRoleMap==undefined){
		//bootbox.alert("No roles found.");
		return false;
	}
//	showLoader();
	$.ajax({
        type: 'GET',
        url: '${contextPath}/api/allowAll/getRoleApplicationMap',
        data: { 
        	"encValue" : btoa(userRoleMap),
        },
        success: function (response) {
        	var html = '';
        	$.each(response.data,function(idx,value){
        		html = html+'<div class="col">'
        				+ '<div class="card" style="cursor: pointer;" '
        				+ 'onclick="redirectToApplication('+value.application.applicationId+','+value.role.roleId+',\''+value.user.userLevel+'\',\''+value.objectType+'\','+value.objectTypeId+','+value.user.userTypeId+')">'
        				+ '<a href="#"><img src="${contextPath}/assets/img/icons/event1.jpg" class="card-img-top" alt="...">'
        				+ '<div class="card-body"><label class="card-title" style="cursor: pointer;">'+value.application.applicationName+'</label></div></a></div></div>'
			});
        	$("#applicationRow").empty().append(html);
        	hideLoader();
        	
        },
        error: function (error) {
        	hideLoader();
            bootbox.alert('Error in getting application list.');
        }
    });
}

function redirectToApplication(applicationId,role,userLevel,userType,entityId,userTypeId){
	var json={
			"roleId":role,
			"applicationId":applicationId.toString(),
			"entityId":entityId,
			"userLevel":userLevel,
			"userType":userType,
			"userTypeId":userTypeId
		};
	var enc = enc_password( JSON.stringify(json));
	$("#encValue").val(enc);
	$("#redirectToApp").submit();
}


function submitForm(){
	showLoader();
	var userRoleMap = $("#userRoleMap").val();
	if(userRoleMap==''){
		var html = '';
		$("#applicationRow").empty().append(html);
		$("#menuDiv").empty().append(html);
	}else{
		$("#homeEncValue").val(btoa(userRoleMap));
		$("#home").submit();
	}
	
}
</script>