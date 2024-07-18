<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


  
<html>
<head>
	<meta charset="UTF-8">
	<title>CMC-SWP</title>
	<meta name="keywords" content="CMC-SWP" />
	<meta name="description" content="CMC-SWP">
	<meta name="author" content="Aashdit Technologies">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="shortcut icon" href="${contextPath}/loginPage/images/logo.png">
	<link rel="stylesheet" href="${contextPath}/loginPage/css/bootstrap.min.css">
	<link rel="stylesheet" href="${contextPath}/loginPage/css/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" />

	<script src="${contextPath}/loginPage/js/jquery-3.5.1.min.js"></script>
	<script src="${contextPath}/loginPage/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/AesUtil.js"></script>
	<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/aes.js"></script>
	<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/pbkdf2.js"></script>
	<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/lbase.js"></script> 
	<script src="${pageContext.request.contextPath}/loginPage/js/bootbox.min.js"></script>

<style>
	.grievanceHref {
		text-align: left;
		font-size: 12px;
		color: #0c6d48;
		text-decoration: none;
	}
	.grievanceHref:hover {
		opacity: 0.4;
	}

</style>

</head>

<body class="login_page">

<div class="left-fix">
	<img src="${contextPath}/loginPage/images/l1.png" alt="">
	<img src="${contextPath}/loginPage/images/r1.png" class="rotating" alt="">
	<img src="${contextPath}/loginPage/images/l2.png" alt="">
	<img src="${contextPath}/loginPage/images/r2.png" alt="">
	<img src="${contextPath}/loginPage/images/r3.png" alt="">
	<img src="${contextPath}/loginPage/images/cir.png" alt="">
</div>

	<body class="login_page">
        <section class="main-box">
        <%@ include file="/WEB-INF/views/message.jsp" %>
		<%@ include file="/WEB-INF/views/newLoader.jsp" %>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8">
                        <div class="logo-box">
                            <div class="logo-img">
<!--                                 <img src="#" class="img-fluid" /> -->
                            </div>
                            <div class="logo-content">
                                <!-- <h1>L<span>I</span>MS</h1> -->
                                <!-- <h4>Higher Education Administrative Decision System</h4> -->
<%--                                 <img src="${contextPath}/loginPage/images/heads-logo.png" class="img-fluid" /> --%>
								<h2 style="color:white;font-style: bold; font-size: 70px">CMC-SWP</h2>
                                <p style="color:white;font-style: bold">Housing & Urban Development Department, Odisha</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="login-box">
                            <h2><span>Login Here</span></h2>
                            <!-- <div class="login-fild">
                                <label>Login As...</label>
                                <select class="category">
                                    <option>State Admin</option>
                                    <option>Laboratory</option>
                                    <option selected>Patient</option>
                                </select>
                                <i class='bx bx-category-alt'></i>
                            </div> -->
                            <form class="form-horizontal loginbox" id="login-window" method="POST" action="${contextPath}/overwrite/umt/login" autocomplete="off">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="login-fild">
                                    <label>Username</label>
                                    <input class="form-control plc" name="userName" id="userName" autocomplete="off" type="text" maxlength="36">
                                    <i class='fa fa-user'></i>
									<span class="invalid-message" id="invalidUser" style="color: #ff6565; text-transform: initial;"></span>
                                </div>
                                <div class="login-fild">
                                    <label>Password</label>
									<input class="form-control"name="password" id="password" type="password" autocomplete="off" maxlength="30">
									<i class='fa fa-key'></i>
									<i toggle="#password-field" class="fa fa-fw fa-eye field_icon toggle-password"> </i>
                                   
                                </div>
                                
                                <div class="login-fild">
									<div class="fild d-flex justify-content-between captcha" id="cap" style="display: flex; padding: 0;justify-content: space-between;">
										<input name="captcha" type="text" placeholder="Enter Captcha" maxlength="5" id="captcha" class="form-control plc col-md-5" style="width: 50%;" autocomplete="off">
										<div class="captchrgt captcha" onmousedown="return false">
											<img src="${contextPath}/captcha/5" id="captchaImage" />
											<i class="fa fa-refresh" id="Image1" onclick="refreshCaptcha();" causesvalidation="false" src="${contextPath}/loginPage/images/refresh.png" style="width: 15px;"></i>
										</div>
									</div>
								</div>
								
								<div class="col-md-12" style="float: left; margin-top: 5px;">
									<div class="col-md-6" style="float: left;">
										<a href="${_APPURL_GRVNC}/public/lodgeGrievance" class="grievanceHref">Lodge Grievance</a>
									</div>
									<div class="col-md-6" style="float: left;">
	                                    <a href="#" data-bs-toggle="modal" data-bs-target="#forgotPassModal" class="fp">Forgot Password?</a>
									</div>
								</div>
								
<!-- 								
                                <div class="login-fild mt-0">
                                    <a href="#" data-bs-toggle="modal" data-bs-target="#forgotPassModal" class="fp">Forgot Password?</a>
                                </div>
 -->
                                <div class="login-fild submit-btn">
                                    <button type="button" class="button-style-custom submit-btn" id="btn_submit" onclick="userLogin()">
                                       <span>LOGIN <img src="${contextPath}/loginPage/images/login-btn-arrow.png" class="img-fluid login-btn-arrow" /></span>
                                    </button>
                                    <!-- <a href="#">New Registration</a> -->
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>

       



<div id="forgotPassModal" class="modal fade" role="dialog"  tabindex="-1" aria-labelledby="forgotPassModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:380px">
		<div class="modal-content">
			<div class="modal-header">
				<h6 class="modal-title">Forgot Password ?</h6>
				<button type="button" class="close" data-bs-dismiss="modal">&times;</button>
				<span id="SecondsUntilExpire" style="display: none;"></span>
			</div>
			<div class="modal-body col-md-12">
				<div class="" id="contents">
					<form style="padding:5px" class="form-horizontal" id="resetform" action="${contextPath}/reset/forgot-password" onsubmit="disableButton()" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

						<div class="col-md-12">
							<label>User Name<span style="color: #ff0808;">*</span> :</label>
						</div>
						<div class="col-md-12">
							<input type="text" class="form-control" id="username" name="username" required maxlength="64">
						</div>
						<div class="col-md-12 mt-2">
							<button type="button" onclick="passwordReset()" id="passwordResetBtn" class="btn btn-sm btn-primary" style="background-color:#a9230b; border:none; font-size: 12px;">Submit</button>
							<button type="button" class="btn btn-sm btn-danger" style="background-color: #339355; border: none;  font-size: 12px;" data-dismiss="modal">Cancel</button>
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer text-center">
				<div class="col-md-12">
					<label style="font-style: italic; color: #ff0808; font-size: 12px;">
						Note: Password will be sent to the registered email id.
					</label>
				</div>
			</div>
		</div>
	</div>
</div> 

    </body>

<script type="text/javascript">

function showLoader()
{
	$(".loader-div").css("display", "flex");
}
function hideLoader()
{
	$(".loader-div").css("display", "none");
}


function disableButton(){
	$("#passwordResetBtn").attr('disabled', true);
}
$(function() {
/*
	$(this).bind("contextmenu", function(e) {
		e.preventDefault();
	}); 
	$(this).bind('cut copy paste', function (e) {
	    e.preventDefault();
	});
*/
	$('form').attr("autocomplete", "off");
});

var errorMsg = '${err_msg}';
if(errorMsg !=""){
	setTimeout(function() {
	    $('#errorMsg').fadeOut('slow');
	}, 3000);
}

function userLogin() 
{
	var username = $("#userName").val().trim();
	var password = $("#password").val().trim();
	var generateCaptcha=$("#captcha").val();
    
	if (username == "") {
		bootbox.alert("Username can not be empty.");
		$("#userName").val("");
		return false;
	}else if (password == "") {
		bootbox.alert("Password can not be empty.");
		$("#password").val("");
		return false;
	}else if (generateCaptcha == "") {
		bootbox.alert("Please enter the CAPTCHA."); 
		$("#captcha").val("");
		return false;
	}else {
		showLoader();
		$('#password').val(enc_password(password)); 
		$("#login-window").submit();
		return false;
	}
}

document.onkeydown = function(e) {
	/* Block F12 */
	if(event.keyCode == 123) {
		return false;
	}
	/* CTRL + SHIFT + I */
	if(e.ctrlKey && e.shiftKey && e.keyCode == 'I'.charCodeAt(0)){
		return false;
	}
	/* CTRL + SHIFT + J */
	if(e.ctrlKey && e.shiftKey && e.keyCode == 'J'.charCodeAt(0)){
		return false;
	}
	/* CTRL + SHIFT + C 
	if(e.ctrlKey && e.shiftKey && e.keyCode == 'C'.charCodeAt(0)){
		return false;
	}*/
	/* CTRL + U */
	if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)){
		return false;
	}
}

$(document).on('click', '.toggle-password', function() 
{
	$(this).toggleClass("fa-eye-slash fa-eye");

	var input = $("#password");
	input.attr('type') === 'password' ? input.attr('type','text') : input.attr('type','password')
});

function refreshCaptcha() {
	var image = document.getElementById("captchaImage");
	image.src = '${contextPath}/captcha/5';
}

function passwordReset(){
	showLoader();
	$("#resetform").submit();
}

</script>
</body>
</html>