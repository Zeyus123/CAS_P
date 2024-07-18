<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>
.alert-danger {
    border-color: #f3acbb !important;
    color: #730503;
}
</style>
<body>
	<div class="wrapper sidebar_minimize">
		<div class="main-panel main-panel-bg">
			<div class="content">
				<div class="page-inner">
				 <%@ include file="/WEB-INF/views/message.jsp"%>
					<div class="row" style="margin-top: 30px;">
						<div class="col-md-12">
							<div class="card card-outer">
								<div class="card-header">
									<h4 class="card-title">User Registration Form</h4>
								</div>
								<div class="card-body">
									<form class="form-horizontal" action="${contextPath}/public/umt/user/registration" method="POST" onsubmit="return validateSubmit();">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<div class="row">
										<div class="col-md-12">
											<div class="col-md-3">
												<div class="form-group">
													<label class="col-md-12 required" for="complaintName"><spring:message code="site.admin.user.first.name"></spring:message></label>
													<div class="col-md-12">
														<input type="text" id="firstName" name="firstName" value="${userDetails.firstName}" class="form-control" required="required">
													</div>
												</div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label class="col-md-12 required" for="complaintAge"><spring:message code="site.admin.user.last.name"></spring:message></label>
													<div class="col-md-12">
														<input type="text" id="lastName" name="lastName" value="${userDetails.lastName}" class="form-control" required="required">
													</div>
												</div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label class="col-md-12 required" for="mobile"><spring:message code="site.common.mobile"></spring:message></label>
													<div class="col-md-12">
														<input type="text" name="mobile" id="mobile" class="form-control" maxlength="10"  value="${userDetails.mobile}" required="required">
													</div>
												</div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label class="col-md-12" for="Email"><spring:message code="site.common.email"></spring:message></label>
													<div class="col-md-12">
														<input type="text" name="email" id="email" class="form-control"  value="${userDetails.email}">  
													</div>
												</div>
											</div> 
											<div class="col-md-3">
												<div class="form-group">
													<label class="col-md-12 required" for="complaintName"><spring:message code="site.admin.user.name"></spring:message></label>
													<div id="hideUserId"> 
													<div class="col-md-12">
														<input type="text" id="userName" name="userName" value="${userDetails.userName}" class="form-control" required="required" onchange="userNameValidation();">
													</div>
													</div>
												</div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label class="col-md-12 required"> <spring:message code="site.admin.user.password"></spring:message>: </label> 
													<div class="col-md-12"> 
														<input type="password" class="form-control" name="password" id="txtPass" onchange="ensureStrongPassword(this);" required> 
													</div>
												</div>
											</div> 
											<div class="col-md-3">
												<div class="form-group">
													<label class="col-md-12 required"><spring:message code="site.admin.user.confirm.password"></spring:message>: </label> 
													<div class="col-md-12"> 
														<input type="password" class="form-control" name="txtRePass" id="txtRePass" onblur="clearData();"> 
														<span id="consms" style="font-size: 14px; font-style: italic;"></span>
													</div>
												</div>
											</div> 
											<div class="col-md-12 text-center mt-4">
												<button type="submit" class="btn btn-sm btn-success" id="submitBtn"><spring:message code="site.common.submit"></spring:message></button>
												<button type="reset" class="btn btn-sm btn-primary"><spring:message code="site.common.reset"></spring:message></button>
												   <a class="btn btn-sm btn-warning" href='${contextPath}/login'> <spring:message code="site.common.back"></spring:message></a>
											</div>
											<div class="col-md-12 mt-4">
												<div class="alert alert-info" id="lblPass" style="text-align: left;">
															<spring:message code="site.admin.password.dicliration1"></spring:message><br>
															 -<spring:message code="site.admin.password.dicliration2"></spring:message><br>
															 -<spring:message code="site.admin.password.dicliration3"></spring:message><br>
															 -<spring:message code="site.admin.password.dicliration4"></spring:message><br>
															 -<spring:message code="site.admin.password.dicliration5"></spring:message>
														</div>
											</div>
										</div>
									</div>
								</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		
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

var count = 0;
$("#txtRePass").keyup(function() {
	
	var pass = $("#txtPass").val();
	var rePass = $("#txtRePass").val();
	if ((rePass == null || rePass == "")) {
		$("#consms").text(" ");
		$("#consms").css("color", "black");

	} else {

		if (pass == rePass) {
			$("#consms").text(" ");
			$("#consms").css("color", "green"); 
			$('#submitBtn').removeAttr("disabled");
			count = 1;
		} else {
			$("#consms").text("Password not match..!!!");
			$("#consms").css("color", "red");
			count = 0;
		}
	}

});

$('#txtPass').keyup(function(){
	 $('#submitBtn').prop("disabled", true);
	var pass = ensureStrongPassword(this);
/* 	console.log (pass); */
	if (pass !== true)
	{
		$('#lblPass').removeClass('alert-info');
		$('#lblPass').addClass('alert-danger');
	}
	else
	{
		$('#lblPass').removeClass('alert-danger');
		$('#lblPass').addClass('alert-info');
	}
});

function clearData() {

	if (count == 0) {
		$("#txtRePass").val("");
	}
}

	
	function ensureStrongPassword(element)
	{
		var re = /^([a-zA-Z0-9@*#]{8,15})$/;
		var sc=/[-!$%^&*()_+|~=`{}[:;<>?,.@#\]]/g;
		
		var letters = element.value;
		 var uperCaseFlag = false;
		 var lowerCaseFlag = false;
		 var numberFlag = false;
		 var spCharFlag = false;
		 var lengthFlag = false;
		
		for (var i = 0;i<letters.length; i++) {
	        if (letters[i] === letters[i].toUpperCase()) {
	        	uperCaseFlag = true;
	        } 
	        if(letters[i] === letters[i].toLowerCase()) {
	        	lowerCaseFlag = true;
	        }
	        var matches = letters[i].match(/\d+/g);
	        if (matches != null) {
	        	numberFlag=true;
	        }
	        if(letters[i].match(sc)){
	        	spCharFlag=true;
	        }
	        if (letters.match(re))
	    	{
	        	lengthFlag=true;
	    	}
	        
	   }
		 if(uperCaseFlag==true && lowerCaseFlag==true && numberFlag==true && spCharFlag==true && lengthFlag==true){
	     	return true;
	     } else{
	    	 return false; 
	     }
		
		return true;
	}
	
	 function validateSubmit()
	{
		let txtP = $('#txtPass').val() ;
		if (txtP != '')
		{
			let txtRP = $("#txtRePass").val();
			if (txtP !== txtRP)
			{
				bootbox.alert('Passwords do not match');
				return false;
			}
			var elem = document.getElementById('txtPass');
			var ok = ensureStrongPassword(elem);
			if (!ok)
			{
				bootbox.alert('Password does not match Password Criteria.');
			}
			return ok;
		}
	} 
</script> 
	