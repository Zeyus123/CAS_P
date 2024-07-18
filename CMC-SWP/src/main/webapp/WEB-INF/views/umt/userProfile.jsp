<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link href="${contextPath}/assets/vendor/jquery_datepicker/jquery.datepick.css" rel="stylesheet">
<script type="text/javascript" src="${contextPath}/assets/appJs/validation/common-utils.js"></script>
<style>
.alert-danger {
    border-color: #f3acbb !important;
    color: #730503;
}
/*Profile Pic Start*/
.picture-container{
    position: relative;
    cursor: pointer;
    text-align: center;
}
.picture{
    width: 106px;
    height: 106px;
    background-color: #999999;
    border: 4px solid #CCCCCC;
    color: #FFFFFF;
    border-radius: 0px;
    margin: 10px auto;
    overflow: hidden;
    transition: all 0.2s;
    -webkit-transition: all 0.2s;
}
.picture:hover{
    border-color: #2ca8ff;
}
.content.ct-wizard-green .picture:hover{
    border-color: #05ae0e;
}
.content.ct-wizard-blue .picture:hover{
    border-color: #3472f7;
}
.content.ct-wizard-orange .picture:hover{
    border-color: #ff9500;
}
.content.ct-wizard-red .picture:hover{
    border-color: #ff3b30;
}
.picture input[type="file"] {
    cursor: pointer;
    display: block;
    height: 100%;
    left: 0;
    opacity: 0 !important;
    position: absolute;
    top: 0;
    width: 100%;
}
.picture img{
width:100%;
height:106px;
}
.picture-src{
    width: 100%;
}
</style>

<style>
.alert {
	padding: 5px 0px 7px 5px;
	border-radius: 1px;
	text-align: center;
	font-size: 14px;
	margin-left: 0px;
	width: 100%;
}

.alert-success {
	background-color: #c3f3af;
	border-color: #9ed289 !important;
	color: #194219;
}

.alert-danger {
	background-color: #e66f70;
	border-color: #f3acbb !important;
	color: #232120;
}

.close {
	font-size: 23px;
	opacity: 0.4;
	margin-right: 5px;
}

#messageDiv {
	position: relative;
}
</style>
<body>
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5>User Profile</h5>
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
	<h6 class="headingbg mb-4">User Details</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>

	<form class="form-horizontal" action="${contextPath}/umt/user/profile/update" method="POST" enctype="multipart/form-data" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="row">
			<div class="col-md-10">
				<div class="row">
					<div class="form-group col-md-2">
						<label class="required"><spring:message code="site.admin.user.first.name"></spring:message></label>
						<div class="col-md-12">
							<input type="text" id="firstName" name="firstName" value="${userDetails.firstName}" maxlength="150" class="form-control" required="required">
						</div>
					</div>
					<div class="form-group col-md-2">
						<label class="required" ><spring:message code="site.admin.user.last.name"></spring:message></label>
						<div class="col-md-12">
							<input type="text" id="lastName" name="lastName" value="${userDetails.lastName}" maxlength="150" class="form-control" required="required">
						</div>
					</div>
					<div class="form-group col-md-2">
						<label class="required" ><spring:message code="site.common.mobile"></spring:message></label>
						<div class="col-md-12">
							<input type="text" name="mobile" id="mobile" class="form-control NumbersOnly" maxlength="10"  value="${userDetails.mobile}" onchange="validateMobileNo(this);" required="required">
						</div>
					</div>
					<div class="form-group col-md-2">
						<label class="required" ><spring:message code="site.common.email"></spring:message></label>
						<div class="col-md-12">
						<c:choose>
						   <c:when test="${not empty userDetails.email}">
						  <input type="text" name="email" id="email" class="form-control emailsOnly"  maxlength="150" onchange="validateEmail(this);" readonly  value="${userDetails.email}" required="required">  
							</c:when>
							<c:otherwise>
						<input type="text" name="email" id="email" class="form-control emailsOnly"  maxlength="150" onchange="validateEmail(this);"  value="${userDetails.email}" required="required">  
							</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="form-group col-md-2">
						<label class="required" ><spring:message code="site.admin.user.name"></spring:message></label>
						<div id="hideUserId"> 
							<div class="col-md-12">
								<input type="text" id="userName" name="userName" value="${userDetails.userName}" maxlength="150" class="form-control" required="required" onchange="userNameValidation();">
							</div>
						</div>
					</div>
					<div class="form-group col-md-2">
						<label class="required">Designation</label>
						<div class="col-md-12">
							<input type="text" name="designation" id="designation" class="form-control" required value="${userDetails.designation}" maxlength="200">
						</div>
					</div>
				</div>
			</div>
			
	
				<div class="col-md-2">
					<div class="container">
						<div class="picture-container">
							<div class="picture">
							<c:choose>
								<c:when test="${not empty userDetails.profilePhoto}">
										<img id="uph" src="${contextPath}/umt/user/image/view" aria-hidden="true" alt="User Icon"/>
								</c:when>
								<c:otherwise>
								       <img id="uph" src="${contextPath}/assets/img/profile_pic.png" aria-hidden="true" alt="User Icon"> <span class="ph-text">Photo</span>
								</c:otherwise>
							</c:choose>
							 <input type="file" onchange="document.getElementById('uph').src = window.URL.createObjectURL(this.files[0]);imageCheck(this);" name="userProfileImage" id="userProfileImage">  
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 text-center mt-4">
					<button type="submit" class="btn btn-sm btn-success" id="submitBtn"><spring:message code="site.common.submit"></spring:message></button>
					   <a class="btn btn-sm btn-warning" href='${contextPath}/'> <spring:message code="site.common.back"></spring:message></a>
				</div>
			</div>
	</form>
</div>

<script src="${contextPath}/assets/vendor/jquery_datepicker/jquery.plugin.js"></script>
<script src="${contextPath}/assets/vendor/jquery_datepicker/jquery.datepick.js"></script>
<script> 	
function userNameValidation(){
	var userName = $("#userName").val();  
	$.ajax({
		type : "GET",
		url : "${contextPath}/umt/user/validate-user-name",
		data : {
			"userName" : userName,
		}, 
		success : function(response) {
			var val = JSON.parse(response); 
			if (val.isDuplicate == true) {
				bootbox.alert("Duplicate user name not allowed"); 
				var html ='<div class="col-md-12"><input type="text" id="userName" name="userName" value="${userDetails.userName}" class="form-control" required="required" onchange="userNameValidation()"></div>'
				$("#hideUserId").empty().append(html);
			}  
		}, 
		error : function(error) {
			//bootbox.alert("Failure"); 
		}
	});
}
$(function() {
	$('.datepicker_con>input').datepick(
			{
				onShow : $.datepick.monthOnly,
				dateFormat : 'dd/mm/yyyy',
				yearRange : 'c-100:c+5',
				showOnFocus : true,
				showTrigger : '<button type="button" class="trigger">'
						+ '<i class="fa fa-calendar"></i></button>'
			});
   }); 
</script> 

<script>
  function hide() {
    $("#messageDiv").hide();
  }
  setTimeout(function() {
    $('#messageDiv').fadeOut('slow');
  }, 10000);
  
</script>
	