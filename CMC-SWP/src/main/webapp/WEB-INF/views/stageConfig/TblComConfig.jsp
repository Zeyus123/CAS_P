<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- FOnt aswomon cdn -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.6.0/css/all.min.css">

<style>
	table th {
		background: #c2b8b8;
	}
	.ddBox {
		display: flex;
    justify-content: space-between;
	flex-wrap: wrap;
    flex-direction: column;
	}
	.ddBox ul {
		list-style: none;
		padding: 0;
		margin: 0;
		padding: 5px 0px;
		float: left;
    width: 100%;
	}
	.ddBox ul .sub-menu {
		display: none;
		height: 200px;
		overflow: auto;
	}
	.ddBox i {
		transition: all 0.3s ease-in-out;
	}
	.main-menu > li{
		background: #071d4a;
    border-bottom: 1px solid #bababa;
    position: relative;
    margin-bottom: 5px;
    color: #fff;
	}
	.main-menu > li a {
		color: #fff;
    text-decoration: none;
    padding: 5px 5px;
    display: inline-block;
    width: 100%;
	overflow: hidden;
  position: relative;
  display: inline-block;
  margin: 0 5px 0 5px;
  text-align: center;
  text-decoration: none;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: left;
	}
	.main-menu > li > a i {
		color: #071d4a;
    text-decoration: none;
    background-color: #fff;
    right: 30px;
    position: absolute;
    top: 5px;
    height: 21px;
    width: 21px;
    text-align: center;
    line-height: 21px;
    border-radius: 2px;
	}
	.sub-menu {
		background: #e3e3e3;
	}
	.sub-menu li a {
	    color: #000;
    border-bottom: 1px dashed #aaa;
	text-align: left;
	}
	.ddBox .active > i {
		transform: rotate(90deg);
		-webkit-transform: rotate(90deg);
		-moz-transform: rotate(90deg);
		-o-transform: rotate(90deg);
		-ms-transform: rotate(90deg);
	}
	.left-header {
		text-align: center;
    font-size: 18px;
    background: #020d23;
    padding: 16px 0;
	color: #fff;
	margin-top:0;
	}

.ddBox ul .sub-menu::-webkit-scrollbar {
  width: 3px;
}

/* Track */
.ddBox ul .sub-menu::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px grey;
  border-radius: 10px;
}

/* Handle */
.ddBox ul .sub-menu::-webkit-scrollbar-thumb {
  background: #071d49;
  border-radius: 10px;
}
.dynamicTableCls th {
	padding: 2px 2px;
    font-size: 12px;
}
.dynamicTableCls td {
	padding: 2px 2px;
    font-size: 12px;
}
aside {
    left: 0;
    width: 250px;
    height: 100vh;
    background: #071d49;
    position: fixed;
    top: 0;
    z-index: 9999;
}
.content {
	width: calc(100% - 250px);
padding:15px;
    position: relative;
    left: 250px;
}

.modal{
	z-index: 9999;
}

.readOnlyClass {
	background-color: #e9ecef;
	opacity: 1;
	cursor: not-allowed;
}

.btn-primary{
   background: #007bff;
    border: #ddd 1px solid;
    color: #fff;
    padding: 10px;
}



</style>
<section>
	<div class="d-flex justify-content-between">
		<aside>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="ddBox">
				<h4 class="left-header">Schema & Tables</h4>
				<c:forEach items="${groupByPackage}" var="schema">
					<ul class="main-menu">
						<li>
							<a href="javascript:void(0)">${schema.key}<i class="fa fa-angle-right ClickToOpen"></i></a>
							<ul class="sub-menu">
								<c:forEach items="${schema.value}" var="table">
									<li style="position:relative;padding-right: 15px;"><a href="javascript:void(0)" class="getColumnsClass" draggable="true" ondragstart="drag(event)" value="${schema.key}##${table}">${table}</a>
										<!-- Check box -->
										<!-- <input type="checkbox" name="tableList" value="${schema.key}##${table}" style="position: absolute;right: 0; top: 8px;" onclick="checkUnChackThis(this)"> -->
									</li>
									
								</c:forEach>
							</ul>
						</li>
					</ul>
				</c:forEach>
			</div>
		</aside>

		<div class="content" id="dynamicContent">
			<table class="table table-success table-striped dynamicTableCls" id="dynamicTableId">
				<thead class="thead-dark">
					<tr>
						<th>Sl No</th>
						<th>Column Name</th>
						<th>Column Data Type</th>
						<th>Is Visible</th>
						<th>Is Editable</th>
						<th>Which Role Can Add</th>
						<th>Which Role Can Edit</th>
						<th>Which Role Can View</th>
					</tr>
				</thead>
				<tbody id="tbodyId">
					
				</tbody>
		</div>
	</div>

	


	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<!-- Centered -->
	<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
	  <div class="modal-content">
		<div class="modal-header">
		  <h5 class="modal-title" id="exampleModalLabel">Add <span id="addModalTitleId"></span></h5>
		  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="modal-body" id="modalBodyId">

			<div class="row" id="rowId0">
				<div class="col-md-2">
					<div class="form-group">
						<Select class="form-control readOnlyClass" id="schemaSelectId0">
							<option value="schema1">Schema</option>
						</Select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<Select class="form-control readOnlyClass" id="tableSelectId0">
							<option value="table1">Table</option>
						</Select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<Select class="form-control" id="columnSelectId0" onchange="getOperations(this)">
							<option value="column1">Column</option>
						</Select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<Select class="form-control" id="operationSelectId0">
							<option value="operation1">Operation</option>
						</Select>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<input type="text" class="form-control" id="valueId0" placeholder="Value">
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<select class="form-control" id="andOrId0" style="width: 100%;" onchange="addMoreCriteria(this)">
							<option value="">select</option>
							<option value="and">And</option>
							<option value="or">Or</option>
						</select>
					</div>
				</div>
					
			</div>
		  
		</div>
		<div class="modal-footer">
			<!-- Center button -->
		  <button type="button" class="btn btn-success" style="margin: 0 auto;" onclick="executeQuery()"><i class="fas fa-leaf"></i>Execute</button>
		</div>
	  </div>
	</div>
  	</div>

</section>

<script type="text/javascript">
	$(document).ready(function(){
		$('.main-menu > li > a').click(function(){
			$(this).closest('li').find('.sub-menu').slideToggle();
			$(this).toggleClass('active');
		});
	});

	// double click on table name
	$(document).on('dblclick', '.getColumnsClass', function(){
		var table = $(this).attr('value');
		var schema = table.split('##')[0];
		schema = 'com.aashdit.' + schema + '.model';
		var tableName = table.split('##')[1];
		let entityClassName = schema + '.' + tableName;
		$.ajax({
			url: '${contextPath}/table/getEntityClassColumns',
			type: 'GET',
			data: {entityClassName: entityClassName},
			success: function(data){
				console.log(data);
				if(data.outcome){
					data = data.data;
					let that = $('#tbodyId');
					createTbody(that, data);
				}
			}
		});
	});

	function createTbody(that, data){
		let columns = data.columns;
		let roleList = data.roleList;
		let html = '';
		for(let i in columns){
			let columnName = columns[i].columnName;
			let dataType = columns[i].dataType;
			let modelColumnName = columns[i].modelColumnName;
			let modelDataType = columns[i].modelDataType;
			let isAssociation = columns[i].isAssociation;
			let entityClassName = columns[i].entityClassName;

			html += '<tr>';
				html += '<td>'+(parseInt(i)+1)+'</td>';
				html += '<td>'+modelColumnName;
					if(isAssociation){
						html += '<i class="fa fa-angle-right ClickToOpen" style="margin-left: 15px;" onclick="getAssociation(this)" value="'+entityClassName+'" id="'+modelColumnName+'"></i>';
					}
				html += '</td>';
				html += '<td>'+modelDataType+'</td>';
				html += '<td><input type="checkbox" name="isVisible" value="'+columnName+'"></td>';
				html += '<td><input type="checkbox" name="isEditable" value="'+columnName+'"></td>';
				html += '<td>';
					html += '<select class="form-control" name="addRole">';
						html += '<option value="">Select</option>';
						for(let j in roleList){
							html += '<option value="'+roleList[j].id+'">'+roleList[j].roleName+'</option>';
						}
					html += '</select>';
				html += '</td>';
				html += '<td>';
					html += '<select class="form-control" name="editRole">';
						html += '<option value="">Select</option>';
						for(let j in roleList){
							html += '<option value="'+roleList[j].id+'">'+roleList[j].roleName+'</option>';
						}
					html += '</select>';
				html += '</td>';
				html += '<td>';
					html += '<select class="form-control" name="viewRole">';
						html += '<option value="">Select</option>';
						for(let j in roleList){
							html += '<option value="'+roleList[j].id+'">'+roleList[j].roleName+'</option>';
						}
					html += '</select>';
				html += '</td>';
			html += '</tr>';
		}
		// append html after 'that' element
		$(that).closest('table').find('tbody').html(html);
	}

	function createColumns(columns, that){
		let html = '<ul>';
		for(let i in columns){
			let columnName = columns[i].columnName;
			let dataType = columns[i].dataType;
			let modelColumnName = columns[i].modelColumnName;
			let modelDataType = columns[i].modelDataType;
			let isAssociation = columns[i].isAssociation;
			let entityClassName = columns[i].entityClassName;
			// listing all columns
			html += '<li style="position:relative;padding-right: 15px;">';
				html += '<a href="javascript:void(0)" value="'+columnName+'##'+modelColumnName+'">'+modelColumnName+'</a>';
				html += '<input type="checkbox" value="'+columnName+'##'+modelColumnName+'"style="margin-left: 15px;" onclick="checkUnChackThis(this)">';
				if(isAssociation){
					html += '<i class="fa fa-angle-right ClickToOpen" style="margin-left: 15px;" onclick="getAssociation(this)" value="'+entityClassName+'" id="'+modelColumnName+'"></i>';
				}
			html += '</li>';

		}
		html += '</ul>';
		$(that).html(html);
	}

	function getAssociation(that){
		let entityClassName = $(that).attr('value');
		$.ajax({
			url: '${contextPath}/table/getEntityClassColumns',
			type: 'GET',
			data: {entityClassName: entityClassName},
			success: function(data){
				console.log(data);
				if(data.outcome){
					data = data.data;
					let columns = data.columns;
					createColumns(columns, that);
				}
			}
		});
	}


</script>


