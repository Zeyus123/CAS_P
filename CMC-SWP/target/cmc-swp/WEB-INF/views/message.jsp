<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.alert {
	width: 100%;
	font-size: 14px;
	margin-left: 0px;
	text-align: center;
	border-radius: 4px;
	padding: 3px 3px 4px 0;
}
.alert-success {
	color: #0c8e0c;
	background-color: #c3f3af;
	border-color: #61d132 !important;
}
.alert-danger {
	color: #d91b1b;
	background-color: #ffc4c4;
	border-color: #fe8ba3 !important;
}
.close {
	float: right;
	opacity: 0.3;
	font-size: 30px;
	margin-right: -10px;
	line-height: 10px;
	color: #ff0000 !important;
	background-color: transparent !important;
}

#messageDiv {
	position: relative;
	display: flex;
	align-items: center;
	justify-content: flex-start;
}
#messageDiv button {
	width: 25px;
	height: 25px;
	color: black;
	padding: 0;
	border: none;
	line-height: 0px;
	background: none;
    font-weight: 600;
	text-align: center;
}
#messageDiv .emptySpan {
	width: 03%;
}
#messageDiv .msgTextSpan {
	width: 94%;
	font-weight: 600;
}
#messageDiv .closeBtnSpan {
	width: 03%;
	text-align: right;
}
</style>


<c:if test="${not empty success_msg}">
	<div id="messageDiv" class="alert alert-success">
		<span class="emptySpan">&nbsp;</span>
		<span class="msgTextSpan">${success_msg}</span>
		<span class="closeBtnSpan"><button type="button" class="close" data-dismiss="alert" aria-hidden="true" onclick="hide();">x</button></span>
	</div>
</c:if>

<c:if test="${not empty error_msg}">
	<div id="messageDiv" class="alert alert-danger">
		<span class="emptySpan">&nbsp;</span>
		<span class="msgTextSpan">${error_msg}</span>
		<span class="closeBtnSpan"><button type="button" class="close" data-dismiss="alert" aria-hidden="true" onclick="hide();">x</button></span>
	</div>
</c:if>

<c:if test="${not empty err_msg}">
	<div id="messageDiv" class="alert alert-danger">
		<span class="emptySpan">&nbsp;</span>
		<span class="msgTextSpan">${err_msg}</span>
		<span class="closeBtnSpan"><button type="button" class="close" data-dismiss="alert" aria-hidden="true" onclick="hide();">x</button></span>
	</div>
</c:if>

<script>
	function hide() {
		$("#messageDiv").hide();
	}

	setTimeout(function() {
		$('#messageDiv').fadeOut('slow');
	}, 10000);

</script>

