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
				<c:forEach items="${schemaTableMap}" var="schema">
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

		<div class="content">
			<!-- A area where table name will show in comma separated -->
			<div class="row">
				
				<div class="col-md-12" >
					<div class="row" id="criteriaAreaId">
					</div>
				</div>

				<div class="col-md-12">
					<div class="form-group">
						<div class="col-md-12"><label for="tableList">Table List</label></div>
						<textarea class="form-control" id="tableList1" rows="5"
						readonly ondrop="drop(event)" ondragover="allowDrop(event)"></textarea>
					</div>
				</div>

				
				<div class="col-md-12" style="text-align: center; margin-top: 10px; margin-bottom: 30px;">
					<button type="button" id="executeBtn1" class="btn btn-primary" onclick="executeQuery(this)">Execute Query</button>
				</div>
				<!-- Table -->
				<div class="col-md-12 table-responsive ">
					<table id="dynamicTableId" class="datatable table table-bordered table-striped dynamicTableCls"></table>


					
				</div>
			</div>
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

	function allowDrop(ev) {
		ev.preventDefault();
	}

	function drag(ev) {
		// a tag value is like schema##table
		var tableName = ev.target.getAttribute('value');
		ev.dataTransfer.setData("text", tableName);
	}

	function drop(ev) {
		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		
		// Split the dropped data into schema and table names
		var parts = data.split('##');
		var schemaName = parts[0];
		var tableName = parts[1];

		// // Find the textarea where the cursor is located
		// var textarea = ev.target;
		
		// // Get the current content of the textarea
		// var currentText = textarea.value;
		
		// // Check if schema and table names are already present
		// if (currentText.includes(schemaName + '.' + tableName)) {
		// 	alert("Schema and table names are already in the textarea.");
		// 	return;
		// }

		// // Get the current cursor position in the textarea
		// var cursorPosition = textarea.selectionStart;
		
		// // Insert the schema and table names at the cursor position
		// var textBeforeCursor = currentText.substring(0, cursorPosition);
		// var textAfterCursor = currentText.substring(cursorPosition);
		// var newText = textBeforeCursor + schemaName + '.' + tableName + ', ' + textAfterCursor;
		
		// // Update the textarea with the new text
		// textarea.value = newText;

		// // Move the cursor to the end of the inserted text
		// var newCursorPosition = cursorPosition + schemaName.length + tableName.length + 3; // 3 for ', '
		// textarea.selectionStart = textarea.selectionEnd = newCursorPosition;

		// create a list of criteria filter, join, group by, sort by, sum, avg, count, max, min
		// like tableName 
		//			- filter
		//			- join
		//			- so on
		let formatiseText = makeFormatiseText(tableName);

		// if table name is already present then return
		if ($('#criteriaAreaId').find('p:contains('+formatiseText+')').length > 0) {
			return;
		}

		
		var criteriaHtml = '<div class="col-md-2">';
			criteriaHtml += '<p style="text-align: left;"><b>'+formatiseText+'</b></p>';
			// minus icon after p tag
			criteriaHtml += '<i class="fa fa-minus-circle" onclick="removeTable(this)"></i>';
			criteriaHtml += '<ul>';
				// showAllData(this) show all data of the table
				criteriaHtml += '<li style="position:relative;padding-right: 15px;"><a href="javascript:void(0)" onclick="showAllData(this)" value="'+schemaName+'##'+tableName+'">Show All Data</a></li>';
		for (var i = 0; i < criteriaList.length; i++) {
			criteriaHtml += '<li style="position:relative;padding-right: 15px;"><a href="javascript:void(0)" onclick="getColumnsAndOpertaions(this)" value="'+schemaName+'##'+tableName+'##'+criteriaList[i]+'">'+criteriaList[i]+'</a></li>';
		}
		criteriaHtml += '</ul>';
		criteriaHtml += '</div>';
		$("#criteriaAreaId").append(criteriaHtml);


	}
	var criteriaList = ['filter', 'join', 'group by', 'sort by', 'sum', 'avg', 'count', 'max', 'min'];
	// double click on the table name by class name get the columns "getColumnsClass"
	$(document).on('dblclick', '.getColumnsClass', function(event){
		var schemaTable = $(this).attr('value');
		var schema = schemaTable.split('##')[0];
		var table = schemaTable.split('##')[1];

		let formatiseText = makeFormatiseText(table);

		if ($('#criteriaAreaId').find('p:contains('+formatiseText+')').length > 0) {
			return;
		}

		var criteriaHtml = '<div class="col-md-2">';
			criteriaHtml += '<p style="text-align: left;"><b>'+formatiseText+'</b></p>';
			// minus icon after p tag
			criteriaHtml += '<i class="fa fa-minus-circle" onclick="removeTable(this)"></i>';
			criteriaHtml += '<ul>';
				// showAllData(this) show all data of the table
				criteriaHtml += '<li style="position:relative;padding-right: 15px;"><a href="javascript:void(0)" onclick="showAllData(this)" value="'+schema+'##'+table+'">Show All Data</a></li>';
		for (var i = 0; i < criteriaList.length; i++) {
			criteriaHtml += '<li style="position:relative;padding-right: 15px;"><a href="javascript:void(0)" onclick="getColumnsAndOpertaions(this)" value="'+schema+'##'+table+'##'+criteriaList[i]+'">'+criteriaList[i]+'</a></li>';
		}
		criteriaHtml += '</ul>';
		criteriaHtml += '</div>';
		$("#criteriaAreaId").append(criteriaHtml);

		
	});


	let count = 0;
	function getColumnsAndOpertaions(that){
		var schemaAndTable = $(that).attr('value');
		var schema = schemaAndTable.split('##')[0];
		var table = schemaAndTable.split('##')[1];
		// get a tag text
		var criteria = $(that).text();
		// add to modal title
		$('#addModalTitleId').text(criteria);
		// get the modal body
		var modalBody = $('#modalBodyId');
		
		// get the columns
		$.ajax({
			url : "${contextPath}/table/getColumns",
			type : "GET",
			data : {
				"schema" : schema,
				"table" : table
			},
			success : function(response) {
				var columnDetails = response.data;
				var columns = columnDetails.columns;
				var table = columnDetails.tableName;
				var schema = columnDetails.schemaName;
			
				columns = JSON.parse(columns);
				var query = columnDetails.query;

				// add schema to the select box
				$('#schemaSelectId'+count).empty();
				$('#schemaSelectId'+count).append('<option value="'+schema+'">'+schema+'</option>');
				// add table to the select box
				$('#tableSelectId'+count).empty();
				$('#tableSelectId'+count).append('<option value="'+table+'">'+table+'</option>');
				// add columns to the select box
				$('#columnSelectId'+count).empty();
				$('#columnSelectId'+count).append('<option value="">Select</option>');
				for (var i = 0; i < columns.length; i++) {
					$('#columnSelectId'+count).append('<option value="'+columns[i]+'">'+columns[i]+'</option>');
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
		// show the modal
		$('#exampleModal').modal('show');
	}
	
	function executeQuery(that){
		// get all chcked table and its schema like {schema1:[table1,table2],schema2:[table1,table2]}
		var tableList = {};
		// get id of the textarea where table names are present
		var textareaId = $(that).parent().parent().find('textarea').attr('id');
		// get the table names from the textarea
		var tableNames = $('#'+textareaId).val();
		// split the table names by comma
		var tables = tableNames.split(',');
		// loop through the tables
		for (var i = 0; i < tables.length; i++) {
			// get the schema name
			var schema = tables[i].split('.')[0].trim();
			// get the table name
			var table = tables[i].split('.')[1];
			// check if the schema is already present in the tableList

			// if schema or table name is empty then continue
			if(schema == '' || table == ''){
				continue;
			}

			if (tableList.hasOwnProperty(schema)) {
				// if present then add the table name to the schema
				tableList[schema].push(table);
			}else{
				// if not present then create a new schema and add the table name to the schema
				tableList[schema] = [table];
			}
		}
		var jsondata = JSON.stringify(tableList);
		$('#jsondata').val(jsondata);
		$.ajax({
			url : "${contextPath}/table/generateDynamicReport",
			type : "GET",
			data : {
				// Base64 encoded
				"encodedSchemaAndTables" : btoa(jsondata)
			},
			success : function(response) {
				var data = response.data;
				$('#dynamicTableId').empty();
				var tableHeader = '';
				var tableBody = '';
				for (var i = 0; i < data.length; i++) {
					var columnList = data[i].columns;
					var columns = JSON.parse(columnList);
					for (var j = 0; j < columns.length; j++) {
						let th = $("<th></th>");
						// filter icon
						th.append('<i class="fa fa-filter" onclick="dataFilter(this)"></i>');
						let colunmNmae = columns[j];
						// scheme_type make scheme Type
						colunmNmae = makeFormatiseText(colunmNmae);
						th.text(colunmNmae);
						// min-width: 50 + length of column name px
						th.css('min-width', 100 + columns[j].length + 'px');
						// text-align: center
						th.css('text-align', 'center');
						// add a icon to the header minus icon
						th.append('<i class="fa fa-minus-circle" onclick="removeColumn(this)"></i>');
						
						

						tableHeader += th.prop('outerHTML');
					}
					var rowDetails = data[i].data;
					var rows = JSON.parse(rowDetails);
					for (var j = 0; j < rows.length; j++) {
						var row = rows[j];
						var tr = $('<tr></tr>');
						for (var k = 0; k < row.length; k++) {
							var td = $('<td></td>');
							td.text(row[k]);
							// text-align: center
							td.css('text-align', 'center');
							tr.append(td);
						}
						tableBody += tr.prop('outerHTML');
					}
				}
				// append the table header
				$('#dynamicTableId').append('<thead><tr>'+tableHeader+'</tr></thead>');
				// append the table body
				$('#dynamicTableId').append('<tbody>'+tableBody+'</tbody>');
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
	

	function showAllData(that){
		var schemaAndTable = $(that).attr('value');
		var schema = schemaAndTable.split('##')[0];
		var table = schemaAndTable.split('##')[1];

		var tableList = {};
		tableList[schema] = [table];

		$.ajax({
			url : "${contextPath}/table/generateDynamicReport",
			type : "GET",
			data : {
				// Base64 encoded
				"encodedSchemaAndTables" : btoa(JSON.stringify(tableList))
			},
			success : function(response) {
				var data = response.data;
				

				
				var tableHeader = '';
				var tableBody = '';
				var columnList = data.columns;
				var columns = JSON.parse(columnList);
				for (var j = 0; j < columns.length; j++) {
					let th = $("<th></th>");

					// <th style="min-width: 110px; text-align: center; width: 110px;" class="sorting" tabindex="0" aria-controls="dynamicTableId" rowspan="1" colspan="1" aria-label="Finyear id: activate to sort column ascending">
					// <span style="padding-left: 10px; padding-right: 10px;">Finyear id</span>
					// <i class="fa fa-minus-circle" onclick="removeColumn(this)"></i>
					// <i class="fa fa-filter" onclick="dataFilter(this)"></i>
					// </th>
					let colunmNmae = columns[j];
					// scheme_type make scheme Type
					colunmNmae = makeFormatiseText(colunmNmae);
					
					th.css('min-width', 100 + columns[j].length + 'px');
					th.css('text-align', 'center');
					th.append('<span style="padding-left: 10px; padding-right: 10px;">'+colunmNmae+'</span>');
					th.append('<i class="fa fa-minus-circle" onclick="removeColumn(this)"></i>');
					th.append('<i class="fa fa-filter" onclick="dataFilter(this)"></i>');
					tableHeader += th.prop('outerHTML');
				}
				var rowDetails = data.data;
				var rows = JSON.parse(rowDetails);
				for (var j = 0; j < rows.length; j++) {
					var row = rows[j];
					var tr = $('<tr></tr>');
					for (var k = 0; k < row.length; k++) {
						var td = $('<td></td>');
						td.text(row[k]);
						// text-align: center
						td.css('text-align', 'center');
						tr.append(td);
					}
					tableBody += tr.prop('outerHTML');
				}

				if(tableBody == ''){
					tableBody = '<tr><td colspan="'+columns.length+'" style="text-align: center;">No Data Found</td></tr>';
				}


				// if ($.fn.DataTable.isDataTable('#dynamicTableId')) {
				// 	$('#dynamicTableId').DataTable().destroy();
				// }

				$('#dynamicTableId').empty();

				// append the table header
				$('#dynamicTableId').append('<thead><tr>'+tableHeader+'</tr></thead>');
				// append the table body
				$('#dynamicTableId').append('<tbody>'+tableBody+'</tbody>');

				if(rows.length == 0){
					return;
				}
				

				// if ($.fn.DataTable.isDataTable('#dynamicTableId')) {
				// 	$('#dynamicTableId').DataTable().destroy();
				// }


				$('#dynamicTableId').DataTable({
					dom: 'lBfrtip',
					responsive: true,
					pageLength: 10,
					// export to excel, pdf, copy, print
					buttons: [
						'excel', 'pdf', 'copy', 'print'
					],
					
				});
				


				
				

			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});

	}

	function makeFormatiseText(text){
		// scheme_type make scheme Type
		text = text.replace(/_/g, ' ');
		// make first letter capital
		text = text.charAt(0).toUpperCase() + text.slice(1);
		return text;
	}

	function removeTable(that){
		// remove current div
		$(that).parent().remove();
	}

	function removeColumn(element){
		// get the index of the column
		var index = $(element).parent().index();
		// remove the column from the table
		$('#dynamicTableId tr').find('td:eq('+index+'),th:eq('+index+')').remove();
	}

	function getOperations(that){
		var column = $(that).val();
		var id = $(that).attr('id');
		var id = id.replace('columnSelectId', '');
		var schema = $('#schemaSelectId'+id).val();
		var table = $('#tableSelectId'+id).val(); 
		if (column == '') {
			return;
		}
		$.ajax({
			url : "${contextPath}/table/getOperations",
			type : "GET",
			data : {
				"column" : column,
				"schema" : schema,
				"table" : table
			},
			success : function(response) {
				var operations = response.data;
				// add current div id to the operation select box
				$('#operationSelectId'+id).empty();
				$('#operationSelectId'+id).append('<option value="">Select</option>');
				for (var i = 0; i < operations.length; i++) {
					$('#operationSelectId'+id).append('<option value="'+operations[i].operationName+'">'+operations[i].operationName+'</option>');
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}

	function addMoreCriteria(that){
		// get the id of the button
		var id = $(that).attr('id');
		// get the number from the id
		var id = id.replace('andOrId', '');
		var nextId = parseInt(id) + 1;
		var schema = $('#schemaSelectId'+id).val();
		var table = $('#tableSelectId'+id).val();
		var columnList = $('#columnSelectId'+id).html();

		var column = $('#columnSelectId'+id).val();
		var operation = $('#operationSelectId'+id).val();
		var value = $('#valueId'+id).val();
		if (column == '' || operation == '' || value == '') {
			alert('Please fill all the fields');
			return;
		}

		// add new div
		var appendHtml = '<div class="row" id="rowId'+nextId+'">';
			appendHtml += '<div class="col-md-2">';
				appendHtml += '<div class="form-group">';
					appendHtml += '<Select class="form-control" id="schemaSelectId'+nextId+'">';
						appendHtml += '<option value='+schema+'>'+schema+'</option>';
					appendHtml += '</Select>';
				appendHtml += '</div>';
			appendHtml += '</div>';
			appendHtml += '<div class="col-md-2">';
				appendHtml += '<div class="form-group">';
					appendHtml += '<Select class="form-control" id="tableSelectId'+nextId+'">';
						appendHtml += '<option value='+table+'>'+table+'</option>';
					appendHtml += '</Select>';
				appendHtml += '</div>';
			appendHtml += '</div>';
			appendHtml += '<div class="col-md-2">';
				appendHtml += '<div class="form-group">';
					appendHtml += '<Select class="form-control" id="columnSelectId'+nextId+'" onchange="getOperations(this)">';
						appendHtml += columnList;
					appendHtml += '</Select>';
				appendHtml += '</div>';
			appendHtml += '</div>';
			appendHtml += '<div class="col-md-2">';
				appendHtml += '<div class="form-group">';
					appendHtml += '<Select class="form-control" id="operationSelectId'+nextId+'">';
						appendHtml += '<option value="operation1">Operation</option>';
					appendHtml += '</Select>';
				appendHtml += '</div>';
			appendHtml += '</div>';
			appendHtml += '<div class="col-md-2">';
				appendHtml += '<div class="form-group">';
					appendHtml += '<input type="text" class="form-control" id="valueId'+nextId+'" placeholder="Value">';
				appendHtml += '</div>';
			appendHtml += '</div>';
			appendHtml += '<div class="col-md-2">';
				appendHtml += '<div class="form-group">';
					appendHtml += '<select class="form-control" id="andOrId'+nextId+'" style="width: 100%;" onchange="addMoreCriteria(this)">';
						appendHtml += '<option value="">select</option>';
						appendHtml += '<option value="and">And</option>';
						appendHtml += '<option value="or">Or</option>';
					appendHtml += '</select>';
				appendHtml += '</div>';
			appendHtml += '</div>';
		appendHtml += '</div>';
		$('#rowId'+id).after(appendHtml);
	}


	
	var tdValues = new Set();
	function dataFilter(that){
		let tdValue = new Set();
		// find its tbody tr td value
		var index = $(that).parent().index();
		// get data from entire table
		var tableData = $('#dynamicTableId').DataTable().data();
		// loop through the table data
		for (var i = 0; i < tableData.length; i++) {
			// get the row data
			var rowData = tableData[i];
			// get the td value
			var value = rowData[index];
			// add the value to the set
			tdValue.add(value);
		}

		console.log(tdValue);

	}

	// when make excel make only visible columns and all tbody data using DataTables API
	$(document).on('click', '.buttons-excel', function(){
		
		if ($.fn.DataTable.isDataTable('#dynamicTableId')) {
					$('#dynamicTableId').DataTable().destroy();
				}


		$('#dynamicTableId').DataTable({
			dom: 'lBfrtip',
			responsive: true,
			pageLength: 10,
			// export to excel, pdf, copy, print
			buttons: [
				'excel', 'pdf', 'copy', 'print'
			],
			
		});


		

		
	});

	
	


</script>


