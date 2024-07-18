<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
body
{
	font-size: 12px !important;
}

.btn-toolbar {
    background-color: #68cbf1;
    padding: 3px;
    margin: 2px;
    border-color: #6fa0de;
}

.toolbar {
    padding: 2px;
    border-color: #d4ebf9e0;
    border-width: 2px;
    border-style: solid;
    background-color: aliceblue;
}

div#outcome {
    background-color: white;
    border-color: turquoise;
    border-style: solid;
    padding: 2px;
    border-width: 1px;
    margin-top: -14px;
    max-height : 300px;
    min-height : 300px;
    overflow-y : scroll;
}

#outcome thead th {
    position: sticky;
    top: 0;
}

#outcome th {
    background-color: lightgray;
    border-color: #49505754;
    border-style: solid;
    padding: 4px;
    border-width: 1px;
}

#outcome td {
    border-color: #49505754;
    border-style: solid;
    padding: 3px;
    border-width: 1px;
}

#query
{
	min-height: 320px;
}

</style>
<div class="container-fluid">
	<div class="col-sm-12">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="row toolbar">
			<button class="btn btn-toolbar" id="btnExecute">Execute</button>
			<button class="btn btn-toolbar" id="btnQuery">Query</button>
		</div>
		<div class="row">
			<pre class="form-control" id="query"></pre>
		</div>
		<div class="row" id="outcome">
		
		</div>
	</div>
</div>
<script src="${contextPath}/assets/js/ace-editor/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/assets/js/ace-editor/ext-language_tools.js"></script>

<script>
	$().ready(function(){

		$.ajax({
			url : '${contextPath}/system/support/tables',
			type : "GET",
			success : function (resp)
			{
				if (resp.outcome == true)
				{
					setUpEditor(resp.data);
					
				}
			}
		});

		function setUpEditor(tableNames)
		{
			var tableNameCompleter = {
				    getCompletions: function(editor, session, pos, prefix, callback) {
				        callback(null, tableNames.map(function(tableName) {
				            return {
				                caption: tableName,
				                value: tableName,
				                meta: "Table"
				            };
				        }));

				    }
				};

			//Set up editor
			window.editor = ace.edit("query");
			window.editor.setTheme("ace/theme/textmate");
			window.editor.session.setMode("ace/mode/sql");
			
			window.editor.setOption("highlightActiveLine", true);
			window.editor.setOption("enableBasicAutocompletion", true);
			window.editor.setOption("printMargin", false);
			
			window.editor.completers = [tableNameCompleter];

			//window.editor.session.$mode.$highlightRules.push(tableNames);
		}
		
		
		//window.editor.setOption("enableLiveAutocompletion", true);

		$('#btnExecute').click(() => {
			var sql = window.editor.getSession().getValue();
			if(window.getSelection().toString().length)
			{
				sql = window.getSelection().toString();
			}
			
			$.ajax({
				url : '${contextPath}/system/support/execute',
				type : "POST",
				data : {
						query : sql, 
						${_csrf.parameterName} : "${_csrf.token}" 
					} ,
				success : function (resp)
				{
					if (resp.outcome == true)
					{
						bootbox.alert("Success");
					}
					else
					{
						$('#outcome').html("");
						bootbox.alert(resp.message);
					}
					
				}
			});
			
		});


		$('#btnQuery').click(() => {
			var sql = window.editor.getSession().getValue();
			if(window.getSelection().toString().length)
			{
				sql = window.getSelection().toString();
			}
			
			$.ajax({
				url : '${contextPath}/system/support/query',
				type : "POST",
				data : {
						query : sql, 
						${_csrf.parameterName} : "${_csrf.token}" 
					} ,
				success : function (resp)
				{
					
					//console.log(data.data);
					if (resp.outcome == true)
					{
						var html = "<table><thead><tr>";
						var data = resp.data;
						
						var cols = data.columns;

						for (var icx = 0; icx < cols.length; icx++)
						{
							html += "<th>" + cols[icx] + "</th>";
						}
						html += "</thead><tbody>";

						for (var idx = 0; idx < data.data.length ; idx++)
						{
							html += "<tr>";
							var row = data.data[idx];
							for (var iex = 0 ; iex < row.length; iex ++)
							{
								html += "<td>" + row[iex] + "</td>";
							}
							html += "</tr>";
							
						}

						$('#outcome').html(html);
						
					}
					else
					{
						$('#outcome').html("");
						bootbox.alert(resp.message);
					}
					
				}
			
			});
		});

	});
	

	
</script>
