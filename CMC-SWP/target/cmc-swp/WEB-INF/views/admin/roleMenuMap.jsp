
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


<div class="breadcrumb_conatiner">
    <div class="col-md-6">
       <h4 class="change-color">User Management</h4>
    </div>
    <div class="col-md-6">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
               <li class="breadcrumb-item">
                  <a href="${contextPath}/home">Home</a>
               </li>
               <li class="breadcrumb-item">
                  <a href="#">User Management</a>
               </li>
               <li class="breadcrumb-item active" aria-current="page">User Management</li>
            </ol>
        </nav>
     </div>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="card">
            <div class="card-header">
                <h5 class="card-title">User Management</h5>
            </div>
           <div class="card-body">
	    <%@ include file="/WEB-INF/views/message.jsp"%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="form-group col-md-3">
				<label class="required">Select Role :</label>
				<div class="col-sm-12">
					<select class="form-select" name="roleId" id="roleId">
						<option value=""><spring:message code="label.common.select"></spring:message></option>
						<c:forEach items="${roleList}" var="roleList">
							<option value="${roleList.roleCode}">${roleList.displayName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
				<!-- Menu Table -->
		<div class="col-md-12" style="margin-top: 20px;">
			<div class="form-group">
				<div class="col-sm-12">
					<!-- Add a <table> element where the tree should appear: -->
					<table id="treegrid" class="table table-collapsed">
						<colgroup>
							<col width="30px"></col>
							<col width="*"></col>
							<col width="450px"></col>
							<%-- <col width="180px"></col> --%>
						</colgroup>
						<thead>
							<tr>
								<th>Select</th>
								<th>Menu Name</th>
								<th>App Code</th>
								<th>Menu URL</th>
							</tr>
						</thead>
						<!-- Optionally define a row that serves as template, when new nodes are created: -->
						<tbody>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	
		<div class="form-group col-md-12 text-center">
			<button type="button" class="btn btn-success btn-sm" id="btnAssign">Assign</button>
			<a href="${contextPath}/" class="btn btn-warning btn-sm">Cancel</a>
		</div>
	</div>
</div>
	</div>
	</div>
	

<link href="${contextPath}/assets/js/plugin/jquery-fancytree/skin-xp/ui.fancytree.css" rel="stylesheet" />

<!-- Fancy Tree needs this version of jquery-ui -->
<script	src="${contextPath}/assets/js/plugin/jquery-fancytree/jquery-ui.min.js"></script>
<script	src="${contextPath}/assets/js/plugin/jquery-fancytree/jquery.fancytree-all.min.js"></script>


<!-- end: page -->
<script type="text/javascript">
	
$().ready(function(){
	
	$("#treegrid").fancytree({
		  checkbox: true,
		  selectMode: 3,
		  partsel: true , 
		  source : [] ,
		  extensions: ["table"],
		  table: {
		        indentation: 20,      // indent 20px per node level
		        nodeColumnIdx: 1,     // render the node title into the 2nd column
		        checkboxColumnIdx: 0  // render the checkboxes into the 1st column
	      },
	      renderColumns: function(event, data) {
	          var node = data.node,
              $tdList = $(node.tr).find(">td");

	          // (index #0 is rendered by fancytree by adding the checkbox)
	          $tdList.eq(3).text(node.data.url);
	          $tdList.eq(2).text(node.data.appCode);
        },
	 });
	
	$('#roleId').change(function(){
		showLoader();
		
		var roleCode = $('#roleId option:selected').val();
		let root = $("#treegrid").fancytree('getTree').rootNode;
		root.removeChildren();
		
		$.ajax({
			url : "${contextPath}/admin/menu/" + roleCode,
			type: "GET",
	        dataType: "json",
	        success: function(data) {
	        	hideLoader();
	        	let tree = $("#treegrid").fancytree('getTree');
	        	tree.reload(data);
	        },
	        cache: false
		});

	});
	
	$('#btnAssign').click(function(){
		let nodes = $('#treegrid').fancytree('getTree').getSelectedNodes();
		
		let nodeIds = [];
		
		$.each(nodes, function(idx, elem){
			nodeIds.push(elem.data.id);
			nodeIds.push(elem.parent.data.id);
			if (elem.parent.parent !== null)
			{
				nodeIds.push(elem.parent.parent.data.id);
			}
			
		});
		
		//console.log(nodeIds);
		var roleCode = $('#roleId option:selected').val();

		if (roleCode == "")
		{
			bootbox.alert("Please select a Role");
			return false;
		}
		
		/* Remove nodes with undefined values */
		var distIds = nodeIds.filter(function(value, index, arr){

			return value !== undefined;
		});
		
		/* filter to unique elements */
		distIds = [... new Set(distIds)];
		
		//console.log(distIds);
		
		$.ajax({
			url : "${contextPath}/admin/menu/assign",
			type: "POST",
			data : {
				"_csrf" : '${_csrf.token}',
				"data" : distIds,
				"roleCode" : roleCode
			},
	        dataType: "json",
	        success: function(data) {
	        	bootbox.alert("Menu assigned succesfully.");
	        },
	        cache: false
		});
	});
	
});

	
</script>

