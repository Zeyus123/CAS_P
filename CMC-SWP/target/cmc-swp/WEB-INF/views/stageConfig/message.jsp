<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    position: absolute;
    top: 74px;
    left: 50%;
    z-index: 999999;
    width: calc(100% - 60px);
    transform: translateX(-50%);
}

.alert-danger {
	border-color: #f3acbb !important;
	color: #730503;
}

.close {
	font-size: 23px;
	opacity: 0.4;
	margin-right: 5px;
}
</style>

<c:if test="${not empty success_msg}">
	<div class="col-md-12">
		<div id="messageDiv" class="alert alert-success">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true" onclick="hide();">×</button>
			<span>${success_msg}</span>
		</div>
	</div>
</c:if>

<c:if test="${not empty error_msg}">
	<div class="col-md-12">
		<div id="messageDiv" class="alert alert-danger">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true" onclick="hide();">×</button>
			<span>${error_msg}</span>
		</div>
	</div>
</c:if>

<script>

function hide()
{
	$("#messageDiv").hide();
}
setTimeout(function() {
    $('#messageDiv').fadeOut('slow');
}, 10000);

</script>