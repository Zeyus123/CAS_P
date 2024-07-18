<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>
table.fancytree-ext-table tbody tr.fancytree-selected {
    background-color: #FFFFFF;
}
</style>
<div class="cardcontainer">
	<div class="row">
		<div class="col-md-6">
	    	<h5><spring:message code="site.admin.acl.configure" /></h5>
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
	<h6 class="headingbg mb-4">Menu Permissions</h6>
	<%@ include file="/WEB-INF/views/message.jsp"%>

	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
       <div class="form-group col-md-2">
          <label class="required">Role :</label>
          <div class="col-md-12">
             <select class="form-select" name="roleId" id="roleId"  >
			  	<option value=""><spring:message code="label.common.select"></spring:message></option>
			  	<c:forEach items="${roleList}" var="role">
					<option value="${role.roleCode}" data-id="${role.roleId}">${role.displayName}</option>
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
			    <col width="300px"></col>
			    <col width="300px"></col>
			    <col width="*"></col>
		    </colgroup>
		    <thead>
		      <tr> 
		      	<th>Menu Name</th> 
		      	<th>Menu URL</th>  
		      	<th>Privileges</th>
		      </tr>
		    </thead>
		    <!-- Optionally define a row that serves as template, when new nodes are created: -->
		    <tbody>
		      <tr>
		        <td></td>
		        <td></td>
		        <td></td>
		      </tr>
		    </tbody>
		  </table>
       </div>
     </div>
  </div>
	<div class="col-md-12 text-center">
		<a href="${contextPath}/" class="btn btn-warning">Cancel</a>
	</div>
</div>
<script type="text/template" id="selPriv">
	<select class="form-control chosen" multiple="multiple">
		<c:forEach items="${privs}" var="priv">
			<option value="${priv.id}">${priv.desc}</option>
		</c:forEach>
	</select>
</script>
<link href="${contextPath}/assets/js/plugin/jquery-fancytree/skin-xp/ui.fancytree.css" rel="stylesheet" />

<!-- Fancy Tree needs this version of jquery-ui -->
<script src="${contextPath}/assets/js/plugin/jquery-fancytree/jquery-ui.min.js"></script>
<script src="${contextPath}/assets/js/plugin/jquery-fancytree/jquery.fancytree-all.min.js"></script>

<!-- Chosen for Multiselect Tag Support -->
<script src="${contextPath}/assets/js/plugin/jquery.chosen/chosen.jquery.min.js"></script>
<link href="${contextPath}/assets/js/plugin/jquery.chosen/chosen.min.css" rel="stylesheet" />

<!-- end: page -->
<script type="text/javascript">
$().ready(function(){
	/* http://wwwendt.de/tech/fancytree/demo/index.html */
	$("#treegrid").fancytree({
		  checkbox: false,
		  extensions: ["wide", "table"],
		  selectMode: 1,
		  source : [] ,
		  table: {
		        nodeColumnIdx: 0,     // render the node title into the 2nd column
	      },
	      renderNode: function(event, data)
	      {
	    	  $.each(data.node.children, (idx, elem) => {
	    		  elem.selected = false;
	    	  });
	      },
	      renderColumns: function(event, data) {
	          var node = data.node;
              var $tdList = $(node.tr).find(">td");
              

	          // (index #0 is rendered by fancytree by adding the checkbox)
	          $tdList.eq(1).text(node.data.url);
	          
	          if (node.data.isParent == false)
        	  {
	        	  var $template = $($('#selPriv').html());
	        	  $template.attr("data-id",node.data.id);
	        	  
				   $.each(node.data.privs, (idx, elem) => {
					   var opt = $template.find('[value="' + elem.id + '"]')[0];
					   $(opt).prop('selected', true);
				   });
				   $tdList.eq(2).html($template);
				   $('.chosen').chosen({width: "95%",placeholder_text_multiple : "No privileges assinged"});
        	  }
	         
        },
	 });
	
	$('#roleId').change(function(){
		
		var roleCode = $('#roleId option:selected').val();
		
		$.ajax({
			url : "${contextPath}/admin/menu/assinged/" + roleCode,
			type: "GET",
	        dataType: "json",
	        success: function(data) {
	        	let tree = $("#treegrid").fancytree('getTree');
	        	tree.reload(data);
	        },
	        cache: false
		});

	});
	
	 $(document).on('change','.chosen', function(evt, params) {
		 var menuId =  $(evt.currentTarget).attr('data-id');
		 if (params.hasOwnProperty('selected'))
		 {
		 	addPrivilegeToMenu(menuId, params['selected']);
		 }
		 
		 if (params.hasOwnProperty('deselected'))
		 {
			 removePrivilegeFromMenu(menuId, params['deselected']);
		 }
	    
	 });
	 
	 function addPrivilegeToMenu(menuId, prvId)
	 {
		 menuId = parseInt(menuId);
		 prvId =  parseInt(prvId);
		 var roleId = $('#roleId option:selected').attr('data-id');
		 roleId = parseInt(roleId);
		 
		 $.ajax({
			url : "${contextPath}/admin/privilege/assign",
			type: "POST",
			data : {
				"_csrf" : '${_csrf.token}',
				"menuId" : menuId,
				"privId" : prvId,
				"roleId" : roleId
			},
	        dataType: "json",
	        success: function(data) {
	        	bootbox.alert("Privilege assigned succesfully.");
	        },
	        cache: false
		 });
	 }
	 
	 function removePrivilegeFromMenu(menuId, prvId)
	 {
		 menuId = parseInt(menuId);
		 prvId =  parseInt(prvId);
		 var roleId = $('#roleId option:selected').attr('data-id');
		 roleId = parseInt(roleId);
		 
		 $.ajax({
			url : "${contextPath}/admin/privilege/revoke",
			type: "POST",
			data : {
				"_csrf" : '${_csrf.token}',
				"menuId" : menuId,
				"privId" : prvId,
				"roleId" : roleId
			},
	        dataType: "json",
	        success: function(data) {
	        	bootbox.alert("Privilege removed succesfully.");
	        },
	        cache: false
		 });
	 }
});
</script>