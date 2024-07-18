<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

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
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5><spring:message code="site.admin.acl.level"></spring:message></h5>
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
	<h6 class="headingbg mb-4">Access Level Masters</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>

	<form class="form-horizontal" action="${contextPath}/" method="POST" id="userForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="mstLevelId" />
		<div class="col-md-12" style="padding-top: 15px;">
			<div class="col-sm-12">
				<div>
					<table class="DataTable table table-bordered table-sm">
						<thead>
							<tr>
								<th>Sl #</th>
								<th>Display Name</th>
								<th>Level Code</th>
								<th>Source Entity</th>
								<th>View Entity</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${lstAccessLevels}" var="acl"
								varStatus="cnt">
								<tr>
									<td>${cnt.count}</td>
									<td>${acl.displayName}</td>
									<td>${acl.levelCode}</td>
									<td>${acl.levelEntityName}</td>
									<td>${acl.displayViewName}</td>
									<td></td>
								</tr>
							</c:forEach>
	
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="form-group col-md-12 text-center mt-2">
			<input type="button" class="btn btn-success btn-sm" value="Submit" onclick="return validateForm()">
			<a href="${contextPath}/admin/role/list.htm" class="btn btn-warning btn-sm">Back</a>
		</div>
	</form>
</div>