<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authentication var="principal" property="principal" />

<style>
	.user-details-div {
	}
	.user-details-label {
		color: #fff;
		font-size: 12px;
		font-weight: 500;
	}
	.user-info-span {
		color: #fff200;
		font-size: 14px;
		font-weight: 600;
	}
	.view-profile-href {
		font-size: 12px;
		font-weight: 500;
		color: #f60ceb;
	}
	
	.edit-profile-href {
		font-size: 12px;
		font-weight: 500;
		color: #002699;
	}
	 .profile-links a {
        display: inline-block;
        margin-right: 10px; /* Optional: Add some margin between the links */
    }
</style> -->



<section class="main-header">
	<div class="home-content">
		<div class="logo-box d-flex align-items-center">
			<div class="img_con"><img src="${contextPath}/assets/img/favicon.png"></div>
			<div class="img_con logo"><img src="${contextPath}/assets/img/logo.png"></div>
              
			<div class="d-flex flex-column module_text">
				<span>Cuttack Municipal Corporation</span>
				<span>Single Window Portal</span>
			</div>
		</div>

		<ul class="header_right">
			<li style="overflow: initial;">
				<div class="dropdown">
					<a href="#" class="btn btn-sm" data-bs-toggle="dropdown" aria-expanded="false">
						<span>${principal.dbUser.firstName}&nbsp;${principal.dbUser.lastName}<b>${principal.dbUser.designation}</b></span>
						<i class="fa-solid fa-user"></i>
					</a>
					<ul class="dropdown-menu">
						<c:choose>
							<c:when test="${principal.dbUser.userType ne 'ADMIN'}">
								<li><a href="${contextPath}/core/edit-profile" class="edit-profile-href">Edit Profile</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${contextPath}/umt/user/profile" class="view-profile-href">View Profile</a></li>
							</c:otherwise>
						</c:choose>
<!--                     <li><a class="dropdown-item" href="#">My Profile</a></li> -->
					</ul>
				</div>
			</li>

			<li><div class="svg_links"><a href="#" class="btn btn-sm"><i class="fa-solid fa-bell"></i></a></div></li>
			<li><div class="svg_links"><a href="javascript:void(0)" id="logoutFromMenu" class="btn btn-sm"><i class="fa-solid fa-power-off"></i></a></div></li>
		</ul>
	</div>
</section>



		<%-- <section class="main-header">
          <div class="home-content">
            <div class="logo-box d-flex align-items-center">
              <div class="img_con">
                <img src="../assets/img/favicon.png">
              </div>
              <div class="img_con logo">
                <img src="../assets/img/logo.png">
              </div>
              <div class="d-flex flex-column module_text">
                <span>Human Resource Management System</span>
                <span>Department of Higher Education, Govt. of Odisha</span>
              </div>
            </div>
            <ul class="header_right">
              <li style="overflow: initial;">
                <div class="dropdown">
                  <a href="#" class="btn btn-sm" data-bs-toggle="dropdown" aria-expanded="false">
                    <span>
                      User Name
                      <b>Current Module Role</b>
                    </span>
                    <i class="fa-solid fa-user"></i>
                  </a>
                  <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="#">My Profile</a></li>
                  </ul>
                </div>
              </li>
              <li>
                <div class="svg_links">
                  <a href="#" class="btn btn-sm">
                    <i class="fa-solid fa-bell"></i>
                  </a>
                </div>
              </li>

              <li>
                <div class="svg_links">
                  <a href="#" class="btn btn-sm">
                    <i class="fa-solid fa-power-off"></i>
                  </a>
                </div>
              </li>
            </ul>
          </div>
        </section> --%>
        
        
        <!-- Backup Header -->
        
        
<%-- <nav class="navbar navbar-expand-lg navbar-dark headertop">
	<div class="container-fluid">
		<img src="${contextPath}/assets/img/connect-logo.png" class="img-fluid" style="width: 100px;">

		<c:if test="${not empty principal.dbUser.firstName}">
			<div class="col-md-7 user-details-div">
				<label class="col-md-12 text-center user-details-label">Hello, 
					<span class="user-info-span">${principal.dbUser.firstName} ${principal.dbUser.lastName}</span> &nbsp;
					<c:if test="${principal.dbUser.firstName ne 'Admin' && principal.dbUser.firstName ne 'Administrator'}">
						(${_Sesn_Designation}, &nbsp;${_Sesn_Department})<br>${_Sesn_CollegeName} 
					</c:if>
				</label>
			</div>
		</c:if>

		<div class="collapse navbar-collapse col-md-2 rightlistmenu" style="max-width: 5%;">
			<ul class="navbar-nav mb-2 mb-lg-0" style="">

				<!-- ========================================  NOTIFICATION BELL ======================================== -->
				<li class="nav-item dropdown notificationbtn" style="display: none;">
					<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						<img src="${contextPath}/assets/img/notification.png" alt="Notifications" />
					</a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="#">Action</a></li>
						<li><a class="dropdown-item" href="#">Another action</a></li>
						<li><hr class="dropdown-divider" /></li>
						<li><a class="dropdown-item" href="#">Something else here</a></li>
					</ul>
				</li>

				<!-- ========================================  PROFILE ICON ======================================== -->
				<li class="nav-item dropdown userbtn">
					<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						<img src="${contextPath}/assets/img/user.png" alt="User"/>
					</a>
					<ul class="dropdown-menu" style="left: -10em;">
						<li style="margin-right: 0; margin-top: 0px;">
							<div class="user-box">
								<div class="avatar-lg"><img src="${contextPath}/assets/img/profile_image.jpeg" alt="image profile" class="avatar-img rounded"></div>
								<div class="u-text">
									<h4>${principal.dbUser.firstName} ${principal.dbUser.lastName}</h4>
									<p class="text-muted" style="font-size: 11px;">${principal.dbUser.designation} </p>
									<div class="profile-links">
									<c:choose>
									<c:when test="${principal.dbUser.userType ne 'ADMIN'}">
									<a href="${contextPath}/core/edit-profile" class="edit-profile-href">Edit Profile</a>
									</c:when>
									<c:otherwise>
									 <p class="text-muted"></p><a href="${contextPath}/umt/user/profile" class="view-profile-href">View Profile</a> 
									</c:otherwise>
									</c:choose>
										<p class="text-muted"></p><a href="${contextPath}/umt/user/profile" class="view-profile-href">View Profile</a>
										<a href="${contextPath}/core/edit-profile" class="edit-profile-href">Edit Profile</a>
									</div>
								</div>
							</div>
						</li>
						<li style="margin-top: 0px;">
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="${contextPath}/umt/user/change/password">
								<i class="fa fa-key" style="margin-right: 6px; font-size: 14px;"></i>Change Password
							</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#" id="switchlogout">
								<i class="fa fa-sign-out" style="margin-right: 6px; font-size: 17px;"></i>Logout
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav> --%>


<form method="post" action="${contextPath}/umt/logout" id="frmLogout">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
	<input type="submit" style="display: none" />
</form>

<script>
	$(function() {
		$('#siteLangSelector').change(function() {
			const lang = $(this).val();
			switchLanguage(lang);
		});
	});

	$('#switchlogout').on('click', function() {
		bootbox.confirm("Are you sure you want to logout ?",function(result){
			if(result){
				showLoader();
				$("#frmLogout").submit();
			}
		});
	});

	$('#logoutFromMenu').on('click', function() {
		bootbox.confirm("Are you sure you want to logout ?",function(result){
			if(result){
				showLoader();
				$("#frmLogout").submit();
			}
		});
	});

	$("#langId1").change(function(){
		$('#lang').val(this.value);
		$('#change-lang').submit();
	});
		
</script>

